package com.weblog.controller.PersonController;

import com.weblog.VO.responseVo.VoCommentRS;
import com.weblog.VO.responseVo.VoReplyRS;
import com.weblog.common.JsonResult;
import com.weblog.common.MqCorrelationDate;
import com.weblog.constants.MqConstants.BlogMqConstants;
import com.weblog.constants.MqConstants.CommentMqConstants;
import com.weblog.constants.MqConstants.ReplyMqConstants;
import com.weblog.constants.MqConstants.UserMqConstants;
import com.weblog.entity.Comment;
import com.weblog.entity.Reply;
import com.weblog.entity.User;
import com.weblog.service.IBlogService;
import com.weblog.service.ICommentService;
import com.weblog.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/13
 */
@Slf4j
@RestController
@RequestMapping("/comment")
public class commentController {

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


    @ApiOperation("发布评论")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/publishComment")
    public JsonResult publishComment(@RequestParam("userId") String userId,
                                     @RequestParam("blogId") String blogId,
                                     @RequestParam("content") String content,
                                     @RequestParam("blogUserId") String blogUserId) {
        String commentId = String.valueOf(UUID.randomUUID());
        // 添加评论到数据库
        commentService.insertComment(commentId, userId, blogId, content);
        // 博文的评论量+1
        blogService.blogCommentAmountAddOne(blogId);
        // 博文用户所获的总评论量+1
        userService.userCommentAmountAddOne(blogUserId);



        // 同步es
        // 同步评论
        rabbitTemplate.convertAndSend(CommentMqConstants.COMMENT_EXCHANGE, CommentMqConstants.COMMENT_INSERT_KEY, commentId, MqCorrelationDate.getCorrelationData());
        log.info(" ------------------> 已发送 发表评论的 消息 ");
        // 同步博文评论量
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, blogId, MqCorrelationDate.getCorrelationData());
        // 同步User获得的评论量
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, blogUserId, MqCorrelationDate.getCorrelationData());

        return JsonResult.success("评论成功", 200);
    }

    @ApiOperation("回复评论")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/publishReply")
    public JsonResult publishReply(  @RequestParam("commentId") String commentId,
                                     @RequestParam("blogId") String blogId,
                                     @RequestParam("replyUserId") String replyUserId,
                                     @RequestParam("commentUserId") String commentUserId,
                                     @RequestParam("content") String content,
                                     @RequestParam("blogUserId") String blogUserId) {
        String replyId = String.valueOf(UUID.randomUUID());

        commentService.insertReply(replyId, commentId, replyUserId, commentUserId, blogId, content);
        // 评论的回复量+1
        commentService.commentReplyAmountAddOne(commentId);
        // 博文的评论量+1
        blogService.blogCommentAmountAddOne(blogId);
        // 博文用户所获的总评论量+1
        userService.userCommentAmountAddOne(blogUserId);

        // 同步es
        // 同步回复
        rabbitTemplate.convertAndSend(ReplyMqConstants.REPLY_EXCHANGE, ReplyMqConstants.REPLY_INSERT_KEY, replyId, MqCorrelationDate.getCorrelationData() );
        // 同步评论的回复量
        rabbitTemplate.convertAndSend(CommentMqConstants.COMMENT_EXCHANGE, CommentMqConstants.COMMENT_INSERT_KEY, commentId, MqCorrelationDate.getCorrelationData() );
        // 同步博文评论量
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, blogId, MqCorrelationDate.getCorrelationData());
        // 同步User获得的评论量
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, blogUserId, MqCorrelationDate.getCorrelationData());

        return JsonResult.success("回复成功", 200);
    }


    @ApiOperation("删除评论(根据commentId)")
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/deleteCommentByCommentId")
    public JsonResult deleteCommentByCommentId(
            @RequestParam("commentId") String commentId,
            @RequestParam("id") String id,
            @RequestParam("blogId") String blogId,
            @RequestParam("blogUserId") String blogUserId,
            @RequestParam("replyAmount") String replyAmount) {

        // 获取该评论的所有回复的主键id
        List<String> reply_keyIds = commentService.selectReplyKeyIdListByCommentId(commentId);

        // 删除评论
        commentService.removeById(id);
        // 删除评论的所用回复
        commentService.deleteAllReplyByCommentId(commentId);
        // 更新博文的评论量（减去（1 + 该评论的总回复量））
        Integer amount = Integer.parseInt(replyAmount) + 1;
        blogService.decreaseCommentAmountOfBlog(amount, blogId);
        // 更新用户所获的评论量（减去（1 + 该评论的总回复量））
        userService.decreaseCommentAmountOfUser(amount, blogUserId);

        // es同步
        // es同步删除评论
        Message message = MessageBuilder
                .withBody(id.getBytes(StandardCharsets.UTF_8))
                // 给消息设置 ttl 为 五秒
                .setExpiration("5000")
                .build();
        rabbitTemplate.convertAndSend(CommentMqConstants.COMMENT_EXCHANGE, CommentMqConstants.COMMENT_DELETE_KEY, message, MqCorrelationDate.getCorrelationData());
        log.info("-------> 删除 发送");
//        rabbitTemplate.convertAndSend(CommentMqConstants.COMMENT_EXCHANGE, CommentMqConstants.COMMENT_DELETE_KEY, id, MqCorrelationDate.getCorrelationData());

        // es同步删除评论的所有回复
        for ( String id1 : reply_keyIds ) {
            rabbitTemplate.convertAndSend(ReplyMqConstants.REPLY_EXCHANGE, ReplyMqConstants.REPLY_DELETE_KEY, id1, MqCorrelationDate.getCorrelationData());
        }
        //更新博文的评论量（减去（1 + 该评论的总回复量））
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, blogId, MqCorrelationDate.getCorrelationData());
        // es同步更新博客的用户所获的评论量（减去（1 + 该评论的总回复量））
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, blogUserId, MqCorrelationDate.getCorrelationData());

        return JsonResult.success("删除成功",200);
    }

    @ApiOperation("删除回复")
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/deleteReplyByReplyId")
    public JsonResult deleteReplyByReplyId(
            // 回复的主键id
            @RequestParam("replyId") String replyId,
            @RequestParam("commentId") String commentId,
            @RequestParam("blogId") String blogId,
            @RequestParam("blogUserId") String blogUserId) {
        // 删除回复
        commentService.deleteReplyByReplyId(replyId);
        // 更新评论的回复量（减去1）
        commentService.commentReplyAmountReduceOne(commentId);
        // 更新博文的评论量（减去1）
        blogService.blogCommentAmountReduceOne(blogId);
        // 更新博文的用户所获得评论量（减去1）
        userService.userCommentAmountReduceOne(blogUserId);

        // es同步
        // es同步删除回复
        rabbitTemplate.convertAndSend(ReplyMqConstants.REPLY_EXCHANGE, ReplyMqConstants.REPLY_DELETE_KEY, replyId, MqCorrelationDate.getCorrelationData());
        // es同步更新评论的回复量（减去1）
        rabbitTemplate.convertAndSend(CommentMqConstants.COMMENT_EXCHANGE, CommentMqConstants.COMMENT_INSERT_KEY, commentId, MqCorrelationDate.getCorrelationData());
        // es同步更新博文的评论量（减去1）
        rabbitTemplate.convertAndSend(BlogMqConstants.BLOG_EXCHANGE, BlogMqConstants.BLOG_INSERT_KEY, blogId, MqCorrelationDate.getCorrelationData());
        // es同步更新博文的用户所获得评论量（减去1）
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, blogUserId, MqCorrelationDate.getCorrelationData());

        return JsonResult.success("删除成功", 200);
    }

    @ApiOperation("(取消）点赞评论")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/likeComment")
    public JsonResult likeComment(@RequestParam("userId") String userId,
                                  @RequestParam("commentId") String commentId,
                                  @RequestParam("p") Integer p) {

        if ( p == 2 ) {
            return likeReply( userId, commentId);
        }

        // 判断是否点赞过改博文
        boolean flag = userService.judgeHaveLikedComment(userId, commentId);
        if (flag) {
            // 点赞过了，再点表示取消点赞
            userService.deleteLikeComment(userId, commentId);
            // 评论获赞量-1
            commentService.commentLikedAmountReduceOne(commentId);
            // es同步评论的点赞量
            rabbitTemplate.convertAndSend(CommentMqConstants.COMMENT_EXCHANGE, CommentMqConstants.COMMENT_INSERT_KEY, commentId, MqCorrelationDate.getCorrelationData() );
            return JsonResult.success("取消点赞成功", 200);
        }
        // 创新点赞记录
        userService.createLikeComment(userId, commentId);
        // 评论获赞量+1
        commentService.commentLikedAmountAddOne(commentId);
        // es同步评论的点赞量
        rabbitTemplate.convertAndSend(CommentMqConstants.COMMENT_EXCHANGE, CommentMqConstants.COMMENT_INSERT_KEY, commentId, MqCorrelationDate.getCorrelationData());
        return JsonResult.success("点赞成功", 200);
    }

    @ApiOperation("(取消）点赞回复")
    @Transactional(rollbackFor = Exception.class)
