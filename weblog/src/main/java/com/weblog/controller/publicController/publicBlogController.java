package com.weblog.controller.publicController;

import com.weblog.VO.responseVo.VoblogResponse;
import com.weblog.common.JsonResult;
import com.weblog.entity.Blog;
import com.weblog.entity.User;
import com.weblog.service.IBlogService;
import com.weblog.service.IUserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/13
 */
@ApiModel(description = "公共区的博文资源相关api")
@RestController
@RequestMapping("/public")
public class publicBlogController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IBlogService blogService;

    @ApiOperation("查询推荐的博文（根据 浏览量、点赞量、评论量 的总和降序）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectAdviceBlog")
    public JsonResult selectAdviceBlog(@RequestParam("userId") String userId,
                                       @RequestParam("page") String page1,
                                       @RequestParam("size") String size1 ) {
        JsonResult jsonResult = new JsonResult(200);
        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;
        // 获取推荐博文
        List<Blog> blogList = blogService.getAdviceBlogList( startIndex, size );

        List<VoblogResponse> voBlogList = new ArrayList<>();
        List<Boolean> havaLikedList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setMsg("map 中的 total 表示推荐的博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过");

        return jsonResult;
    }

    @ApiOperation("查询最新博文（时间降序）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectLatestBlog")
    public JsonResult selectLatestBlog(@RequestParam("userId") String userId, @RequestParam("page") String page1, @RequestParam("size") String size1 ) {
        JsonResult jsonResult = new JsonResult(200);
        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;
        // 分页查询最新博文
        List<Blog> blogList = blogService.getLatestBlog( startIndex, size );

        Integer total = blogService.count();
        jsonResult.add("total", total);
        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setMsg("map 中的 total 表示博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过");
        return jsonResult;
    }

    @ApiOperation("查询热榜博文（根据 浏览量 降序，前一百）（加了二级缓存）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectHotBlog")
    public JsonResult selectHotBlog(@RequestParam("userId") String userId, @RequestParam("page") String page1, @RequestParam("size") String size1 ) {
        Long begin = System.nanoTime();
        JsonResult jsonResult = new JsonResult(200);
        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;

        List<Blog> blogList = blogService.getHotBlog( 0, 100 );

        Integer total = blogList.size();
        jsonResult.add("total", total);
        jsonResult.setMsg("total（实际获取博文总数），热榜博文一次性全部获取，最多100篇");

        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setMsg("total（实际获取博文总数），热榜博文一次性全部获取，最多100篇, map中的havaLikedList的值表示对应索引的博文是否被点赞过");

        Long end = System.nanoTime();
        System.out.println( "花费时间: " + ( end - begin ) + "ns");
        return jsonResult;
    }

    @ApiOperation("综合排序搜索（默认）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/searchAdviceBlog")
    public JsonResult searchAdviceBlog(@RequestParam("userId") String userId,@RequestParam("keyword") String keyword,
                                       @RequestParam("page") String page1, @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);
        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = ( page - 1 ) * size;

        List<Blog> blogList = blogService.getBlogListByCombined(keyword, startIndex, size);

        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);

        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setMsg("map 中的 total 表示博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过");

        return jsonResult;
    }

    /**
     * 关键字输入（待优化：输入关键字，模糊查询，并实现自动补全功能）
     */
    @ApiOperation("最新优先搜索")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/searchLatestBlog")
    public JsonResult searchLatestBlog(@RequestParam("userId") String userId,
                                       @RequestParam("keyword") String keyword,
                                                @RequestParam("page") String page1,
                                                @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);
        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = ( page - 1 ) * size;

        List<Blog> blogList = blogService.searchLatestBlog(keyword, startIndex, size);

        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);
        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setMsg("map 中的 total 表示博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过");

        return jsonResult;
    }

    @ApiOperation("最热优先搜索")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/searchHotBlog")
    public JsonResult searchHotBlog(@RequestParam("userId") String userId,
                                    @RequestParam("keyword") String keyword,
                                    @RequestParam("page") String page1,
                                    @RequestParam("size") String size1) {
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = ( page - 1 ) * size;

        List<Blog> blogList = blogService.searchHotBlog(keyword, startIndex, size);

        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);
        System.out.println( voBlogList );
        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setMsg("map 中的 total 表示博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过");

        return jsonResult;
    }


    @ApiOperation("分类查询博文（分页查询）")
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/selectBlogListByCategoryId")
    public JsonResult selectBlogListByCategoryId(@RequestParam("userId") String userId,
                                                       @RequestParam("categoryId") String categoryId,
                                                       @RequestParam("page") String page1,
                                                       @RequestParam("size") String size1){
        JsonResult jsonResult = new JsonResult(200);

        Integer page = Integer.parseInt(page1);
        Integer size = Integer.parseInt(size1);
        Integer startIndex = (page - 1) * size;
        // 从数据库中分类分页查询博文
        List<Blog> blogList = blogService.getBlogListByCategoryId(categoryId, startIndex, size);
        // 统计该类的博文总数
        Integer total = blogService.getBlogTotalAmountByCategoryId(categoryId);
        jsonResult.add("total", total);

        // 判断用户是否点赞过了
        List<Boolean> havaLikedList = new ArrayList<>();
        List<VoblogResponse> voBlogList = new ArrayList<>();
        // 判断用户是否点赞过了
        for ( Blog blog : blogList ) {
            Boolean havaLiked = userService.judgeHaveLikedBlog(userId, blog.getBlogId());
            havaLikedList.add(havaLiked);
            User user = userService.getById(blog.getUserId());

            VoblogResponse voblogResponse = new VoblogResponse(blog);
            voblogResponse.setUserName( user.getUserName() );
            voblogResponse.setHeadshot( user.getHeadshot() );
            voblogResponse.setHaveLiked( havaLiked );
            voBlogList.add(voblogResponse);
        }
        jsonResult.setData(voBlogList);

        // 将点赞标签存进map里面
        jsonResult.add("havaLikedList", havaLikedList);
        jsonResult.setData(blogList);
        jsonResult.setMsg("map 中的 total 表示专栏的博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过");
        return jsonResult;
    }

}
