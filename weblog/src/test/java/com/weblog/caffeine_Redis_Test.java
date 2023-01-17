package com.weblog;

import com.github.benmanes.caffeine.cache.Cache;
import com.weblog.config.CaffeineConfig;
import com.weblog.constants.other.CaffeineConstants;
import com.weblog.entity.Blog;
import com.weblog.service.IBlogService;
import com.weblog.service.Impl.IBlogServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/14
 */
@SpringBootTest
public class caffeine_Redis_Test {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Cache<String, Object> caffeineCache;

    @Autowired
    private IBlogServiceImpl blogService;

    @Test
    public void test1(){

        caffeineCache.put("key","value");
//        cache.put("key2",null);

        System.out.println(caffeineCache.getIfPresent("key"));
    }

    @Test
    public void test2() {
        // redis中没有，则查询 es库
        List<Blog> blogList1 = blogService.getHotBlogList(0, 100);
        if ( !blogList1.isEmpty() && blogList1.size() != 0 && blogList1 != null ) {
            System.out.println( "-----> get hotBlog from es库" );
//                logger.info("get hotBlog from es库");
            // 存进 caffeine缓存中
            caffeineCache.put(CaffeineConstants.HOT_BLOG_KEY, blogList1);
            // 存进 redis
            redisTemplate.opsForValue().set(CaffeineConstants.HOT_BLOG_KEY, blogList1, 24 * 60, TimeUnit.MINUTES);
        }

    }

    @ApiOperation("删除 caffeine 和 redis 中的 key")
    @Test
    public void deleteKeyOfCaffeineAndRedis() {
        redisTemplate.delete(CaffeineConstants.HOT_BLOG_KEY);
//        caffeineCache.invalidate(key);
    }




}
