package com.weblog;

import io.netty.channel.DefaultEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2023/1/21
 */
@SpringBootTest
@Slf4j
public class Test1 {
    @Test
    public void test1() {
//        AtomicInteger
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
        DefaultEventLoopGroup group = new DefaultEventLoopGroup(10);
//        for (int i = 0; i < 10; i++) {
//            new Thread(() ->{
//                    SingleTest singleTest = SingleTest.getInstance();
//            },"thread"+i).start();
//        }
        SingleTest.test();
    }


    private static class SingleTest{

        private static class Singleton{
            private static SingleTest INSTANCE = new SingleTest();
        }

        static volatile AtomicInteger atomicInteger = new AtomicInteger(0);
        static {

        }

        public SingleTest() {
            log.info("11111");
        }

        public static SingleTest getInstance()  {
            return Singleton.INSTANCE;
        }

        private static void test(){
            
        }
    }

}
