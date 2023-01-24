package com.weblog.controller.PersonController;

import com.weblog.VO.requestVo.VoBlog;
import com.weblog.VO.responseVo.VoColumn;
import com.weblog.VO.responseVo.VoblogResponse;
import com.weblog.common.JsonResult;
import com.weblog.common.MqCorrelationDate;
import com.weblog.constants.MqConstants.*;
import com.weblog.constants.other.CaffeineConstants;
import com.weblog.entity.Blog;
import com.weblog.entity.Column;
import com.weblog.entity.User;
import com.weblog.service.IBlogService;
import com.weblog.service.ICommentService;
import com.weblog.service.IUserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/10
 */
@ApiModel(description = "博文相关的api（发布，更改，删除，查询...）")
@RestController
@RequestMapping("/blog")
public class blogController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBlogService blogService;

    /**
     * 注入发送小的API
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation("发布博文")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/publishBlog")
    public JsonResult publicBlog( @RequestBody VoBlog voBlog ) {

        if ( !voBlog.getBlogId().equals( "" ) && voBlog.getBlogId() != null) {
            JsonResult jsonResult = updateBlogByBlogId( voBlog );
            return jsonResult;
        }

        String[] tags = voBlog.getTag().split(" ");
        String blogId = String.valueOf(UUID.randomUUID());
        blogService.publishBlog( blogId, voBlog.getUserId(), voBlog.getTitle(), voBlog.getPublishImage(), voBlog.getDiscription(),
                voBlog.getContent(), voBlog.getTag(), voBlog.getAbleLook(), voBlog.getStatus() );

        // 将博文与对应的专栏关联起来
        blogService.insertBlogIntoColumn( blogId, voBlog.getColumnIdList() );

        // 博文同步到ES索引库中
        // 数据同步请求发送                                  交换机                       RoutingLKey     内容
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, blogId, MqCorrelationDate.getCorrelationData());

        // 用户的博文数量+1
        blogService.blogAmountAddOne(voBlog.getUserId());
        // User实体跟新到ES索引库中
        // 数据同步请求发送
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, voBlog.getUserId(), MqCorrelationDate.getCorrelationData());

        if ( blogService.count() < 100 ) {
            // 同步 caffeine 和 redis 二级缓存中的热榜博文
            rabbitTemplate.convertAndSend(CaffeineMqConstants.CAFFEINE_EXCHANGE, CaffeineMqConstants.CAFFEINE_INSERT_KEY, CaffeineConstants.HOT_BLOG_KEY, MqCorrelationDate.getCorrelationData());
        }
        return JsonResult.success("发布成功", 200);
    }


    /**
     * 后面将 数据库和es 中删除博文对应的评论、回复 优化成定时（每日凌晨3:00）进行任务调度删除，以减轻白天时对数据库和Mq消息队列的压力
     */
    @ApiOperation("删除博文（根据blogId）")
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/deleteBlogByBlogId")
    public JsonResult deleteBlogByBlogId( @RequestParam("userId") String userId,
                                          @RequestParam("blogId") String blogId,
                                          @RequestParam("id") String id) {

        // 获取该博文的评论量
        Integer commentAmount = blogService.getBlogCommentAmountByBlogId(blogId);
        // 获取出所有的评论的主键id
        List<String> comment_keyIds = commentService.selectCommentKeyIdListByBlogId(blogId);
        // 获取出所有的回复的主键id
        List<String> reply_keyIds = commentService.selectReplyKeyIdListByBlogId(blogId);

        // 删除博文
        blogService.deleteBlogByBlogId( blogId );
        // 更新用户的评论量（减去该博文博文的评论量）
        blogService.decreaseCommentedAmountOfUser(userId, commentAmount);
        // 删除该博文对应的所有评论
        commentService.deleteAllCommentOfBlogByBlogId(blogId);
        // 删除博文对应的所有评论的回复
        commentService.deleteAllReplyOfBlogByBlogId(blogId);

        // 同步es
        // 同步删除博文
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_DELETE_KEY, id, MqCorrelationDate.getCorrelationData());
        // 同步删除评论
        for ( String id1 : comment_keyIds ) {
            rabbitTemplate.convertAndSend(CommentMqConstants.COMMENT_EXCHANGE, CommentMqConstants.COMMENT_DELETE_KEY, id1, MqCorrelationDate.getCorrelationData());
        }
        // 同步删除回复
        for ( String id1 : reply_keyIds ) {
            rabbitTemplate.convertAndSend(ReplyMqConstants.REPLY_EXCHANGE, ReplyMqConstants.REPLY_DELETE_KEY, id1, MqCorrelationDate.getCorrelationData());
        }
        // 同步用户的所获评论量（减去该博文博文的评论量）
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, userId, MqCorrelationDate.getCorrelationData());

        // 同步 caffeine 和 redis 二级缓存中的热榜博文
        rabbitTemplate.convertAndSend(CaffeineMqConstants.CAFFEINE_EXCHANGE, CaffeineMqConstants.CAFFEINE_INSERT_KEY, CaffeineConstants.HOT_BLOG_KEY, MqCorrelationDate.getCorrelationData());


        return JsonResult.success("删除成功",200);
    }



    @ApiOperation("修改博文")
    @Transactional(rollbackFor = Exception.class)
