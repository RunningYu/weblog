package com.weblog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.weblog.common.RestClient;
import com.weblog.constants.MqConstants.CaffeineMqConstants;
import com.weblog.constants.other.CaffeineConstants;
import com.weblog.constants.other.EsConstants;
import com.weblog.dao.BlogMapper;
import com.weblog.entity.Blog;
import com.weblog.entity.Column;
import com.weblog.service.IBlogService;
import com.weblog.utils.Jieba;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/10
 */
@Service
public class IBlogServiceImpl  extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Jieba jieba;

    @Autowired
    private Cache<String, Object> caffeineCache;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private BlogMapper blogMapper;

    @ApiOperation("发布博文")
    @Override
    public void publishBlog( String blogId, String userId, String title, String publishImage, String discription,
                             String content, String tag, Integer ableLook, Integer status ) {
        blogMapper.publishBlog( blogId, userId, title, publishImage, discription, content, tag, ableLook, status );
    }

    @ApiOperation("新建用户的博文 标签")
    @Override
    public void insertTag(String userId, String tag ) {
        blogMapper.insertTag( userId, tag );
    }

    @ApiOperation("新建用户的博文 专栏")
    @Override
    public void insertColumn( String columnId, String userId, String column, String discription, String cover ) {
        blogMapper.insertColumn( columnId, userId, column, discription, cover );
    }

    @ApiOperation("将博文与对应的专栏关联起来")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBlogIntoColumn(String blogId, List<String> columnIdList ) {
        for( String columnId : columnIdList ) {
            blogMapper.insertBlogIntoColumn( blogId, columnId );
        }
    }

    @ApiOperation("用户的博文数量+1")
    @Override
    public void blogAmountAddOne(String userId) {
        blogMapper.blogAmountAddOne(userId);
    }

    @ApiOperation("同步博文到索引表中")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBlogToIndexById(String blogId) {
        // 博文的索引库中的索引表名称
        String indexName = "tb_weblog_blog";

        try {
            // 根据博文id从数据库中查询博文
            Blog blog = getBlogFromDatabaseById(blogId);

            // 1.准备Request对象
            IndexRequest request = new IndexRequest(indexName).id(blog.getId().toString());
            // 2.准备Json文档
            request.source(JSON.toJSONString(blog), XContentType.JSON);
            // 3.发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("博文的评论量+1")
    @Override
    public void blogCommentAmountAddOne(String blogId) {
        blogMapper.blogCommentAmountAddOne(blogId);
    }

    @ApiOperation("获取该博文的评论量")
    @Override
    public Integer getBlogCommentAmountByBlogId(String blogId) {
        return blogMapper.getBlogCommentAmountByBlogId(blogId);
    }

    @ApiOperation("删除博文")
    @Override
    public void deleteBlogByBlogId(String blogId) {
        blogMapper.deleteBlogByBlogId(blogId);
    }

    @ApiOperation("更新用户的评论量（减去该博文博文的评论量）")
    @Override
    public void decreaseCommentedAmountOfUser(String userId, Integer commentAmount) {
        blogMapper.decreaseCommentedAmountOfUser(userId, commentAmount);
    }

    @ApiOperation("es同步删除博文")
    @Override
    public void deleteBlogOfIndexByBlogId(String id) {
        restClient.deleteDocumentById("tb_weblog_blog", id);
    }

    @ApiOperation("更新博文的评论量（减去（1 + 该评论的总回复量））")
    @Override
    public void
    decreaseCommentAmountOfBlog(Integer amount, String blogId) {
        blogMapper.decreaseCommentAmountOfBlog(amount, blogId);
    }

    @ApiOperation("更新博文的评论量（减去1）")
    @Override
    public void blogCommentAmountReduceOne(String blogId) {
        blogMapper.blogCommentAmountReduceOne(blogId);
    }

    @ApiOperation("修改博文")
    @Override
    public void updateBlogByBlogId(String blogId, String title, String publishImage, String discription, String content, String tag, Integer ableLook, Integer status ) {
        blogMapper.updateBlogByBlogId(blogId, title, publishImage, discription, content, tag, ableLook, status);
    }

    @ApiOperation("查询改博文的归属专栏")
    @Override
    public List<String> getColumnIListByBlogId(String blogId) {
        return blogMapper.getColumnIListByBlogId(blogId);
    }

    @ApiOperation("删除 专栏博文关联")
    @Override
    public void deleteConnectOfBlogAndColumn(String blogId, String columnId) {
        blogMapper.deleteConnectOfBlogAndColumn(blogId, columnId);
    }


    @Override
    @ApiOperation("根据博文id从数据库中查询博文")
    public Blog getBlogFromDatabaseById(String blogId) {
        return blogMapper.getBlogFromDatabaseById(blogId);
    }

    @ApiOperation("分页查询自己的博文（从es中）")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Blog> getOwnBlogListByUserId(String userId, Integer startIndex, Integer size) {

        String indexName = "tb_weblog_blog";
        // 封装必须匹配的过滤条件
        Map<String, String> esMust = new HashMap<>();
        esMust.put("userId", userId);
        // 封装选择性匹配的过滤条件
        Map<String, String> esShould = new HashMap<>();
        // 封装排序的条件
        Map<String, String> sort = new HashMap<>();
        sort.put("sortCondition", "createTime");
        sort.put("sortWay", "DESC");

        SearchHit[] hits = restClient.getListByMust_Should_SortFromIndex(indexName, esMust, esShould, sort, startIndex, size);

        // 反序列化获取博文列表
        List<Blog> blogList = hitsToBlogList(hits);
        return blogList;
    }

    @ApiOperation("分页查询专栏中的博文")
    @Override
    public List<Blog> getBlogListByColumnId(String columnId, Integer startIndex, Integer size) {

        return blogMapper.getBlogListByColumnId(columnId, startIndex, size);
    }

    @ApiOperation("统计专栏中的博文总量")
    @Override
    public Integer getBlogTotalAmountByColumnId(String columnId) {
        return blogMapper.getBlogTotalAmountByColumnId(columnId);
    }

    @ApiOperation("查询个人用户的所有专栏")
    @Override
    public List<Column> getColumnListByUserId(String userId) {
        return blogMapper.getColumnListByUserId(userId);
    }

    @ApiOperation("统计用户的专栏总数")
    @Override
    public Integer getColumnTotalAmountByUserId(String userId) {
        return blogMapper.getColumnTotalAmountByUserId(userId);
    }

    @ApiOperation("从数据库中分类分页查询博文")
    @Override
    public List<Blog> getBlogListByUserIdCategoryId(String userId, String categoryId, Integer startIndex, Integer size) {
        return blogMapper.getBlogListByUserIdCategoryId(userId, categoryId, startIndex, size);
    }

    @ApiOperation("统计用户的该类的博文总数")
    @Override
    public Integer getBlogTotalAmountByUserIdCategoryId(String userId, String categoryId) {
        return blogMapper.getBlogTotalAmountByUserIdCategoryId(userId, categoryId);
    }

    @ApiOperation("分页查询 推荐博文（根据浏览量+点赞量+评论量 总和降序）")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Blog> getAdviceBlogList(Integer startIndex, Integer size) {

        List<Blog> blogList = blogMapper.getAdviceBlogList(startIndex, size);

        return blogList;
    }

    @ApiOperation("分页查询最新博文")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Blog> getLatestBlog(Integer startIndex, Integer size) {
        String indexName = "tb_weblog_blog";

        // 封装排序的条件
        Map<String, String> sort = new HashMap<>();
        sort.put("sortCondition", "createTime");
        sort.put("sortWay", "DESC");

        SearchHit[] hits = restClient.getListByMust_Should_SortFromIndex(indexName, new HashMap<String, String>(), new HashMap<String, String>(), sort, startIndex, size);

        // 反序列化获取博文列表
        List<Blog> blogList = hitsToBlogList(hits);
        return blogList;

    }

    @ApiOperation("从 es库 or caffeine进程缓存 or redis 中根据浏览量降序查询出前100条")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Blog> getHotBlog(Integer startIndex, Integer size) {
        String key = CaffeineConstants.HOT_BLOG_KEY;
        // 下面代码的查询优先顺序是：caffeine -> redis -> es -> db
        List<Blog> blogList = (List<Blog>) caffeineCache.get( key, k -> {

            // 先查询 redis
            Object obj = redisTemplate.opsForValue().get(k);
            if ( Objects.nonNull(obj) ) {
                logger.info("get hotBlog from redis");
                return obj;
            }

            // redis中没有，则查询 es库
            List<Blog> blogList1 = getHotBlogList(startIndex, size);
            if ( !blogList1.isEmpty() && blogList1.size() != 0 && blogList1 != null ) {
                logger.info("get hotBlog from es库");

                // 进行异步处理，同步到redis和cache本地缓存中
                rabbitTemplate.convertAndSend(CaffeineMqConstants.CAFFEINE_EXCHANGE, CaffeineMqConstants.CAFFEINE_INSERT_KEY, key);

                return blogList1;
            }
            // es库中没有的话，最后查询 DB（但一般es库中都会有，因为实现了同步功能）
            return null;
        });
        return blogList;
    }

    @ApiOperation("博文浏览量+1")
    @Override
    public void blogReadAmountAddOne(String blogId) {
        blogMapper.blogReadAmountAddOne(blogId);
    }

    /**
     * 有个小疑问，不知道为什么，通过测试，发现有 两次 改动时，才会更新caffeine的缓存
     */
    @ApiOperation("更新caffeine 和 redis 中对热榜博文的缓存")
    @Override
    public void updateBlogInCaffeineAndRedis() {
        List<Blog> blogList = getHotBlogList(0, 100);
        // 修改 Redis
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(CaffeineConstants.HOT_BLOG_KEY, blogList, 24 * 60, TimeUnit.MINUTES);
        // 修改本地缓存
        caffeineCache.put(CaffeineConstants.HOT_BLOG_KEY, blogList);
        logger.info("update ---> Blog In Caffeine And Redis");
    }

    @ApiOperation("从es库中根据浏览量降序查询出前100条 热点博文")
    @Transactional(rollbackFor = Exception.class)
    public List<Blog> getHotBlogList(Integer startIndex, Integer size) {
        // 从es中查询
        String indexName = "tb_weblog_blog";
        // 封装排序的条件
        Map<String, String> sort = new HashMap<>();
        sort.put("sortCondition", "readAmount");
        sort.put("sortWay", "DESC");

        SearchHit[] hits = restClient.getListByMust_Should_SortFromIndex(indexName, new HashMap<String, String>(), new HashMap<String, String>(), sort, startIndex, size);

        List<Blog> blogList = new ArrayList<>();
        // 反序列
        for ( SearchHit hit : hits ) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
            Blog blog = JSON.parseObject(json, Blog.class);
            blogList.add(blog);
        }
        return blogList;
    }

    @ApiOperation("删除 caffeine 和 redis 中的 key")
    @Override
    public void deleteKeyOfCaffeineAndRedis(String key) {
        redisTemplate.delete(key);
        caffeineCache.invalidate(key);
    }

    @ApiOperation("最新优先搜索")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Blog> searchLatestBlog(String keyword, Integer startIndex, Integer size) {

        String indexName = EsConstants.TB_WEBLOG_BLOG;
        Set<String> keywordList = jieba.JIEBA( keyword );

        Map<String, String> esMust = new HashMap<>();
        esMust.put(EsConstants.ABLE_LOOK, "1");
        Map<String, String> sort = new HashMap<>();
        sort.put(EsConstants.SORT_CONDITION, EsConstants.CREATE_TIME);
        sort.put(EsConstants.SORT_WAY, "DESC");
        Map<String, Set<String>> esShouldList = new HashMap<>();
        esShouldList.put(EsConstants.BLOG_TITLE + " " + EsConstants.BLOG_DESCRIOPTION, keywordList);

        SearchHit[] hits = restClient.searchBlogList(indexName, esMust, esShouldList, sort, startIndex, size);
        // 反序列化获取博文列表
        List<Blog> blogList = hitsToBlogList(hits);
        return blogList;
    }

    @ApiOperation("最热优先搜索")
    @Override
    public List<Blog> searchHotBlog(String keyword, Integer startIndex, Integer size) {

        String indexName = EsConstants.TB_WEBLOG_BLOG;
        Set<String> keywordList = jieba.JIEBA( keyword );

        Map<String, String> esMust = new HashMap<>();
        esMust.put(EsConstants.ABLE_LOOK, "1");
        Map<String, String> sort = new HashMap<>();
        sort.put(EsConstants.SORT_CONDITION, EsConstants.READ_AMOUNT);
        sort.put(EsConstants.SORT_WAY, "DESC");
        Map<String, Set<String>> esShouldList = new HashMap<>();
        esShouldList.put(EsConstants.BLOG_TITLE + " " + EsConstants.BLOG_DESCRIOPTION, keywordList);

        SearchHit[] hits = restClient.searchBlogList(indexName, esMust, esShouldList, sort, startIndex, size);

        // 反序列化获取博文列表
        List<Blog> blogList = hitsToBlogList(hits);
        return blogList;
    }

    @ApiOperation("综合排序搜索（默认）")
    @Override
    public List<Blog> getBlogListByCombined(String keyword, Integer startIndex, Integer size) {

        String indexName = EsConstants.TB_WEBLOG_BLOG;
        Set<String> keywordList = jieba.JIEBA( keyword );

        Map<String, String> esMust = new HashMap<>();
        esMust.put(EsConstants.ABLE_LOOK, "1");
        Map<String, String> sort = new HashMap<>();
        sort.put(EsConstants.SORT_CONDITION, EsConstants.LIKE_READ_COMMENT_AMOUNT);
        sort.put(EsConstants.SORT_WAY, "DESC");
        Map<String, Set<String>> esShouldList = new HashMap<>();
        esShouldList.put(EsConstants.BLOG_TITLE + " " + EsConstants.BLOG_DESCRIOPTION, keywordList);

        SearchHit[] hits = restClient.searchBlogList(indexName, esMust, esShouldList, sort, startIndex, size);

        // 反序列化获取博文列表
        List<Blog> blogList = hitsToBlogList(hits);
        return blogList;
    }

    @ApiOperation("博文的点赞量-1")
    @Override
    public void blogLikeAmountReduceOne(String blogId) {
        blogMapper.blogLikeAmountReduceOne(blogId);
    }

    @ApiOperation("博文的点赞量+1")
    @Override
    public void blogLikeAmountAddOne(String blogId) {
        blogMapper.blogLikeAmountAddOne(blogId);
    }

    @ApiOperation("统计个人博文总数")
    @Override
    public Integer getOwnBlogTotalAmountByUserId(String userId) {
        return blogMapper.getOwnBlogTotalAmountByUserId(userId);
    }

    @ApiOperation("查询自己的审核不通过的博文（分页查询，按时间降序）")
    @Override
    public List<Blog> selectOwnFailBlog(String userId, Integer startIndex, Integer size) {
        return blogMapper.selectOwnFailBlog(userId, startIndex, size);
    }

    @ApiOperation("统计个人的审核失败的博文总数")
    @Override
    public Integer getOwnFailBlogTotalAmountByUserId(String userId) {
        return blogMapper.getOwnFailBlogTotalAmountByUserId(userId);
    }

    @ApiOperation("查询自己的草稿的博文（分页查询，按时间降序）")
    @Override
    public List<Blog> selectOwnDraftFailBlog(String userId, Integer startIndex, Integer size) {
        return blogMapper.selectOwnDraftFailBlog(userId, startIndex, size);
    }

    @ApiOperation("统计个人的草稿博文总数")
    @Override
    public Integer getOwnDraftBlogTotalAmountByUserId(String userId) {
        return blogMapper.getOwnDraftBlogTotalAmountByUserId(userId);
    }

    @ApiOperation("查询自己的待审核的博文（分页查询，按时间降序）")
    @Override
    public List<Blog> selectOwnNotAuditFailBlog(String userId, Integer startIndex, Integer size) {
        return blogMapper.selectOwnNotAuditBlog(userId, startIndex, size);
    }

    @ApiOperation("查询自己的待审核的博文（分页查询，按时间降序）")
    @Override
    public Integer getOwnNotAuditBlogTotalAmountByUserId(String userId) {
        return blogMapper.getOwnNotAuditBlogTotalAmountByUserId(userId);
    }

    @ApiOperation("从数据库中分类分页查询博文")
    @Override
    public List<Blog> getBlogListByCategoryId(String categoryId, Integer startIndex, Integer size) {
        return blogMapper.getBlogListByCategoryId(categoryId, startIndex, size);
    }

    @ApiOperation("统计该类的博文总数")
    @Override
    public Integer getBlogTotalAmountByCategoryId(String categoryId) {
        return blogMapper.getBlogTotalAmountByCategoryId(categoryId);
    }

    @ApiOperation("反序列化获取博文列表")
    public List<Blog> hitsToBlogList( SearchHit[] hits ) {
        List<Blog> blogList = new ArrayList<>();
        // 反序列
        for ( SearchHit hit : hits ) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
            Blog blog = JSON.parseObject(json, Blog.class);
            blogList.add(blog);
        }
        return blogList;
    }

}
