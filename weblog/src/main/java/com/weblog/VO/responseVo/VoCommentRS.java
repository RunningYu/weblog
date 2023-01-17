package com.weblog.VO.responseVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weblog.entity.Comment;
import lombok.Data;

import java.util.Date;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/18
 */
@Data
public class VoCommentRS {

    private Integer id;
    private String commentId;
    private String blogId;
    private String userId;
    private String content;
    private Integer commentLikeAmount;
    private Integer commentReplyAmount;
    private String ps;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String userName;
    private String headshot;
    private Boolean haveLiked;

    public VoCommentRS(Comment comment) {
        id = comment.getId();
        commentId = comment.getCommentId();
        blogId = comment.getBlogId();
        userId = comment.getUserId();
        content = comment.getContent();
        commentLikeAmount = comment.getCommentLikeAmount();
        commentReplyAmount = comment.getCommentReplyAmount();
        ps = comment.getPs();
        createTime = comment.getCreateTime();
    }


}
