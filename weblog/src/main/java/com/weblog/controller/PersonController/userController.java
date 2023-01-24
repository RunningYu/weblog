package com.weblog.controller.PersonController;

import com.weblog.VO.requestVo.VoUserLogin;
import com.weblog.VO.requestVo.VoUserTheme;
import com.weblog.VO.responseVo.UploadResponse;
import com.weblog.common.JsonResult;
import com.weblog.common.MqCorrelationDate;
import com.weblog.constants.MqConstants.UserMqConstants;
import com.weblog.utils.JwtTokenManager;
import com.weblog.utils.MD5;
import com.weblog.utils.MinioUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.weblog.entity.User;
import com.weblog.service.IUserService;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/2
 */
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private MD5 md5;


    @ApiOperation("用户登录接口（首次用改电话登录，就当是注册，创建改新用户）")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/login")
    public JsonResult userLogin ( @RequestBody VoUserLogin voUserLogin ) {
        JsonResult result = new JsonResult();

        String phone = voUserLogin.getPhone();
        String password = voUserLogin.getPassword();
        System.out.println( voUserLogin );

        System.out.println(phone);
        if ( phone.length() != 11 ) {
            System.out.println(phone.length());
            return JsonResult.error("电话长度错误", 500);
        } else if ( phone.charAt(0) != '1' ) {
            System.out.println();
            return JsonResult.error("规范电话号码首位应该为1", 500);
        }
        System.out.println( "原密码：" + password );
        String mdCode = md5.encrypt(password);
        System.out.println( "加密后的密码 ：" + mdCode );
        // 判断数据库是否已存在该用户
        User user = userService.selectUserByPhone(phone);
        System.out.println( "————————————————————————————————————————————————————>" + user );
        // 不存在则新创建该用户, 存在则直接登录成功
        if ( user == null ) {
            userService.addUser( phone, phone, mdCode );
            result.setMsg("该号码首次使用，已为该号码新建账号，用户名默认为号码，登录成功");
            user = userService.selectUserByPhone(phone);
            rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, user.getId(), MqCorrelationDate.getCorrelationData());
        }
        if ( !user.getPassword().equals(mdCode)) {
            // 密码错误
            return JsonResult.error("密码输入错误", 400);
        }
        // 登录成功后，生成token，生成 权限token，并存进redis里面
        String token = jwtTokenManager.createToken(user);

        // ( token, user ) 存进云服务器中的 redis
        ValueOperations ops = redisTemplate.opsForValue();
