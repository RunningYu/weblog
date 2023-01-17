package com.weblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weblog.entity.Comment;
import com.weblog.entity.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/12
 */
public interface ICommentService extends IService<Comment> {

    @ApiOperation("添加评论")
    void insertComment(String commentId, String userId, String blogId, String content);

    @ApiOperation("添加回复")
    void insertReply(String replyId, String commentId, String replyUserId, String commentUserId, String blogId, String content);

    @ApiOperation("评论的回复量+1")
    void commentReplyAmountAddOne(String commentId);

    @ApiOperation("同步es的 评论 索引表")
    void insertCommentToIndextByCommentId(String commentId);

    @ApiOperation("同步es的 回复 索引表")
    void insertReplyToIndexByReplyId(String replyId);

    @ApiOperation("删除该博文对应的所有评论")
    void deleteAllCommentOfBlogByBlogId(String blogId);

    @ApiOperation("删除博文对应的所有评论的回复")
    void deleteAllReplyOfBlogByBlogId(String blogId);

    @ApiOperation("获取出所有的评论的主键id")
    List<String> selectCommentKeyIdListByBlogId(String blogId);

    @ApiOperation("获取出所有的回复的主键id")
    List<String> selectReplyKeyIdListByBlogId(String blogId);

    @ApiOperation("es同步删除评论")
    void deleteCommentOfIndexByCommentKeyId(String id);

    @ApiOperation("es同步删除评论")
    void deleteReplyOfIndexByReplyKeyId(String id);

    @ApiOperation("删除评论的所用回复")
    void deleteAllReplyByCommentId(String commentId);

    @ApiOperation("获取该评论的所有回复的主键id")
    List<String> selectReplyKeyIdListByCommentId(String commentId);

    @ApiOperation("更新评论的回复量（减去1）")
    void commentReplyAmountReduceOne(String commentId);

    @ApiOperation("评论获赞量-1")
    void commentLikedAmountReduceOne(String commentId);

    @ApiOperation("评论获赞量+1")
    void commentLikedAmountAddOne(String commentId);

    @ApiOperation("回复获赞量-1")
    void replyLikedAmountReduceOne(String replyId);

    @ApiOperation("回复获赞量+1")
    void replyLikedAmountAddOne(String replyId);

    @ApiOperation("es分页查询评论")
    List<Comment> getCommentListByBlogId(String blogId, Integer startIndex, Integer size);

    @ApiOperation("es分页查询回复")
    List<Reply> getReplyListByCommentId(String commentId, Integer startIndex, Integer size);

    @ApiOperation("删除回复")
    void deleteReplyByReplyId(String replyId);

    @ApiOperation("获取全部的reply")
    List<Reply> getAllReply();

    @ApiOperation("获取评论总数")
    Integer getCommentTotalByBlogId(String blogId);

    @ApiOperation("获取回复总数")
    Integer getReplyTotalByCommentId(String commentId);

    @ApiOperation("从DB分页查询评论")
    List<Comment> getCommentListByBlogIdFromDB(String blogId, Integer startIndex, Integer size);

    @ApiOperation("从DB分页查询回复")
    List<Reply> getReplyListByCommentIdFromDB(String commentId, Integer startIndex, Integer size);
}
