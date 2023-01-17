package com.weblog.VO.responseVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weblog.entity.Blog;
import lombok.Data;

import java.util.Date;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/18
 */
@Data
public class VoblogResponse {

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
    private String userName;
    private String headshot;
    private Boolean haveLiked;

    public VoblogResponse(){

    }

    public VoblogResponse(Blog blog){
        id = blog.getId();
        blogId = blog.getBlogId();
        userId = blog.getUserId();
        title = blog.getTitle();
        publishImage = blog.getPublishImage();
        discription = blog.getDiscription();
        content = blog.getContent();
        tag = blog.getTag();
        likeAmount = blog.getLikeAmount();
        collectAmount = blog.getCollectAmount();
        readAmount = blog.getReadAmount();
        commentAmount = blog.getCommentAmount();
        likeReadCommentAmount = blog.getLikeReadCommentAmount();
        ableLook = blog.getAbleLook();
        status = blog.getStatus();
        ps = blog.getPs();
        createTime = blog.getCreateTime();
        updateTime = blog.getUpdateTime();
    }

}
