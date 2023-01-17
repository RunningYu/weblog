package com.weblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weblog.entity.Blog;
import com.weblog.entity.Column;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/10
 */
public interface IBlogService extends IService<Blog> {
    @ApiOperation("发布博文")
    void publishBlog(String blogId, String userId, String title, String publishImage, String discription,
                     String content, String tag, Integer ableLook, Integer status );

    @ApiOperation("新建用户的博文 标签")
    void insertTag( String userId, String tag );

    @ApiOperation("新建用户的博文 专栏")
    void insertColumn( String columnId, String userId, String column, String discription, String cover );

    @ApiOperation("将博文与对应的专栏关联起来")
    void insertBlogIntoColumn(String blogId, List<String> columnIdList );

    @ApiOperation("用户的博文数量+1")
    void blogAmountAddOne(String userId);

    @ApiOperation("同步博文到es索引表中")
    void insertBlogToIndexById(String blogId);

    @ApiOperation("博文的评论量+1")
    void blogCommentAmountAddOne(String blogId);

    @ApiOperation("获取该博文的评论量")
    Integer getBlogCommentAmountByBlogId(String blogId);

    @ApiOperation("删除博文")
    void deleteBlogByBlogId(String blogId);

    @ApiOperation("更新用户的评论量（减去该博文博文的评论量）")
    void decreaseCommentedAmountOfUser(String userId, Integer commentAmount);

    @ApiOperation("es同步删除博文")
    void deleteBlogOfIndexByBlogId(String id);

    @ApiOperation("更新博文的评论量（减去（1 + 该评论的总回复量））")
    void decreaseCommentAmountOfBlog(Integer amount, String blogId);

    @ApiOperation("更新博文的评论量（减去1）")
    void blogCommentAmountReduceOne(String blogId);

    @ApiOperation("修改博文")
    void updateBlogByBlogId(String blogId, String title, String publishImage, String discription, String content, String tag, Integer ableLook, Integer status );

    @ApiOperation("查询改博文的归属专栏")
    List<String> getColumnIListByBlogId(String blogId);

    @ApiOperation("删除 专栏博文关联")
    void deleteConnectOfBlogAndColumn(String blogId, String columnId);

    @ApiOperation("从数据库中查询一篇博文")
    Blog getBlogFromDatabaseById(String blogId);

    @ApiOperation("es分页查询自己的博文")
    List<Blog> getOwnBlogListByUserId(String userId, Integer startIndex, Integer size);

    @ApiOperation("分页查询专栏中的博文")
    List<Blog> getBlogListByColumnId(String columnId, Integer startIndex, Integer size);

    @ApiOperation("统计专栏中的博文总量")
    Integer getBlogTotalAmountByColumnId(String columnId);

    @ApiOperation("分页查询个人用户的所有专栏")
    List<Column> getColumnListByUserId(String userId);

    @ApiOperation("统计用户的专栏总数")
    Integer getColumnTotalAmountByUserId(String userId);

    @ApiOperation("从数据库中分类分页查询博文")
    List<Blog> getBlogListByUserIdCategoryId(String userId, String categoryId, Integer startIndex, Integer size);

    @ApiOperation("统计用户的该类的博文总数")
    Integer getBlogTotalAmountByUserIdCategoryId(String userId, String categoryId);

    @ApiOperation("分页查询 推荐博文")
    List<Blog> getAdviceBlogList(Integer startIndex, Integer size);

    @ApiOperation("分页查询最新博文")
    List<Blog> getLatestBlog(Integer startIndex, Integer size);

    @ApiOperation("从 es库 or caffeine进程缓存 or redis 中查询出前100条")
    List<Blog> getHotBlog(Integer startIndex, Integer size);

    @ApiOperation("博文浏览量+1")
    void blogReadAmountAddOne(String blogId);

    @ApiOperation("更新caffeine 和 redis 中对热榜博文的缓存")
    void updateBlogInCaffeineAndRedis();

    @ApiOperation("删除 caffeine 和 redis 中的key")
    void deleteKeyOfCaffeineAndRedis(String key);

    @ApiOperation("最新优先搜索")
    List<Blog> searchLatestBlog(String keyword, Integer startIndex, Integer size);

    @ApiOperation("最热优先搜索")
    List<Blog> searchHotBlog(String keyword, Integer startIndex, Integer size);

    @ApiOperation("综合排序搜索（默认）")
    List<Blog> getBlogListByCombined(String keyword, Integer startIndex, Integer size);

    @ApiOperation("博文的点赞量-1")
    void blogLikeAmountReduceOne(String blogId);

    @ApiOperation("博文点赞量+1")
    void blogLikeAmountAddOne(String blogId);

    @ApiOperation("统计个人博文总数")
    Integer getOwnBlogTotalAmountByUserId(String userId);

    @ApiOperation("查询自己的审核不通过的博文（分页查询，按时间降序）")
    List<Blog> selectOwnFailBlog(String userId, Integer startIndex, Integer size);

    @ApiOperation("统计个人的审核失败的博文总数")
    Integer getOwnFailBlogTotalAmountByUserId(String userId);

    @ApiOperation("查询自己的草稿的博文（分页查询，按时间降序）")
    List<Blog> selectOwnDraftFailBlog(String userId, Integer startIndex, Integer size);

    @ApiOperation("统计个人的草稿博文总数")
    Integer getOwnDraftBlogTotalAmountByUserId(String userId);

    @ApiOperation("查询自己的待审核的博文（分页查询，按时间降序）")
    List<Blog> selectOwnNotAuditFailBlog(String userId, Integer startIndex, Integer size);

    @ApiOperation("统计个人的待审核的博文总数")
    Integer getOwnNotAuditBlogTotalAmountByUserId(String userId);

    @ApiOperation("从数据库中分类分页查询博文")
    List<Blog> getBlogListByCategoryId(String categoryId, Integer startIndex, Integer size);

    @ApiOperation("统计该类的博文总数")
    Integer getBlogTotalAmountByCategoryId(String categoryId);
}