//    @PutMapping("/updateBlogByBlogId")
    public JsonResult updateBlogByBlogId(VoBlog voBlog) {



        // 修改博文
        blogService.updateBlogByBlogId(voBlog.getBlogId(), voBlog.getTitle(), voBlog.getPublishImage(), voBlog.getDiscription(), voBlog.getContent(),
                voBlog.getTag(), voBlog.getAbleLook(), voBlog.getStatus() );
        // 博文同步到ES索引库中
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, voBlog.getBlogId(), MqCorrelationDate.getCorrelationData());

        // 去重，找出新放到的专栏（需数据库建立关联） 和 去掉的专栏（需数据库删除关联）
        // 查询改博文之前的归属专栏
        List<String> preColumnIdList = blogService.getColumnIListByBlogId(voBlog.getBlogId());
        // 找出修改后新添加的归属专栏（用 现在的 去掉 之前的）
        List<String> nowColumnIdList = voBlog.getColumnIdList();
        nowColumnIdList.removeAll(preColumnIdList);
        // 找出修改后删除的归属专栏（用 之前的 去掉 现在的）
        preColumnIdList.removeAll(voBlog.getColumnIdList());
        // 新建 专栏博文关联
        blogService.insertBlogIntoColumn(voBlog.getBlogId(), nowColumnIdList);
        // 删除 专栏博文关联
        for ( String columnId : preColumnIdList ) {
            blogService.deleteConnectOfBlogAndColumn(voBlog.getBlogId(), columnId);
        }

        // 同步 caffeine 和 redis 二级缓存中的热榜博文
        rabbitTemplate.convertAndSend(CaffeineMqConstants.CAFFEINE_EXCHANGE, CaffeineMqConstants.CAFFEINE_INSERT_KEY, CaffeineConstants.HOT_BLOG_KEY, MqCorrelationDate.getCorrelationData());

        return JsonResult.error("草稿转为发布待审核状态", 200);
    }


    @ApiOperation("（取消）点赞博文")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/likeBlog")
    public JsonResult likeBlog(@RequestParam("userId") String userId,
                               @RequestParam("blogId") String blogId,
                               @RequestParam("blogUserId") String blogUserId) {
        // 判断是否点赞过改博文
        boolean flag = userService.judgeHaveLikedBlog(userId, blogId);
        if ( flag ) {
            // 点赞过了，第二次点的时候就是取消点赞
            userService.deleteLikeBlog(userId, blogId);
            // 用户获赞量-1
            userService.userLikedAmountReduceOne(blogUserId);
            // 博文的点赞量-1
            blogService.blogLikeAmountReduceOne(blogId);
            // es同步user实体
            rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, blogUserId, MqCorrelationDate.getCorrelationData());
            // es同步blog
            rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, blogId, MqCorrelationDate.getCorrelationData());
            return JsonResult.success("取消点赞成功", 200);
        }
        // 创建用户点赞博文关系
        userService.createLikeBlog(userId, blogId);
        // 用户获赞量+1
        userService.userLikedAmountAddOne(blogUserId);
        // 博文的点赞量+1
        blogService.blogLikeAmountAddOne(blogId);
        // es同步user实体
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, blogUserId, MqCorrelationDate.getCorrelationData());
        // es同步blog
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, blogId, MqCorrelationDate.getCorrelationData());
        return JsonResult.success("点赞成功", 200);
    }


    @ApiOperation("新建用户的博文 标签")
    @PostMapping("/insertTag")
    public JsonResult insertTag(@RequestParam("userId") String userId, @RequestParam("tag") String tag) {
        blogService.insertTag( userId, tag );
        return JsonResult.success("新建成功", 200);
    }

    @ApiOperation("新建用户的博文 专栏")
    @PostMapping("/insertColumn")
    public JsonResult insertColumn(@RequestParam("userId") String userId,
                                   @RequestParam("column") String column,
                                   @RequestParam("discription") String discription,
                                   @RequestParam("cover") String cover ) {
        String columnId = String.valueOf(UUID.randomUUID());
        blogService.insertColumn( columnId, userId, column, discription, cover );
        return JsonResult.success("新建成功", 200);
    }

    @ApiOperation("查询一篇博文的详细情况")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectOneBlog")
    public JsonResult selectOneBlog(@RequestParam("userId") String userId, @RequestParam("blogId") String blogId) {
        JsonResult jsonResult = new JsonResult(200);
        // 查询博文
        Blog blog = blogService.getBlogFromDatabaseById(blogId);
        User user = userService.getById(blog.getUserId());
        VoblogResponse voblogResponse = new VoblogResponse(blog);
        voblogResponse.setUserName( user.getUserName() );
        voblogResponse.setHeadshot( user.getHeadshot() );
        Boolean havaLiked = userService.judgeHaveLikedBlog( userId, blogId );
        // 判断用户是否点赞过了
        voblogResponse.setHaveLiked(havaLiked);
        jsonResult.setData(voblogResponse);
        return jsonResult;
    }

    @ApiOperation("查询自己的审核不通过的博文（分页查询，按时间降序）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectOwnFailBlog")
    public JsonResult selectOwnFailBlog(@RequestParam("userId") String userId,
                                    @RequestParam("page") String page1,
                                    @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;

        // 查询博文
        List<Blog> blogList = blogService.selectOwnFailBlog(userId, startIndex, size);
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        Integer total = blogService.getOwnFailBlogTotalAmountByUserId(userId);
        jsonResult.add("total", total);
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.setMsg("map中的 total : 用户的审核不通过的总博文数量");
        return jsonResult;
    }

    @ApiOperation("查询自己的草稿的博文（分页查询，按时间降序）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectOwnDraftFailBlog")
    public JsonResult selectOwnDraftFailBlog(@RequestParam("userId") String userId,
                                        @RequestParam("page") String page1,
                                        @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;

        // 查询博文
        List<Blog> blogList = blogService.selectOwnDraftFailBlog(userId, startIndex, size);
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        Integer total = blogService.getOwnDraftBlogTotalAmountByUserId(userId);
        jsonResult.add("total", total);
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.setMsg("map中的 total : 用户的草稿的总博文数量");
        return jsonResult;
    }

    @ApiOperation("查询自己的待审核的博文（分页查询，按时间降序）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectOwnNotAuditFailBlog")
    public JsonResult selectOwnNotAuditFailBlog(@RequestParam("userId") String userId,
                                             @RequestParam("page") String page1,
                                             @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;

        // 查询博文
        List<Blog> blogList = blogService.selectOwnNotAuditFailBlog(userId, startIndex, size);
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        Integer total = blogService.getOwnNotAuditBlogTotalAmountByUserId(userId);
        jsonResult.add("total", total);
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.setMsg("map中的 total : 用户的待审核的的总博文数量");
        return jsonResult;
    }

    @ApiOperation("查询自己的博文（分页查询，按时间降序）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectOwnBlog")
    public JsonResult selectOwnBlog(@RequestParam("userId") String userId,
                                    @RequestParam("page") String page1,
                                    @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;

        // 查询博文
        List<Blog> blogList = blogService.getOwnBlogListByUserId(userId, startIndex, size);
        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        Integer total = blogService.getOwnBlogTotalAmountByUserId(userId);
        jsonResult.add("total", total);
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setMsg("map中的 total : 个人的审核通过的博文总数");
        return jsonResult;
    }


    @ApiOperation("根据专栏查询博文（分页查询，按时间降序）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectBlogListByColumnId")
    public JsonResult selectBlogListByColumnId(@RequestParam("userId") String userId,
                                               @RequestParam("columnId") String columnId,
                                               @RequestParam("page") String page1,
                                               @RequestParam("size") String size1) {

        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;
        // 从数据库中查询
        List<Blog> blogList = blogService.getBlogListByColumnId(columnId, startIndex, size);
        // 统计专栏的博文总数
        Integer total = blogService.getBlogTotalAmountByColumnId(columnId);
        jsonResult.add("total", total);

        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setData(blogList);
        jsonResult.setMsg("map 中的 total 表示专栏的博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过");

        return jsonResult;
    }

    @ApiOperation("查询个人用户的所有专栏情况（分页查询）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectColumnByUserId")
    public JsonResult selectColumnByUserId(@RequestParam("userId") String userId) {


        JsonResult jsonResult = new JsonResult(200);

//        Integer page = Integer.parseInt(page1);
//        Integer size = Integer.parseInt(size1);
//        Integer startIndex = (page - 1) * size;

        List<Column> columns = blogService.getColumnListByUserId(userId);
        List<VoColumn> voColumns = new ArrayList<>();
        for ( Column column : columns ) {
            Integer blogAmount = blogService.getBlogTotalAmountByColumnId( column.getColumnId() );
            voColumns.add( new VoColumn(column, blogAmount));
        }
        jsonResult.setData(voColumns);
        // 统计用户的专栏总数
        Integer total = blogService.getColumnTotalAmountByUserId(userId);
        jsonResult.add("total", total);
        jsonResult.setMsg("map 中的 total 表示用户的专栏总数");
        return jsonResult;
    }

    @ApiOperation("分类查询博文（分页查询）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectBlogListByUserIdCategoryId")
    public JsonResult selectBlogListByUserIdCategoryId(@RequestParam("userId") String userId,
                                                       @RequestParam("categoryId") String categoryId,
                                                       @RequestParam("page") String page1,
                                                       @RequestParam("size") String size1){
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;
        // 从数据库中分类分页查询博文
        List<Blog> blogList = blogService.getBlogListByUserIdCategoryId(userId, categoryId, startIndex, size);
        // 统计用户的该类的博文总数
        Integer total = blogService.getBlogTotalAmountByUserIdCategoryId(userId, categoryId);
        jsonResult.add("total", total);

        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);

        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setData(blogList);
        jsonResult.setMsg("map 中的 total 表示专栏的博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过");
        return jsonResult;
    }

    @ApiOperation("博文浏览量+1")
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/blogReadAmountAddOne")
    public JsonResult blogReadAmountAddOne(@RequestParam("blogId") String blogId) {
        blogService.blogReadAmountAddOne(blogId);
        Blog blog = blogService.getBlogFromDatabaseById(blogId);
        userService.userReadAmountAddOne(blog.getUserId());
        // 同步 caffeine 和 redis 二级缓存中的热榜博文
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, blogId, MqCorrelationDate.getCorrelationData());
        String key = CaffeineConstants.HOT_BLOG_KEY;
        rabbitTemplate.convertAndSend(CaffeineMqConstants.CAFFEINE_EXCHANGE, CaffeineMqConstants.CAFFEINE_INSERT_KEY,key, MqCorrelationDate.getCorrelationData());
        return JsonResult.success("浏览量+1", 200);
    }


}
