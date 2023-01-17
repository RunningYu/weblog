package com.weblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weblog.entity.Blog;
import com.weblog.entity.Column;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/10
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    @ApiOperation("发布博文")
    @Insert("insert into tb_weblog_blog( blog_id, user_id, title, publish_image, discription, content, tag, able_look, " +
            "status ) values( #{blogId}, #{userId}, #{title}, #{publishImage}, #{discription}, #{content}, #{tag}, " +
            "#{ableLook}, #{status} ) ")
    void publishBlog( String blogId, String userId, String title, String publishImage, String discription,
                      String content, String tag, Integer ableLook, Integer status );

    @ApiOperation("新建用户的博文 标签")
    @Insert("insert into tb_weblog_tags(user_id, tag_name) values( #{userId}, #{tag} )")
    void insertTag(String userId, String tag);

    @ApiOperation("新建用户的博文 专栏")
    @Insert("insert into tb_weblog_column( column_id, user_id, column_name, discription, cover ) values (#{columnId}, #{userId}, #{column}, " +
            "#{discription}, #{cover} )")
    void insertColumn( String columnId, String userId, String column, String discription, String cover );

    @ApiOperation("将博文与对应的专栏关联起来")
    @Insert("insert into tb_weblog_column_blog( blog_id, column_id ) values(#{blogId}, #{columnId} ) ")
    void insertBlogIntoColumn(String blogId, String columnId);

    @ApiOperation("用户的博文数量+1")
    @Update("update tb_weblog_user set blog_amount = blog_amount + 1 where id = #{userId} ")
    void blogAmountAddOne(String userId);

    @ApiOperation("根据博文id查询博文")
    @Select("select * from tb_weblog_blog where blog_id = #{blogId}")
    Blog getBlogFromDatabaseById(String blogId);

    @ApiOperation("博文的评论量+1")
    @Update("update tb_weblog_blog set comment_amount = comment_amount + 1, like_read_comment_amount = like_read_comment_amount + 1" +
            " where blog_id = #{blogId}")
    void blogCommentAmountAddOne(String blogId);

    @ApiOperation("获取该博文的评论量")
    @Select("select comment_amount from tb_weblog_blog where blog_id = #{blogId}")
    Integer getBlogCommentAmountByBlogId(String blogId);

    @ApiOperation("删除博文")
    @Delete("delete from tb_weblog_blog where blog_id = #{blogId}")
    void deleteBlogByBlogId(String blogId);

    @ApiOperation("更新用户的评论量（减去该博文博文的评论量）")
    @Update("update tb_weblog_user set comment_amount = comment_amount - #{commentAmount} where id = #{userId}")
    void decreaseCommentedAmountOfUser(String userId, Integer commentAmount);

    @ApiOperation("更新博文的评论量（减去（1 + 该评论的总回复量））")
    @Update("update tb_weblog_blog set comment_amount = comment_amount - #{amount},like_read_comment_amount = like_read_comment_amount - #{amount}" +
            " where blog_id = #{blogId}")
    void decreaseCommentAmountOfBlog(Integer amount, String blogId);

    @ApiOperation("更新博文的评论量（减去1）")
    @Update("update tb_weblog_blog set comment_amount = comment_amount - 1, like_read_comment_amount = like_read_comment_amount - 1" +
            " where blog_id = #{blogId}")
    void blogCommentAmountReduceOne(String blogId);

    @ApiOperation("修改博文")
    @Update("update tb_weblog_blog set title = #{title}, publish_image = #{publishImage}, discription = #{discription}," +
            " content = #{content}, tag = #{tag}, able_look = #{ableLook}, status = #{status} where blog_id = #{blogId} ")
    void updateBlogByBlogId(String blogId, String title, String publishImage, String discription, String content, String tag, Integer ableLook, Integer status );

    @ApiOperation("查询改博文的归属专栏")
    @Select( "select column_name from tb_weblog_column where column_id in (select column_id from tb_weblog_column_blog where blog_id = #{blogId})")
    List<String> getColumnIListByBlogId(String blogId);

    @ApiOperation("删除 专栏博文关联")
    @Delete("delete from tb_weblog_column_blog where blog_id = #{blogId} and column_id = #{columnId}")
    void deleteConnectOfBlogAndColumn(String blogId, String columnId);

    @ApiOperation("分页查询专栏中的博文")
    @Select("select * from tb_weblog_blog where blog_id in (select blog_id from tb_weblog_column_blog " +
            "where column_id = #{columnId} ) ORDER BY create_time DESC limit #{startIndex}, #{size}")
    List<Blog> getBlogListByColumnId(String columnId, Integer startIndex, Integer size);

    @ApiOperation("统计专栏中的博文总量")
    @Select("select count(*) from tb_weblog_column_blog where column_id = #{columnId}")
    Integer getBlogTotalAmountByColumnId(String columnId);

    @ApiOperation("分页查询个人用户的所有专栏")
    @Select("select * from tb_weblog_column where user_id = #{userId}")
    List<Column> getColumnListByUserId(String userId);

    @ApiOperation("统计用户的专栏总数")
    @Select("select count(*) from tb_weblog_column where user_id = #{userId}")
    Integer getColumnTotalAmountByUserId(String userId);

    @ApiOperation("从数据库中分类分页查询博文")
    @Select( "select * from tb_weblog_blog where blog_id in (select blog_id from tb_weblog_category_blog" +
            " where category_id = #{categoryId} and user_id = #{userId} ) ORDER BY create_time DESC limit #{startIndex}, #{size}")
    List<Blog> getBlogListByUserIdCategoryId(String userId, String categoryId, Integer startIndex, Integer size);

    @ApiOperation("统计用户的该类的博文总数")
    @Select("select count(*) from tb_weblog_category_blog where user_id = #{userId} and category_id = #{categoryId}")
    Integer getBlogTotalAmountByUserIdCategoryId(String userId, String categoryId);

    @ApiOperation("博文浏览量+1")
    @Update("update tb_weblog_blog set read_amount = read_amount + 1, like_read_comment_amount = like_read_comment_amount + 1" +
            " where blog_id = #{blogId}")
    void blogReadAmountAddOne(String blogId);

    @ApiOperation("分页查询 推荐博文（根据浏览量+点赞量+评论量 总和降序）")
    @Select("select * from tb_weblog_blog ORDER BY read_amount + like_amount + comment_amount DESC limit #{startIndex}, #{size}")
    List<Blog> getAdviceBlogList(Integer startIndex, Integer size);

    @ApiOperation("博文的点赞量-1")
    @Update("update tb_weblog_blog set like_amount = like_amount - 1, like_read_comment_amount = like_read_comment_amount - 1" +
            " where blog_id = #{blogId}")
    void blogLikeAmountReduceOne(String blogId);

    @ApiOperation("博文的点赞量+1")
    @Update("update tb_weblog_blog set like_amount = like_amount + 1, like_read_comment_amount = like_read_comment_amount + 1" +
            " where blog_id = #{blogId}")
    void blogLikeAmountAddOne(String blogId);

    @ApiOperation("统计个人通过的博文总数")
    @Select("select count(*) from tb_weblog_blog where user_id = #{userId} and status = 1")
    Integer getOwnBlogTotalAmountByUserId(String userId);

    @ApiOperation("查询自己的审核不通过的博文（分页查询，按时间降序）")
    @Select("select * from tb_weblog_blog where user_id = #{userId} and status = 0 ORDER BY create_time DESC limit #{startIndex}, #{size}")
    List<Blog> selectOwnFailBlog(String userId, Integer startIndex, Integer size);

    @ApiOperation("统计个人的审核失败的博文总数")
    @Select("select count(*) from tb_weblog_blog where user_id = #{userId} and status = 0")
    Integer getOwnFailBlogTotalAmountByUserId(String userId);

    @ApiOperation("查询自己的草稿的博文（分页查询，按时间降序）")
    @Select("select * from tb_weblog_blog where user_id = #{userId} and status = 3 ORDER BY create_time DESC limit #{startIndex}, #{size}")
    List<Blog> selectOwnDraftFailBlog(String userId, Integer startIndex, Integer size);

    @ApiOperation("统计个人的草稿博文总数")
    @Select("select count(*) from tb_weblog_blog where user_id = #{userId} and status = 3")
    Integer getOwnDraftBlogTotalAmountByUserId(String userId);

    @ApiOperation("查询自己的待审核的博文（分页查询，按时间降序）")
    @Select("select * from tb_weblog_blog where user_id = #{userId} and status = 2 ORDER BY create_time DESC limit #{startIndex}, #{size}")
    List<Blog> selectOwnNotAuditBlog(String userId, Integer startIndex, Integer size);

    @ApiOperation("统计个人的待审核的博文总数")
    @Select("select count(*) from tb_weblog_blog where user_id = #{userId} and status = 2")
    Integer getOwnNotAuditBlogTotalAmountByUserId(String userId);

    @ApiOperation("从数据库中分类分页查询博文")
    @Select( "select * from tb_weblog_blog where blog_id in (select blog_id from tb_weblog_category_blog" +
            " where category_id = #{categoryId} ) ORDER BY create_time DESC limit #{startIndex}, #{size}")
    List<Blog> getBlogListByCategoryId(String categoryId, Integer startIndex, Integer size);

    @ApiOperation("统计该类的博文总数")
    @Select("select count(*) from tb_weblog_category_blog where category_id = #{categoryId} ")
    Integer getBlogTotalAmountByCategoryId(String categoryId);
}
