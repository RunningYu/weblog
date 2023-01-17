package com.weblog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weblog.VO.requestVo.VoUserTheme;
import com.weblog.common.RestClient;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weblog.dao.UserMapper;
import com.weblog.entity.User;
import com.weblog.service.IUserService;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/2
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public void addUser(String username, String phone, String password) {
        userMapper.addUser( username, phone, password );
    }

    @ApiOperation("根据id跟新索引表")
    @Override
    public void insertUserToIndexById(String userId) {
        String indexName = "tb_weblog_user";
        try {
            User user = userMapper.selectById(userId);

            // 1.准备Request对象
            IndexRequest request = new IndexRequest(indexName).id(user.getId().toString());
            // 2.准备Json文档
            request.source(JSON.toJSONString(user), XContentType.JSON);
            // 3.发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ApiOperation("博文用户所获的总评论量+1")
    @Override
    public void userCommentAmountAddOne(String blogUserId) {
        userMapper.userCommentAmountAddOne(blogUserId);
    }

    @ApiOperation("更新用户所获的评论量（减去（1 + 该评论的总回复量））")
    @Override
    public void decreaseCommentAmountOfUser(Integer amount, String blogUserId) {
        userMapper.decreaseCommentAmountOfUser(amount, blogUserId);
    }

    @ApiOperation("用户注销")
    @Override
    public void deleteUserOfIndexByUserId(String userId) {
        restClient.deleteDocumentById("tb_weblog_user", userId);
    }

    @ApiOperation("更新博文的用户所获得评论量（减去1）")
    @Override
    public void userCommentAmountReduceOne(String UserId) {
        userMapper.userCommentAmountReduceOne(UserId);
    }

    @ApiOperation("判断是否点赞过改博文")
    @Override
    public boolean judgeHaveLikedBlog(String userId, String blogId) {
        Integer n = userMapper.judgeHaveLikedBlog(userId, blogId);
        return n == 1 ? true : false;
    }

    @ApiOperation("取消点赞")
    @Override
    public void deleteLikeBlog(String userId, String blogId) {
        userMapper.deleteLikeBlog(userId, blogId);
    }

    @ApiOperation("创建用户点赞博文关系")
    @Override
    public void createLikeBlog(String userId, String blogId) {
        userMapper.createLikeBlog(userId, blogId);
    }

    @ApiOperation("用户获赞量-1")
    @Override
    public void userLikedAmountReduceOne(String blogUserId) {
        userMapper.userLikedAmountReduceOne(blogUserId);
    }

    @ApiOperation("用户获赞量+1")
    @Override
    public void userLikedAmountAddOne(String blogUserId) {
        userMapper.userLikedAmountAddOne(blogUserId);
    }

    @ApiOperation("判断是否点赞过评论")
    @Override
    public boolean judgeHaveLikedComment(String userId, String commentId) {
        Integer n = userMapper.judgeHaveLikedComment(userId, commentId);
        return n == 1 ? true : false;
    }

    @ApiOperation("取消点赞评论")
    @Override
    public void deleteLikeComment(String userId, String commentId) {
        userMapper.deleteLikeComment(userId, commentId);
    }

    @ApiOperation("创建用户点 评论、回复 关系")
    @Override
    public void createLikeComment(String userId, String commentId) {
        userMapper.createLikeComment(userId, commentId);
    }

    @ApiOperation("判断是否存在关注记录")
    @Override
    public boolean judgeHaveFollowedUser(String userId, String followedUserId) {
        Integer n = userMapper.judgeHaveFollowedUser(userId, followedUserId);
        return n == 1 ? true : false;
    }

    @ApiOperation("取消关注")
    @Override
    public void deleteFollowUser(String userId, String followedUserId) {
        userMapper.deleteFollowUser(userId, followedUserId);
    }

    @ApiOperation("创建关注记录")
    @Override
    public void createFollowUser(String userId, String followedUserId) {
        userMapper.createFollowUser( userId, followedUserId );
    }

    @ApiOperation("用户的关注人数-1")
    @Override
    public void concernAmountReduceOne(String userId) {
        userMapper.concernAmountReduceOne(userId);
    }

    @ApiOperation("用户的关注人数+1")
    @Override
    public void concernAmountAddOne(String userId) {
        userMapper.concernAmountAddOne( userId );
    }

    @ApiOperation("粉丝数+1")
    @Override
    public void fansAmountAddOne(String userId) {
        userMapper.fansAmountAddOne(userId);
    }

    @ApiOperation("/修改用户密码")
    @Override
    public void updatePassword(String userId, String password) {
        userMapper.updatePassword( userId, password );
    }

    @ApiOperation("/设置个人主页的背景图片（上限为6张）")
    @Transactional(rollbackFor = Exception.class)
    @Override

    public void setUserTheme(VoUserTheme voUserTheme) {
        userMapper.setUserTheme(voUserTheme.getUserId(), voUserTheme.getColor(), voUserTheme.getIsGrey(), voUserTheme.getMenuPlace());
        for ( String image : voUserTheme.getBackgroundImageList() ) {
            String imageId = UUID.randomUUID().toString();
            // 添加背景图片
            userMapper.insertBackgroudImages( voUserTheme.getUserId(), imageId, image );
        }
    }

    @ApiOperation("/用户个人资料的修改")
    @Override
    public void updateUserInfo(String userId, String userName, String phone, String email, String headShot) {
        userMapper.updateUserInfo(userId, userName, phone, email, headShot);
    }

    @ApiOperation("更新用户总排名")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRanking() {

        // 根据浏览量 + 获赞量 + 评论量 + 博文量 的综合降序获取所有用户
        List<User> userList = userMapper.getAllUserBy_RLCBFAmount();

        // 更新用户总排名
        int ranking = 0;
        for ( User user : userList ) {
            ranking ++;
            userMapper.updateUserRanking( user.getId(), ranking );
//            rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, 1);
        }
    }

    @ApiOperation("获取个人主题")
    @Override
    public List<String> getThemePicturesByUserId(String userId) {
        return userMapper.getThemePicturesByUserId(userId);
    }

    @ApiOperation("先删除用户之前的图片背景")
    @Override
    public void deletePreUserBackgroundPictures(String userId) {
        userMapper.deletePreUserBackgroundPictures(userId);
    }

    @ApiOperation("用户的博文总浏览量+1")
    @Override
    public void userReadAmountAddOne(String userId) {
        userMapper.userReadAmountAddOne(userId);
    }

    @ApiOperation("粉丝数-1")
    @Override
    public void fansAmountReduceOne(String userId) {
        userMapper.fansAmountReduceOne( userId );
    }
}
