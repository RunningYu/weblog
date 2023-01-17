package com.weblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/10
 */
@ApiModel(description = "评论的回复")
@Data
@TableName("tb_weblog_reply")
public class Reply {

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

}
