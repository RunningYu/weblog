package com.weblog.VO.responseVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weblog.entity.Reply;
import lombok.Data;

import java.util.Date;
import java.util.function.BinaryOperator;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/18
 */
@Data
public class VoReplyRS {
    private Integer id;
    private String replyId;
    private String commentId;
    private String blogId;
    private String replyUserId;
    private String commentUserId;
    private String content;
    private Integer likeAmount;
    private String ps;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String userName;
    private String headshot;
    private Boolean haveLiked;

    public VoReplyRS(Reply reply) {
        id = reply.getId();
        replyId = reply.getReplyId();
        commentId = reply.getCommentId();
        blogId = reply.getBlogId();
        replyUserId = reply.getReplyUserId();
        commentUserId = reply.getCommentUserId();
        content = reply.getContent();
        likeAmount = reply.getLikeAmount();
        ps = reply.getPs();
        createTime = reply.getCreateTime();
    }
}
