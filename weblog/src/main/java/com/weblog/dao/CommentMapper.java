package com.weblog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weblog.entity.Comment;
import com.weblog.entity.Reply;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/12
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @ApiOperation("添加评论")
    @Insert("insert into tb_weblog_comment( comment_id, user_id, blog_id, content ) values (#{commentId}, #{userId}, " +
            "#{blogId}, #{content})")
    void insertComment(String commentId, String userId, String blogId, String content);

    @ApiOperation("添加回复")
    @Insert("insert into tb_weblog_reply(reply_id, comment_id, reply_user_id, comment_user_id, blog_id, content) " +
            "values(#{replyId},#{commentId},#{replyUserId},#{commentUserId},#{blogId},#{content}) ")
    void insertReply(String replyId, String commentId, String replyUserId, String commentUserId, String blogId, String content);

    @ApiOperation("评论的回复量+1")
    @Update("update tb_weblog_comment set comment_reply_amount = comment_reply_amount + 1 where comment_id = #{commentId}")
    void commentReplyAmountAddOne(String commentId);

    @ApiOperation("根据评论id从数据库中查询评论")
    @Select("select * from tb_weblog_comment where comment_id = #{commentId}")
    Comment getCommentByCommentId(String commentId);

    @ApiOperation("根据回复id从数据库中查询回复")
    @Select("select * from tb_weblog_reply where reply_id = #{replyId}")
    Reply getReplyByReplyId(String replyId);

    @ApiOperation("删除该博文对应的所有评论")
    @Delete("delete from tb_weblog_comment where blog_id = #{blogId}")
    void deleteAllCommentOfBlogByBlogId(String blogId);

    @ApiOperation("删除博文对应的所有评论的回复")
    @Delete("delete from tb_weblog_reply where blog_id = #{blogId}")
    void deleteAllReplyOfBlogByBlogId(String blogId);

    @ApiOperation("获取出所有的评论的主键id")
    @Select("select id from tb_weblog_comment where blog_id = #{blogId}")
    List<String> selectCommentKeyIdListByBlogId(String blogId);

    @ApiOperation("获取出所有的回复的主键id")
    @Select("select id from tb_weblog_reply where blog_id = #{blogId}")
    List<String> selectReplyKeyIdListByBlogId(String blogId);

    @ApiOperation("删除评论的所用回复")
    @Delete("delete from tb_weblog_reply where comment_id = #{commentId}")
    void deleteAllReplyByCommentId(String commentId);

    @ApiOperation("获取该评论的所有回复的主键id")
    @Select("select id from tb_weblog_reply where comment_id = #{commentId}")
    List<String> selectReplyKeyIdListByCommentId(String commentId);

    @ApiOperation("更新评论的回复量（减去1）")
    @Update("update tb_weblog_comment set comment_reply_amount = comment_reply_amount - 1 where comment_id = #{commentId}")
    void commentReplyAmountReduceOne(String commentId);

    @ApiOperation("评论获赞量-1")
    @Update("update tb_weblog_comment set comment_like_amount = comment_like_amount - 1 where comment_id = #{commentId}")
    void commentLikedAmountReduceOne(String commentId);

    @ApiOperation("评论获赞量+1")
    @Update("update tb_weblog_comment set comment_like_amount = comment_like_amount + 1 where comment_id = #{commentId}")
    void commentLikedAmountAddOne(String commentId);

    @ApiOperation("回复获赞量-1")
    @Update("update tb_weblog_reply set like_amount = like_amount - 1 where reply_id = #{replyId}")
    void replyLikedAmountReduceOne(String replyId);

    @ApiOperation("回复获赞量+1")
    @Update("update tb_weblog_reply set like_amount = like_amount + 1 where reply_id = #{replyId}")
    void replyLikedAmountAddOne(String replyId);

    @ApiOperation("删除回复")
    @Delete("delete from tb_weblog_reply where reply_id = #{replyId}")
    void deleteReplyByReplyId(String replyId);

    @ApiOperation("获取全部的reply")
    @Select("select * from tb_weblog_reply")
    List<Reply> getAllReply();

    @ApiOperation("获取评论总数")
    @Select("select count(*) from tb_weblog_comment where blog_id = #{blogId}")
    Integer getCommentTotalByBlogId(String blogId);

    @ApiOperation("获取回复总数")
    @Select("select count(*) from tb_weblog_reply where comment_id = #{commentId}")
    Integer getReplyTotalByCommentId(String commentId);

    @ApiOperation("从DB分页查询评论")
    @Select("select * from tb_weblog_comment where blog_id = #{blogId} ORDER BY create_time DESC limit #{startIndex}, #{size}")
    List<Comment> getCommentListByBlogIdFromDB(String blogId, Integer startIndex, Integer size);

    @ApiOperation("从DB分页查询回复")
    @Select("select * from tb_weblog_reply where comment_id = #{commentId} ORDER BY create_time DESC limit #{startIndex}, #{size}")
    List<Reply> getReplyListByCommentIdFromDB(String commentId, Integer startIndex, Integer size);
}