//        VoUserSerialize userSerialize = new VoUserSerialize(user);
        // 将 token 作为 key , user实体作为value 存进redis
        redisTemplate.opsForValue().set(token, user.getId() + " " + user.getUserName(), 7, TimeUnit.DAYS);
        System.out.println("________________________________________");

        System.out.println("生成的token：" + token);

        String userIdAndName = (String) ops.get( token );
        System.out.println( "从redis 中获取到的userId 和 userName ：" + userIdAndName );
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        result.setMsg("登录成功");
        result.setData(map);
        result.setCode(200);
        return result;
    }

    @ApiOperation("获取用户信息")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/getUserInfo")
    public JsonResult getUserInfo(@RequestParam("userId") String userId) {
        JsonResult jsonResult = new JsonResult(200);
        User user = userService.getById(userId);
        jsonResult.setData(user);
        return jsonResult;
    }

    @ApiOperation("（取消）关注博主")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/followUser")
    public JsonResult followUser(@RequestParam("userId") String userId, @RequestParam("followedUserId") String followedUserId) {

        // 判断是否存在关注记录
        boolean flag = userService.judgeHaveFollowedUser( userId, followedUserId );
        if ( flag ) {
            // 已经关注过的了，再次点击时表示取消关注
            userService.deleteFollowUser( userId, followedUserId );
            // 用户的关注人数-1
            userService.concernAmountReduceOne( userId );
            // 被取消关注的人的粉丝数-1
            userService.fansAmountReduceOne( followedUserId );
            // es同步user
            rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, userId, MqCorrelationDate.getCorrelationData());
            return JsonResult.success("取消关注成功", 200);
        }
        // 创建关注记录
        userService.createFollowUser( userId, followedUserId );
        // 用户的关注人数+1
        userService.concernAmountAddOne( userId );
        // 被关注的用户的粉丝数+1
        userService.fansAmountAddOne( followedUserId );
        // es同步user
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, userId, MqCorrelationDate.getCorrelationData());
        return JsonResult.success("关注成功", 200);
    }

    @ApiOperation("/用户个人资料的修改")
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/updateUserInfo")
    public JsonResult updateUserInfo(@RequestParam("userId") String userId,
                                     @RequestParam("userName") String userName,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("email") String email,
                                     @RequestParam("headshot") String headShot)  {
        if ( phone.length() != 11 ) {
            return JsonResult.error("用户电话输入不规范", 400);
        }
        userService.updateUserInfo(userId, userName, phone, email, headShot);
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, userId, MqCorrelationDate.getCorrelationData());
        return JsonResult.success("修改成功", 200);
    }

    @ApiOperation("/修改用户密码")
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/updatePassword")
    public JsonResult updatePassword(@RequestParam("userId") String userId,
                                     @RequestParam("prePassword") String prePassword,
                                     @RequestParam("newPassword") String newPassword) {
        System.out.println("原密码：" + prePassword);
        System.out.println("新密码：" + newPassword);
        User user = userService.getById(userId);
        if ( md5.encrypt(prePassword).equals( user.getPassword() ) ) {
            String mdCode = md5.encrypt(newPassword);
            userService.updatePassword( userId, mdCode );
            rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, userId, MqCorrelationDate.getCorrelationData());
            return JsonResult.success("密码修改成功", 200);
        }
        return JsonResult.error("原密码输入错误，修改失败，请重新输入原密码", 400);
    }

    @ApiOperation("主题设置修改, 背景图片限制上限为6张")
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/setUserTheme")
    public JsonResult setUserTheme(@RequestBody VoUserTheme voUserTheme) {
        System.out.println( "—————————— setUserTheme ——————————————————————>" + voUserTheme );
        if (voUserTheme.getBackgroundImageList().size() > 6) {
            return JsonResult.error("上传的背景图片最多是6张", 400);
        }
        // 先删除用户之前的图片背景
        userService.deletePreUserBackgroundPictures(voUserTheme.getUserId());

        System.out.println( voUserTheme.getColor() );
        System.out.println("color");
        userService.setUserTheme(voUserTheme);
        return JsonResult.success("设置成功");
    }

    /**
     * 设置主页的链接分享
     */


    @ApiOperation("图片上传，返回图片链接")
    @PostMapping("/uploadPicture")
    public JsonResult minioUpload(@RequestParam(value = "file") MultipartFile file){
        JsonResult jsonResult = new JsonResult(200);
        UploadResponse response = null;
        try {
            response = minioUtil.uploadFile(file, "file");
            jsonResult.setData(response);
            jsonResult.setMsg("上传成功");
            System.out.println(jsonResult);
        } catch (Exception e) {
            jsonResult.setMsg("上传失败");
            jsonResult.setCode(400);
            return jsonResult;
        }
        return jsonResult;
    }

    @ApiOperation("获取个人主题")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/getTheme")
    public JsonResult getTheme(@RequestParam("userId") String userId) {
        JsonResult jsonResult = new JsonResult(200);
        List<String> pictureList = userService.getThemePicturesByUserId(userId);
        VoUserTheme userTheme = new VoUserTheme();
        if ( !userId.equals("0") ) {
            User user = userService.getById(userId);
            userTheme.setBackgroundImageList(pictureList);
            userTheme.setUserId(userId);
            userTheme.setColor(user.getColor());
            userTheme.setIsGrey(user.getIsGrey());
            userTheme.setMenuPlace(user.getMenuPlace());
            jsonResult.setData(userTheme);
            return jsonResult;
        }
        userTheme.setIsGrey(0);
        jsonResult.setData(userTheme);
        jsonResult.setMsg("0, 灰度页面");
        return jsonResult;
    }

}
