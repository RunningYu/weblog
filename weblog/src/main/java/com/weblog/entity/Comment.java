package com.weblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/10
 *
 *
 */
@ApiModel(description = "博文的评论")
@Data
@TableName("tb_weblog_comment")
public class Comment {

    private Integer id;
    private String commentId;
    private String blogId;
    private String userId;
    private String content;
    private Integer commentLikeAmount;
    private Integer commentReplyAmount;
    private String ps;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date createTime;

}
