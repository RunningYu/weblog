package com.weblog;

import com.github.benmanes.caffeine.cache.Cache;
import com.weblog.VO.requestVo.VoBlog;
import com.weblog.entity.Blog;
import com.weblog.other.Other;
import com.weblog.service.IBlogService;
import com.weblog.utils.Jieba;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import com.weblog.entity.User;
import com.weblog.service.IUserService;
import com.weblog.utils.JwtTokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class WeblogApplicationTests {

//    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    public void czyTokenTest() {
        User user = new User();
        user.setId("1arg232t3tg231235");
        user.setUserName("其然乐衣");
        user.setPassword("2000516");
        user.setPhone("13330245687");
        user.setEmail("947219346@qq.com");


        String token = jwtTokenManager.createToken(user, 0L);
        System.out.println("生成的token: ");
        System.out.println(token);
        User userFromToken = jwtTokenManager.getUserFromToken(token);
        System.out.println("userFromToken:");
        System.out.println(userFromToken);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisTestSet(  ) {

//        Scanner scanner = new Scanner(System.in);
//        long s = scanner.nextLong();

        ValueOperations ops = redisTemplate.opsForValue();
        ops.set( "username", 45);
        System.out.println(  );
    }

    @Test
    public void redisTestGet( ) {
        ValueOperations ops = redisTemplate.opsForValue();
        Object name = ops.get( "username");
        System.out.println( name );
    }

    @Test
    public void test1() {
        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAADWLSw6DMAwF7-I1CzvxJ3AbC6cSC1pEQK1U9e6ERXdPM_O-sHlr79ceMEGSEPFIJbszoj9YeTYNozyjecAAy91xH_WzwURqiFqISzd-_IGI6ABnq_vT19oPZIUk65hGgd8FZ46hAnMAAAA.5gPwIqpcrqyniDOgpuZRyD2bRuGDyHIEoa4N2JHLXscCDdJMiH7PWM6A0rmv5RPvG3PL7GdFM-gJSsyGzzDVpA";
//        User user = jwtTokenManager.getUserFromToken(token);
//        System.out.println( user );
//        redisTemplate.opsForValue().set(token, user, 5, TimeUnit.MINUTES);
        System.out.println("________________________________________");
        User user = (User) redisTemplate.opsForValue().get(token);
        System.out.println( user );

    }

    @Test
    public void test2() {
        userService.addUser("222222221", "13411111111", "2412424");
//        System.out.println( user );
    }

    @Test
    public void test3() {
//        blogService.insertTag("1", "数据库");
//        String columnId, String userId, String column, String discription, String cover
//        blogService.insertColumn("12421ji21or21", "1", "springboot", "比较流行的java后端企业开发框架", "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg" );
        VoBlog voBlog = new VoBlog();
        voBlog.setUserId("1");
        voBlog.setTitle("如何提升代码效率3");
        voBlog.setPublishImage("http://8.134.164.93:9001/file/20221201_1669893285_614.jpg");
        voBlog.setDiscription("掌握一些巧妙的方法有助于开发");
        voBlog.setContent("怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。");
        voBlog.setTag("java springboot");
        voBlog.setAbleLook(1);
        voBlog.setStatus(1);
        for ( int i = 0; i < 30; i ++ ) {
            String blogId = UUID.randomUUID().toString();
            blogService.publishBlog(blogId, voBlog.getUserId(), voBlog.getTitle(), voBlog.getPublishImage(), voBlog.getDiscription(),
                    voBlog.getContent(), voBlog.getTag(), voBlog.getAbleLook(), voBlog.getStatus());
            blogService.insertBlogToIndexById(blogId);
        }
    }

    @Test
    public void testUser() {
        User user = userService.getById(1);
        System.out.println( user );
        userService.insertUserToIndexById("1");
    }

    @Test
    public void test4() {
        List<String> list1 = new ArrayList<>();
        list1.add("111");
        list1.add("222");
        list1.add("333");
        list1.add("444");
        list1.add("555");
        list1.add("666");
        System.out.println( list1 );
        List<String> list2 = new ArrayList<>();
        list2.add("222");
        list2.add("333");
        list2.add("777");
        list2.add("888");
        System.out.println(list2);

        List<String> list3 = new ArrayList<>(list1);
        list1.removeAll(list2);
        list2.removeAll(list3);
        System.out.println( list1 );
        System.out.println( list2 );

    }

    @Value("${server.port}")
    private String port;

    @Test
    public void test5() {
//        List<Blog> blogList = blogService.getBlogListByColumnId("efgf34qt1t24grewg", 0, 5);
//        System.out.println(blogList);
//        System.out.println(blogList.size());

        List<Blog> blogList = blogService.getBlogListByUserIdCategoryId("1", "1", 0,5);
        System.out.println(blogList);
        System.out.println(blogList.size());
    }

    @Autowired
    private Other other;

    @Test
    public void test6() {
        System.out.println( other.printValue() );
    }

    @Test
    public void test7() {

        AtomicInteger p = new AtomicInteger(0);
        p.compareAndSet(0, 1);
        int n = p.get();
        System.out.println( n );
    }

    @Autowired
    private Cache<String, Object> caffeineCache;

    @Test
    public void test8() {
        Object ob = caffeineCache.getIfPresent("key");
        System.out.println( ob );
    }

    @Autowired
    private Jieba jieba;
    @Test
    public void test9() {
        Set<String> keywords = jieba.JIEBA("springboot企业开发框架");
        // [springboot, 企业, 开发, 框架]
        System.out.println( keywords );
    }

    @Test
    public void test10() {

        userService.updateUserRanking();

    }



}
