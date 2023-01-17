package com.weblog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weblog.common.RestClient;
import com.weblog.dao.CommentMapper;
import com.weblog.entity.Comment;
import com.weblog.entity.Reply;
import com.weblog.service.ICommentService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/12
 */
@ApiModel(description = "评论、回复")
@Service
public class ICommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private CommentMapper commentMapper;

    @ApiOperation("添加评论")
    @Override
    public void insertComment(String commentId, String userId, String blogId, String content) {
        commentMapper.insertComment( commentId, userId, blogId, content);
    }

    @ApiOperation("添加回复")
    @Override
    public void insertReply(String replyId, String commentId, String replyUserId, String commentUserId, String blogId, String content) {
        commentMapper.insertReply( replyId, commentId, replyUserId, commentUserId, blogId, content);
    }

    @ApiOperation("评论的回复量+1")
    @Override
    public void commentReplyAmountAddOne(String commentId) {
        commentMapper.commentReplyAmountAddOne(commentId);
    }

    @ApiOperation("同步es的 评论 索引表")
    @Override
    public void insertCommentToIndextByCommentId(String commentId) {
        String indexName = "tb_weblog_comment";
        try {
            // 根据评论id从数据库中查询评论
            Comment comment = getCommentByCommentId(commentId);

            // 1.准备Request对象
            IndexRequest request = new IndexRequest(indexName).id(comment.getId().toString());
            // 2.准备Json文档
            request.source(JSON.toJSONString(comment), XContentType.JSON);
            // 3.发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("同步es的 评论 索引表")
    @Override
    public void insertReplyToIndexByReplyId(String replyId) {
        String indexName = "tb_weblog_reply";
        try {
            // 根据回复id从数据库中查询回复
            Reply reply = getReplyByReplyId(replyId);

            // 1.准备Request对象
            IndexRequest request = new IndexRequest(indexName).id(reply.getId().toString());
            // 2.准备Json文档
            request.source(JSON.toJSONString(reply), XContentType.JSON);
            // 3.发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("删除该博文对应的所有评论")
    @Override
    public void deleteAllCommentOfBlogByBlogId(String blogId) {
        commentMapper.deleteAllCommentOfBlogByBlogId(blogId);
    }

    @ApiOperation("删除博文对应的所有评论的回复")
    @Override
    public void deleteAllReplyOfBlogByBlogId(String blogId) {
        commentMapper.deleteAllReplyOfBlogByBlogId(blogId);
    }

    @ApiOperation("获取出所有的评论的主键id")
    @Override
    public List<String> selectCommentKeyIdListByBlogId(String blogId) {
        return commentMapper.selectCommentKeyIdListByBlogId(blogId);
    }

    @ApiOperation("获取出所有的回复的主键id")
    @Override
    public List<String> selectReplyKeyIdListByBlogId(String blogId) {
        return commentMapper.selectReplyKeyIdListByBlogId(blogId);
    }

    @ApiOperation("es同步删除评论")
    @Override
    public void deleteCommentOfIndexByCommentKeyId(String id) {
        restClient.deleteDocumentById("tb_weblog_comment", id);
    }

    @ApiOperation("es同步删除评论")
    @Override
    public void deleteReplyOfIndexByReplyKeyId(String id) {
        restClient.deleteDocumentById("tb_weblog_reply", id);
    }

    @ApiOperation("删除评论的所用回复")
    @Override
    public void deleteAllReplyByCommentId(String commentId) {
        commentMapper.deleteAllReplyByCommentId(commentId);
    }

    @ApiOperation("获取该评论的所有回复的主键id")
    @Override
    public List<String> selectReplyKeyIdListByCommentId(String commentId) {
        return commentMapper.selectReplyKeyIdListByCommentId(commentId);
    }

    @ApiOperation("更新评论的回复量（减去1）")
    @Override
    public void commentReplyAmountReduceOne(String commentId) {
        commentMapper.commentReplyAmountReduceOne(commentId);
    }

    @ApiOperation("评论获赞量-1")
    @Override
    public void commentLikedAmountReduceOne(String commentId) {
        commentMapper.commentLikedAmountReduceOne(commentId);
    }

    @ApiOperation("评论获赞量+1")
    @Override
    public void commentLikedAmountAddOne(String commentId) {
        commentMapper.commentLikedAmountAddOne(commentId);
    }

    @ApiOperation("回复获赞量-1")
    @Override
    public void replyLikedAmountReduceOne(String replyId) {
        commentMapper.replyLikedAmountReduceOne(replyId);
    }

    @ApiOperation("回复获赞量+1")
    @Override
    public void replyLikedAmountAddOne(String replyId) {
        commentMapper.replyLikedAmountAddOne(replyId);
    }

    @ApiOperation("es分页查询评论")
    @Override
    public List<Comment> getCommentListByBlogId(String blogId, Integer startIndex, Integer size) {

        String indexName = "tb_weblog_comment";

        Map<String, String> esMust = new HashMap<>();
        esMust.put("blogId", blogId);
        Map<String, String> esShould = new HashMap<>();
        Map<String, String> sort = new HashMap<>();
        sort.put("sortCondition", "createTime");
        sort.put("sortWay", "DESC");
        SearchHit[] hits = restClient.getListByMust_Should_SortFromIndex(indexName, esMust, esShould, sort, startIndex, size);

        List<Comment> commentList = new ArrayList<>();

        // 反序列化
        for ( SearchHit hit : hits ) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
            Comment comment = JSON.parseObject(json, Comment.class);
            commentList.add( comment );
        }
        return commentList;
    }

    @ApiOperation("es分页查询回复")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Reply> getReplyListByCommentId(String commentId, Integer startIndex, Integer size) {

        String indexName = "tb_weblog_reply";

        Map<String, String> esMust = new HashMap<>();
        esMust.put("commentId", commentId);
        Map<String, String> esShould = new HashMap<>();
        Map<String, String> sort = new HashMap<>();
        sort.put("sortCondition", "createTime");
        sort.put("sortWay", "DESC");
        SearchHit[] hits = restClient.getListByMust_Should_SortFromIndex(indexName, esMust, esShould, sort, startIndex, size);

        List<Reply> replyList = new ArrayList<>();

        // 反序列化
        for ( SearchHit hit : hits ) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
            Reply reply = JSON.parseObject(json, Reply.class);
            replyList.add( reply );
        }
        return replyList;
    }

    @ApiOperation("删除回复")
    @Override
    public void deleteReplyByReplyId(String replyId) {
        commentMapper.deleteReplyByReplyId(replyId);
    }

    @ApiOperation("获取全部的reply")
    @Override
    public List<Reply> getAllReply() {
        return commentMapper.getAllReply();
    }

    @ApiOperation("获取评论总数")
    @Override
    public Integer getCommentTotalByBlogId(String blogId) {
        return commentMapper.getCommentTotalByBlogId(blogId);
    }

    @ApiOperation("获取回复总数")
    @Override
    public Integer getReplyTotalByCommentId(String commentId) {
        return commentMapper.getReplyTotalByCommentId(commentId);
    }

    @ApiOperation("从DB分页查询评论")
    @Override
    public List<Comment> getCommentListByBlogIdFromDB(String blogId, Integer startIndex, Integer size) {
        return commentMapper.getCommentListByBlogIdFromDB(blogId, startIndex, size);
    }

    @ApiOperation("从DB分页查询回复")
    @Override
    public List<Reply> getReplyListByCommentIdFromDB(String commentId, Integer startIndex, Integer size) {
        return commentMapper.getReplyListByCommentIdFromDB(commentId, startIndex, size);
    }

    @ApiOperation("根据回复id从数据库中查询回复")
    public Reply getReplyByReplyId(String replyId) {
        return commentMapper.getReplyByReplyId(replyId);
    }

    @ApiOperation("根据评论id从数据库中查询评论")
    public Comment getCommentByCommentId(String commentId) {
        return commentMapper.getCommentByCommentId(commentId);
    }
}
