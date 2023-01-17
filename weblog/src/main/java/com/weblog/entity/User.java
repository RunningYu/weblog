package com.weblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/2
 */
@ApiModel(description = "用户")
@Data
@TableName("tb_weblog_user")
public class User {

    private String id;
    private String userName;
    private String phone;
    private String email;
    private String password;
    /**
     *  博文被点赞量
     */
    private Integer likedAmount;
    /**
     * 博文被收藏总量
     */
    private Integer collectedAmount;
    /**
     * 博文被流量总量
     */
    private Integer viewedAmount;
    /**
     * 评论量
     */
    private Integer commentAmount;
    /**
     * 关注量
     */
    private Integer concernAmount;
    /**
     * 博文数量
     */
    private Integer blogAmount;
    private Integer fansAmount;
    /**
     * 排名（默认根据博文数量）
     */
    private Integer ranking;
    /**
     * 头像照片链接
     */
    private String headshot;
    private String color;
    private Integer isGrey;
    private String menuPlace;
    private String role;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
