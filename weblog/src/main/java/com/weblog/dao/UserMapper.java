package com.weblog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.*;
import com.weblog.entity.User;

import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/2
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from tb_weblog_user where phone = #{phone}")
    User selectUserByPhone(String phone);

    @Insert("insert into tb_weblog_user(user_name, phone, password) values( #{username}, #{phone}, #{password})")
    void addUser(String username,  String phone, String password);

    @ApiOperation("博文用户所获的总评论量+1")
    @Update("update tb_weblog_user set comment_amount = comment_amount + 1 where id = #{blogUserId}")
    void userCommentAmountAddOne(String blogUserId);

    @ApiOperation("更新用户所获的评论量（减去（1 + 该评论的总回复量））")
    @Update("update tb_weblog_user set comment_amount = comment_amount - #{amount} where id = #{blogUserId}")
    void decreaseCommentAmountOfUser(Integer amount, String blogUserId);

    @ApiOperation("更新博文的用户所获得评论量（减去1）")
    @Update("update tb_weblog_user set comment_amount = comment_amount - 1 where id = #{blogUserId}")
    void userCommentAmountReduceOne(String userId);

    @ApiOperation("判断是否点赞过改博文")
    @Select("select count(*) > 0 from tb_weblog_like_blog where user_id = #{userId} and blog_id = #{blogId} ")
    Integer judgeHaveLikedBlog(String userId, String blogId);

    @ApiOperation("取消点赞")
    @Delete("delete from tb_weblog_like_blog where user_id = #{userId} and blog_id = #{blogId}")
    void deleteLikeBlog(String userId, String blogId);

    @ApiOperation("创建用户点赞博文关系")
    @Insert("insert into tb_weblog_like_blog(user_id, blog_id) values(#{userId}, #{blogId}) ")
    void createLikeBlog(String userId, String blogId);

    @ApiOperation("用户获赞量-1")
    @Update("update tb_weblog_user set liked_amount = liked_amount - 1 where id = #{blogUserId} ")
    void userLikedAmountReduceOne(String blogUserId);

    @ApiOperation("用户获赞量+1")
    @Update("update tb_weblog_user set liked_amount = liked_amount + 1 where id = #{blogUserId} ")
    void userLikedAmountAddOne(String blogUserId);

    @ApiOperation("判断是否点赞过改评论")
    @Select("select count(*) > 0 from tb_weblog_like_comment_reply where user_id = #{userId} and comment_id = #{commentId} ")
    Integer judgeHaveLikedComment(String userId, String commentId);

    @ApiOperation("取消点赞评论")
    @Delete("delete from tb_weblog_like_comment_reply where user_id = #{userId} and comment_id = #{commentId}")
    void deleteLikeComment(String userId, String commentId);

    @ApiOperation("创建用户点 评论、回复 关系")
    @Insert("insert into tb_weblog_like_comment_reply(user_id, comment_id) values(#{userId}, #{commentId}) ")
    void createLikeComment(String userId, String commentId);

    @ApiOperation("判断是否存在关注记录")
    @Select("select count(*) > 0 from tb_weblog_follow where user_id = #{userId} and follow_id = #{followedUserId} ")
    Integer judgeHaveFollowedUser(String userId, String followedUserId);

    @ApiOperation("取消关注")
    @Delete("delete from tb_weblog_follow where user_id = #{userId} and follow_id = #{followedUserId}")
    void deleteFollowUser(String userId, String followedUserId);

    @ApiOperation("创建关注记录")
    @Insert("insert into tb_weblog_follow(user_id, follow_id)  values( #{userId}, #{followedUserId} )")
    void createFollowUser(String userId, String followedUserId);

    @ApiOperation("用户的关注人数-1")
    @Update("update tb_weblog_user set concern_amount = concern_amount - 1 where id = #{userId} ")
    void concernAmountReduceOne(String userId);

    @ApiOperation("用户的关注人数+1")
    @Update("update tb_weblog_user set concern_amount = concern_amount + 1 where id = #{userId} ")
    void concernAmountAddOne(String userId);

    @ApiOperation("粉丝数-1")
    @Update("update tb_weblog_user set fans_amount = fans_amount - 1 where id = #{userId} ")
    void fansAmountReduceOne(String userId);

    @ApiOperation("粉丝数+1")
    @Update("update tb_weblog_user set fans_amount = fans_amount + 1 where id = #{userId} ")
    void fansAmountAddOne(String userId);

    @ApiOperation("/修改用户密码")
    @Update("update tb_weblog_user set password = #{password} where id = #{userId} ")
    void updatePassword(String userId, String password);

    @ApiOperation("/设置个人主页的背景图片（上限为6张）")
    @Update("update tb_weblog_user set color = #{color}, is_grey = #{isGrey}, menu_place = #{menuPlace} where id = #{userId} ")
    void setUserTheme(String userId, String color, Integer isGrey, String menuPlace);

    @ApiOperation("添加背景图片")
    @Insert("insert into tb_weblog_background_picture(user_id, picture_id, picture_path ) values (#{userId}, #{imageId}, #{image})")
    void insertBackgroudImages(String userId, String imageId, String image);

    @ApiOperation("/用户个人资料的修改")
    @Update("update tb_weblog_user set user_name = #{userName}, phone = #{phone}, email = #{email}, headshot = #{headShot}" +
            " where id = #{userId} ")
    void updateUserInfo(String userId, String userName, String phone, String email, String headShot);

    @ApiOperation("根据浏览量 + 获赞量 + 评论量 + 博文量 的综合降序获取所有用户")
    @Select("select * from tb_weblog_user ORDER BY liked_amount + comment_amount + blog_amount + fans_amount DESC")
    List<User> getAllUserBy_RLCBFAmount();

    @ApiOperation("更新用户总排名")
    @Update("update tb_weblog_user set ranking = #{ranking} where id = #{id}")
    void updateUserRanking(String id, int ranking);

    @ApiOperation("获取个人主题")
    @Select("select picture_path from tb_weblog_background_picture where user_id = #{userId}")
    List<String> getThemePicturesByUserId(String userId);

    @ApiOperation("先删除用户之前的图片背景")
    @Delete("delete from tb_weblog_background_picture where user_id = #{userId}")
    void deletePreUserBackgroundPictures(String userId);

    @ApiOperation("用户的博文总浏览量+1")
    @Update("update tb_weblog_user set viewed_amount = viewed_amount + 1 where id = #{userId} ")
    void userReadAmountAddOne(String userId);
}
