package com.weblog;

import com.alibaba.fastjson.JSON;
import com.weblog.entity.Blog;
import com.weblog.entity.Comment;
import com.weblog.entity.Reply;
import com.weblog.entity.User;
import com.weblog.service.IBlogService;
import com.weblog.service.ICommentService;
import com.weblog.service.IUserService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/13
 */
@SpringBootTest
public class esTest {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IBlogService blogService;

    //批量添加 到 tb_weblog_blog
    @Test
    void testBulkRequest2() throws IOException {
        //批量查询酒店的数量
        List<Blog> blogList = blogService.list();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是blog ："+blogList);
        System.out.println("--------------------------------------------------------------------------");

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Blog blog : blogList){

            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_weblog_blog")
                    .id(blog.getId().toString())
                    .source(JSON.toJSONString(blog), XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }

    //批量添加 到 tb_weblog_user
    @Test
    void testBulkRequest3() throws IOException {
        //批量查询酒店的数量
        List<User> users = userService.list();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是user ："+users);
        System.out.println("--------------------------------------------------------------------------");

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(User user : users){

            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_weblog_user")
                    .id(user.getId().toString())
                    .source(JSON.toJSONString(user), XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }


    //批量添加 到 tb_weblog_comment
    @Test
    void testBulkRequest4() throws IOException {
        //批量查询酒店的数量
        List<Comment> commentList = commentService.list();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是blog ："+commentList);
        System.out.println("--------------------------------------------------------------------------");

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Comment comment : commentList){

            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_weblog_comment")
                    .id(comment.getId().toString())
                    .source(JSON.toJSONString(comment), XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }

    //批量添加 到 tb_weblog_reply
    @Test
    void testBulkRequest5() throws IOException {
        //批量查询酒店的数量
        List<Reply> replyList = commentService.getAllReply();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是blog ："+replyList);
        System.out.println("--------------------------------------------------------------------------");

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Reply reply : replyList){

            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_weblog_reply")
                    .id(reply.getId().toString())
                    .source(JSON.toJSONString(reply), XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }

//    @Test
//    public void test1() {
//
//        List<Blog> blogList = blogService.getHotBlog(0, 100);
//        System.out.println( blogList.size() );
//        for ( Blog blog : blogList ) {
//            System.out.println( blog );
//        }
//    }
}