//    @PostMapping("/likeReply")
    public JsonResult likeReply( String userId, String replyId ) {
        // 判断是否点赞过改博文
        boolean flag = userService.judgeHaveLikedComment(userId, replyId);
        if (flag) {
            // 点赞过了，再点表示取消点赞
            userService.deleteLikeComment(userId, replyId);
            // 回复获赞量-1
            commentService.replyLikedAmountReduceOne(replyId);
            // es同步回复的点赞量
            rabbitTemplate.convertAndSend(ReplyMqConstants.REPLY_EXCHANGE, ReplyMqConstants.REPLY_INSERT_KEY, replyId, MqCorrelationDate.getCorrelationData() );
            return JsonResult.success("取消点赞成功", 200);
        }
        // 创新点赞记录
        userService.createLikeComment(userId, replyId);
        // 回复获赞量+1
        commentService.replyLikedAmountAddOne(replyId);
        // es同步回复的点赞量
        rabbitTemplate.convertAndSend(ReplyMqConstants.REPLY_EXCHANGE, ReplyMqConstants.REPLY_INSERT_KEY, replyId, MqCorrelationDate.getCorrelationData() );
        return JsonResult.success("点赞成功", 200);
    }

    @ApiOperation("分页查询评论列表")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectCommentListByBlogId")
    public JsonResult selectCommentListByBlogId(@RequestParam("userId") String userId,
                                                @RequestParam("blogId") String blogId,
                                                @RequestParam("page") String page1,
                                                @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;

        // es分页查询评论
//        List<Comment> commentList = commentService.getCommentListByBlogId(blogId, startIndex, size);

        // 从DB分页查询评论
        List<Comment> commentList = commentService.getCommentListByBlogIdFromDB( blogId, startIndex, size );

        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoCommentRS> voCommentRSList = new ArrayList<>();
        for ( Comment comment : commentList ) {
            Boolean havaLiked = userService.judgeHaveLikedComment(userId, comment.getCommentId() );
            havaLikedList.add(havaLiked);
//            System.out.println( "userId : " + comment.getUserId() );
            User user = userService.getById(comment.getUserId());
//            System.out.println( user );
            VoCommentRS voCommentRS = new VoCommentRS(comment);
            voCommentRS.setUserName( user.getUserName() );
            voCommentRS.setHeadshot( user.getHeadshot() );
            voCommentRS.setHaveLiked( havaLiked );
            voCommentRSList.add( voCommentRS );
        }
        jsonResult.setData( voCommentRSList );
        Integer total = commentService.getCommentTotalByBlogId( blogId );
        jsonResult.add("total", total);
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setMsg("map中的total，表示评论总数，havaLikedList的值表示对应索引的评论是否被点赞过");

        return jsonResult;
    }

    @ApiOperation("分页查询回复列表")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectReplyListByCommentId")
    public JsonResult selectReplyListByCommentId(@RequestParam("userId") String userId,
                                                 @RequestParam("commentId") String commentId,
                                                 @RequestParam("page") String page1,
                                                 @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;

        // es分页查询回复
//        List<Reply> replyList = commentService.getReplyListByCommentId(commentId, startIndex, size);

        // es分页查询回复
        List<Reply> replyList = commentService.getReplyListByCommentIdFromDB(commentId, startIndex, size);

        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoReplyRS> replyRSList = new ArrayList<>();
        for ( Reply reply : replyList ) {

            Boolean havaLiked = userService.judgeHaveLikedComment(userId, reply.getReplyId() );
            havaLikedList.add(havaLiked);
            User user = userService.getById(reply.getReplyUserId());
            VoReplyRS voReplyRS = new VoReplyRS(reply);
            voReplyRS.setUserName( user.getUserName() );
            voReplyRS.setHeadshot( user.getHeadshot() );
            voReplyRS.setHaveLiked( havaLiked );
            replyRSList.add( voReplyRS );
        }
        jsonResult.setData( replyRSList );
        jsonResult.add("havaLikedList", havaLikedList);
        Integer total = commentService.getReplyTotalByCommentId( commentId );
        jsonResult.add("total", total);
        jsonResult.setMsg("map中的total，表示评论总数，havaLikedList的值表示对应索引的回复是否被点赞过");

        return jsonResult;
    }

}
