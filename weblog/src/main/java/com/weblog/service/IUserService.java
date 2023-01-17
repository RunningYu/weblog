package com.weblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weblog.VO.requestVo.VoUserTheme;
import com.weblog.entity.User;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/2
 */
public interface IUserService extends IService<User> {

    User selectUserByPhone(String phone);

    void addUser(String username, String phone, String password);

    @ApiOperation("根据id跟新索引表")
    void insertUserToIndexById(String userId);

    @ApiOperation("博文用户所获的总评论量+1")
    void userCommentAmountAddOne(String blogUserId);

    @ApiOperation("更新用户所获的评论量（减去（1 + 该评论的总回复量））")
    void decreaseCommentAmountOfUser(Integer amount, String blogUserId);

    @ApiOperation("用户注销")
    void deleteUserOfIndexByUserId(String userId);

    @ApiOperation("更新博文的用户所获得评论量（减去1）")
    void userCommentAmountReduceOne(String blogUserId);

    @ApiOperation("判断是否点赞过改博文")
    boolean judgeHaveLikedBlog(String userId, String blogId);

    @ApiOperation("取消点赞博文")
    void deleteLikeBlog(String userId, String blogId);

    @ApiOperation("创建用户点赞博文关系")
    void createLikeBlog(String userId, String blogId);

    @ApiOperation("用户获赞量-1")
    void userLikedAmountReduceOne(String blogUserId);

    @ApiOperation("用户获赞量+1")
    void userLikedAmountAddOne(String blogUserId);

    @ApiOperation("判断是否点赞过改评论")
    boolean judgeHaveLikedComment(String userId, String commentId);

    @ApiOperation("取消点赞评论")
    void deleteLikeComment(String userId, String commentId);

    @ApiOperation("创建用户点 评论、回复 关系")
    void createLikeComment(String userId, String commentId);

    @ApiOperation("判断是否存在关注记录")
    boolean judgeHaveFollowedUser(String userId, String followedUserId);

    @ApiOperation("取消关注")
    void deleteFollowUser(String userId, String followedUserId);

    @ApiOperation("创建关注记录")
    void createFollowUser(String userId, String followedUserId);

    @ApiOperation("用户的关注人数-1")
    void concernAmountReduceOne(String userId);

    @ApiOperation("用户的关注人数+1")
    void concernAmountAddOne(String userId);

    @ApiOperation("粉丝数-1")
    void fansAmountReduceOne(String followedUserId);

    @ApiOperation("粉丝数+1")
    void fansAmountAddOne(String userId);

    @ApiOperation("/修改用户密码")
    void updatePassword( String userId, String password);

    @ApiOperation("/设置个人主页的背景图片（上限为6张）")
    void setUserTheme(VoUserTheme voUserTheme);

    @ApiOperation("/用户个人资料的修改")
    void updateUserInfo(String userId, String userName, String phone, String email, String headShot);

    @ApiOperation("更新用户总排名")
    void updateUserRanking();

    @ApiOperation("获取个人主题")
    List<String> getThemePicturesByUserId(String userId);

    @ApiOperation("先删除用户之前的图片背景")
    void deletePreUserBackgroundPictures(String userId);

    @ApiOperation("用户的博文总浏览量+1")
    void userReadAmountAddOne(String userId);
}
