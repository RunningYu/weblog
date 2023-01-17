package com.weblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.Date;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/5
 */

@ApiModel(description = "博文")
@Data
@TableName("tb_weblog_blog")
public class Blog {
    private Integer id;
    private String blogId;
    private String userId;
    private String title;
    private String publishImage;
    private String discription;
    private String content;
    private String tag;
    private Integer likeAmount;
    private Integer collectAmount;
    private Integer readAmount;
    private Integer commentAmount;
    private Integer likeReadCommentAmount;
    private Integer ableLook;
    private Integer status;
    private String ps;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
