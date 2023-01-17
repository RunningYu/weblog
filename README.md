# weblog
博客平台

技术栈：


- **jwt + redis + token** : 用户权限 
- **Caffeine+Redis** :实现两级缓存，对热门博文和最新博文进行优化，提升查询效率，减轻数据库压力
  - **Caffeine**：用本地进程缓存来缓存数据，（由于存储在内存中）实现快速的数据的读取，大量减少对数据库的访问，减少数据库的压力
- **RabbitMQ**：进行数据一致性和可靠性处理；同步业务，异步处理，提高客户端的响应速度；
- 定时任务 + 监听器：
  - **线程池**：定时更新热点帖子的缓存（每天 2 : 00 定时更新）
  - **Quartz**： Quartz任务调度框架和Springboot的 监听器 共同实现任务调度功能，并实现了定时计算帖子分数、更新热门帖子等，对系统热门进行优化
- **MinIO**: 在云服务器上安装**MinIO**高性能对象存储来对项目中的图片进行存储
- **Elasticsearch**：搜索引擎提供便捷、快速、多功能的查询API，如聚合、过滤、范围、模糊等查询

weblog项目的业务设计
![image](https://user-images.githubusercontent.com/105648852/212812633-9f9f759c-b45d-495a-9bbc-38bbcedd84da.png)


接口文档：
0. 图片上传接口
url 
POST	http://localhost:6868/user/uploadPicture
请求示例
参数名	类型	值	说明
file	File	图片文件	图片file文件
成功响应
状态码：200 OK
响应示例：data 中是用户的专栏列表以及详细数据
{
    "code": 200,
    "msg": "上传成功",
    "data": {
        "minIoUrl": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg",
        "nginxUrl": "null/file/20221216_1671159067_927.jpg"
    },
    "map": {}
}
错误响应
1. 用户相关接口
登录 注册
url 
POST	http://localhost:6868/user/login
请求示例
参数名	类型	值	说明
phone	String	13330245687	账号（电话）
password	String	12345678	密码
成功响应
状态码：200 OK
响应示例：
{
    "code": null,
    "msg": "登录成功",
    "data": {
        "user": {
            "id": "1",
            "userName": "其然乐衣",
            "phone": "13330245687",
            "email": "947219346@qq.com",
            "password": "25d55ad283aa400af464c76d713c07ad",
            "likedAmount": 1,
            "collectedAmount": 0,
            "viewedAmount": 0,
            "commentAmount": 10,
            "concernAmount": 0,
            "blogAmount": 0,
            "fansAmount": 0,
            "ranking": 0,
            "headshot": null,
            "color": null,
            "isGrey": null,
            "menuPlace": null,
            "create_time": null,
            "update_time": null
        },
        "token": "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKkgsLi7PL0pRslIyMk0xNU1MMbIwTkw0MTBITDMxM0k2N0sxNzRONjBPTFHSUcoEqTMEMlIrCpSsDM3MDY0MTIwMjYEyiSUwAUMzI0MdpdLi1KK8xNxUoIanrduet2x7snPCi4WLlWoBlYfienQAAAA.lqsGe3xqzu4T4zj19sQnHS5Hh_7xZ4E-UXUsG-PKzxbYNYw0lt8l-k4YkDU4W_Xw6BSDIeeaTtAgTnBsMMvrxw"
    },
    "map": {}
}
错误响应
状态码：400 error
响应示例：
{
    "code": 400,
    "msg": "密码输入错误",
    "data": null,
    "map": {}
}
修改个人信息
url 
PUT	http://localhost:6868/user/updateUserInfo
请求示例
参数名	类型	值	说明
userId	String	3	用户id
userName	String	天龙人	用户名
phone	String	12121212121	电话
email	String	947219346@qq.com	QQ邮箱
headshot	String	http://8.134.164.93:9001/file/20221216_1671159067_927.jpg	头像链接
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "修改成功",
    "data": null,
    "map": {}
}
错误响应
状态码：400 
响应示例：当输入的电话号码长度  != 11 时
{
    "code": 400,
    "msg": "用户电话输入不规范",
    "data": null,
    "map": {}
}


修改密码
url 
PUT	http://localhost:6868/user/updatePassword
请求示例
参数名	类型	值	说明
userId	String	1	用户id
prePassword	String	12345678	原密码
newPassword	String	87654321	新密码
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "密码修改成功",
    "data": null,
    "map": {}
}
错误响应
状态码：400error
响应示例：当输入的原密码不对时
{
    "code": 400,
    "msg": "原密码输入错误，修改失败，请重新输入原密码",
    "data": null,
    "map": {}
}

获取主题信息
url 
GET	http://localhost:6868/user/getTheme
请求示例
参数名	类型	值	说明
userId	String	1	用户id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": null,
    "data": {
        "userId": "1",
        "backgroundImageList": [
            "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg"
        ],
        "color": null,
        "isGrey": null,
        "menuPlace": null
    },
    "map": {}
}
错误响应

主题设置
url 
PUT	http://localhost:6868/user/setUserTheme
请求示例
参数名	类型	值	说明
userId	String	1	用户id
backgroundImageList	List<String>	图片链接数组	图片链接数组
color	String	12121212121	电话
isGrey	String	1	1-打开灰度页面 0-关闭
menuPlace	String	left	left 或者 right
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "修改成功",
    "data": null,
    "map": {}
}
错误响应
状态码：400 
响应示例：当上传的背景图片 > 6时
{
    "code": 400,
    "msg": "上传的背景图片最多是6张",
    "data": null,
    "map": {}
}


（取消）关注博主
url 
PUT	http://localhost:6868/user/setUserTheme
请求示例
参数名	类型	值	说明
userId	String	1	用户id
backgroundImageList	List<String>	图片链接数组	图片链接数组
color	String	12121212121	电话
isGrey	String	1	1-打开灰度页面 0-关闭
menuPlace	String	left	left 或者 right
成功响应1
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "关注成功",
    "data": null,
    "map": {}
}
成功响应2
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "取消关注成功",
    "data": null,
    "map": {}
}
错误响应
状态码：400 

2. 博文
发表博文
url 
POST	http://localhost:6868/blog/publishBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
blogId	String	8ea0feb9-f28f-4459-93af-a452910c062f	博文id
title	String	1111111111111111111	博文题目
publishImage	String	http://8.134.164.93:9001/file/20221201_1669893285_614.jpg	封面
discription	String	卷啊	博文描述
content	String	时代变了	博文内容
tag	String	前端 后端	标签(空格隔开)
ableLook	int	0	是否公开
status	int	2	博文状态（0-审核不通过，1-已发布，2-待审核，3-草稿）
columnIdList	List<String>	博文归属专栏数组	博文归属专栏列表数组或链表
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "发布成功",
    "data": null,
    "map": {}
}
错误响应
评论博文
url 
POST	http://localhost:6868/comment/publishComment
请求示例
参数名	类型	值	说明
userId	String	2	用户id
blogId	String	f10cc656-55c7-4958-a1aeac3bc386324f	博文id
content	String	确实诶	回复内容
blogUserId	String	1	博文的用户id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "评论成功",
    "data": null,
    "map": {}
}
错误响应
修改博文
url 
PUT	http://localhost:6868/blog/updateBlogByBlogId
请求示例
参数名	类型	值	说明
userId	String	1	用户id
blogId	String	8ea0feb9-f28f-4459-93af-a452910c062f	博文id
title	String	1111111111111111111	博文题目
publishImage	String	http://8.134.164.93:9001/file/20221201_1669893285_614.jpg	封面
discription	String	卷啊	博文描述
content	String	时代变了	博文内容
tag	String	前端 后端	标签
ableLook	int	0	是否公开
columnIdList	List<String>	博文归属专栏数组	博文归属专栏列表数组或链表
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "修改成功",
    "data": null,
    "map": {}
}
错误响应


删除博文
url 
DELETE	http://localhost:6868/blog/deleteBlogByBlogId
请求示例
参数名	类型	值	说明
userId	String	1	用户id
blogId	String	f10cc656-55c7-4958-a1aeac3bc386324f	博文id
id	String	11	博文的主键id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "删除成功",
    "data": null,
    "map": {}
}
错误响应
回复评论
url 
POST	http://localhost:6868/comment/publishReply
请求示例
参数名	类型	值	说明
commentId	String	2b922393-728a-4219-8e70-256b975448c8	评论id
blogId	String	f10cc656-55c7-4958-a1aeac3bc386324f	博文id
replyUserId	String	3	回复者id
commentUserId	String	2	评论的用户id
content	String	确实诶	回复内容
blogUserId	String	1	博文的用户id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "回复成功",
    "data": null,
    "map": {}
}
错误响应
删除评论
url 
DELETE	http://localhost:6868/comment/deleteCommentByCommentId
请求示例
参数名	类型	值	说明
commentId	String	42cf00aa-f4ff-47a5-9fb0-be430f842fe5	评论id
blogId	String	f10cc656-55c7-4958-a1aeac3bc386324f	博文id
id	String	11	评论的主键id
replyAmount	String	0	评论的回复量
blogUserId	String	1	该评论的博文的用户id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "删除成功",
    "data": null,
    "map": {}
}
错误响应
删除回复
url 
DELETE	http://localhost:6868/comment/deleteReplyByReplyId
请求示例
参数名	类型	值	说明
replyId	String	2cbab48f-4ce1-4461-abb6-347624b3fc19	回复id
blogId	String	f10cc656-55c7-4958-a1aeac3bc386324f	博文id
commentId	String	42cf00aa-f4ff-47a5-9fb0-be430f842fe5	评论id
blogUserId	String	1	该评论的博文的用户id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "删除成功",
    "data": null,
    "map": {}
}
错误响应
(取消）点赞评论
url 
POST	http://localhost:6868/comment/likeComment
请求示例
参数名	类型	值	说明
userId	String	2	用户id
commentId	String	2b922393-728a-4219-8e70-256b975448c8	评论id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "点赞成功",
    "data": null,
    "map": {}
}
错误响应
(取消）点赞回复
url 
POST	http://localhost:6868/comment/likeReply
请求示例
参数名	类型	值	说明
userId	String	2	用户id
replyId	String	84696a3a-1f2d-460b-891b-fc395a03be51	回复id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "点赞成功",
    "data": null,
    "map": {}
}
错误响应
(取消）点赞博文
url 
POST	http://localhost:6868/blog/likeBlog
请求示例
参数名	类型	值	说明
userId	String	2	用户id
blogId	String	f10cc656-55c7-4958-a1aeac3bc386324f	博文id
blogUserId	String	1	博文的用户id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "点赞成功",
    "data": null,
    "map": {}
}
错误响应
分页查询 评论 列表
评论实体类的字段说明

url 
GET	http://localhost:6868/comment/selectCommentListByBlogId
请求示例
参数名	类型	值	说明
userId	String	1	用户id
blogId	String	124214o1p2jr	博文id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是评论的详细数据
{
    "code": 200,
    "msg": "map中的total，表示评论总数，havaLikedList的值表示对应索引的评论是否被点赞过",
    "data": [
        {
            "id": 47,
            "commentId": "326d54b1-8b73-4c3b-8adc-c53dc1327fc4",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 0,
            "commentReplyAmount": 1,
            "ps": null,
            "createTime": "2022-12-18 23:14:01",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 46,
            "commentId": "f3f2db80-db09-407f-a3bc-27e624d81e46",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 0,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-18 23:13:59",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 43,
            "commentId": "5fb922d9-3c85-486b-a653-7a98be8113b6",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 0,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-18 23:13:52",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 42,
            "commentId": "2545d001-6056-4189-8b56-6955eeae5a95",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 0,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-18 23:12:39",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 39,
            "commentId": "be1a3d60-2a97-455a-b36c-f3abcaa2bf8d",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 0,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-18 23:12:31",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 38,
            "commentId": "d1c890cc-55a4-44fc-a4e3-26593dd87391",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 0,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-18 23:12:27",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 27,
            "commentId": "6862ede9-557a-494b-affb-e430e959a2ac",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 1,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-17 00:03:31",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 26,
            "commentId": "f1c4eb36-50e6-4791-ad7e-c7f63e610257",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 1,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-16 23:59:33",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 25,
            "commentId": "6c39c82f-9293-488f-a15e-d476435f1854",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 1,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-16 23:59:32",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 23,
            "commentId": "6c4c9a70-ec4d-401e-81ae-52f3ccdd96b6",
            "blogId": "124214o1p2jr",
            "userId": "2",
            "content": "怎么说呢，大概也是这样吧",
            "commentLikeAmount": 1,
            "commentReplyAmount": 0,
            "ps": null,
            "createTime": "2022-12-16 23:59:31",
            "userName": "路飞",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        }
    ],
    "map": {
        "total": 45,
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应
分页查询 回复 列表
回复实体类的字段说明

url 
GET	http://localhost:6868/comment/selectReplyListByCommentId
请求示例
参数名	类型	值	说明
userId	String	1	用户id
commentId	String	2b922393-728a-4219-8e70-256b975448c8	评论id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是回复的详细数据
{
    "code": 200,
    "msg": "map中的total，表示评论总数，havaLikedList的值表示对应索引的回复是否被点赞过",
    "data": [
        {
            "id": 10,
            "replyId": "6af77b9c-9632-4f88-a550-82be0bb17729",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-16 15:46:53",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 9,
            "replyId": "0a821d61-bf4c-40c1-a4af-9b207da7e74e",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-16 12:12:26",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 8,
            "replyId": "a13bb420-db85-46e1-b008-9878201309f1",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-16 12:09:07",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 7,
            "replyId": "2e573dd9-f3d2-4290-8356-80b1c2dc3fae",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-16 12:08:54",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 6,
            "replyId": "ecd79d37-799a-49a3-ad90-6512be508fde",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-16 11:51:59",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 5,
            "replyId": "a4f904b0-9cff-49aa-a849-20f25d18257f",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-15 11:02:31",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 4,
            "replyId": "8621a907-75fe-481c-814f-74ce96ccbaab",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-15 10:49:16",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 3,
            "replyId": "8178491e-b733-4c20-a236-06c3dc3815d6",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-15 10:48:14",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        },
        {
            "id": 1,
            "replyId": "84696a3a-1f2d-460b-891b-fc395a03be51",
            "commentId": "2b922393-728a-4219-8e70-256b975448c8",
            "blogId": "124214o1p2jr",
            "replyUserId": "3",
            "commentUserId": "2",
            "content": "确实欸",
            "likeAmount": 0,
            "ps": null,
            "createTime": "2022-12-12 16:00:16",
            "userName": "天龙人",
            "headshot": "http://8.134.164.93:9001/file/20221216_1671159067_927.jpg"
        }
    ],
    "map": {
        "total": 9,
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应
新建用户的博文 标签
url 
POST	http://localhost:6868/blog/insertColumn
请求示例
参数名	类型	值	说明
userId	String	2	用户id
column	String	Spring Security	专栏名称
discription	String	安全框架	专栏说明
cover	String	http://8.134.164.93:9001/file/20221201_1669893285_614.jpg	封面
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "新建成功",
    "data": null,
    "map": {}
}
错误响应
新建用户的博文 专栏
url 
POST	http://localhost:6868/blog/insertTag
请求示例
参数名	类型	值	说明
userId	String	2	用户id
tag	String	springboot	标签名
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "新建成功",
    "data": null,
    "map": {}
}
错误响应
数据统计（各类数据统计可以直接在user信息里查看到）
用户实体类信息

url 
GET	http://localhost:6868/user/getUserInfo
请求示例
参数名	类型	值	说明
userId	String	1	用户id
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": null,
    "data": {
        "id": "1",
        "userName": "其然乐衣",
        "phone": "13330245687",
        "email": "947219346@qq.com",
        "password": "25d55ad283aa400af464c76d713c07ad",
        "likedAmount": 1,
        "collectedAmount": 0,
        "viewedAmount": 0,
        "commentAmount": 10,
        "concernAmount": 0,
        "blogAmount": 0,
        "fansAmount": 0,
        "ranking": 0,
        "headshot": null,
        "color": null,
        "isGrey": null,
        "menuPlace": null,
        "create_time": null,
        "update_time": null
    },
    "map": {}
}
错误响应


收藏博文------(暂时不考虑做)


3. 举报------(暂时不考虑做)

4. 查看博文
4.0 查询推荐的博文
博文实体类的字段说明

url 
GET	http://localhost:6868/public/selectAdviceBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是用户的专栏列表以及详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示推荐的博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 1,
            "blogId": "124214o1p2jr",
            "userId": "1",
            "title": "springboot后端企业开发框架",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 10029,
            "commentAmount": 25,
            "likeReadCommentAmount": 10054,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-10 23:00:47",
            "updateTime": "2022-12-10 23:00:47"
        },
        {
            "id": 40,
            "blogId": "2e0e0716-6ec0-4145-8022-872661a825ba",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 5745,
            "commentAmount": 0,
            "likeReadCommentAmount": 5745,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 34,
            "blogId": "96358b6f-bd96-42ac-8126-e762a6c9cad7",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 4234,
            "commentAmount": 0,
            "likeReadCommentAmount": 4234,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 12,
            "blogId": "214211124214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2432,
            "commentAmount": 1,
            "likeReadCommentAmount": 2433,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:21:57",
            "updateTime": "2022-12-11 17:21:57"
        },
        {
            "id": 23,
            "blogId": "feb3d261-1e12-4e66-8517-39660de18796",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 657,
            "commentAmount": 0,
            "likeReadCommentAmount": 657,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 35,
            "blogId": "bfc105f1-c298-40d5-b3b1-5060fdf4c7ef",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 535,
            "commentAmount": 0,
            "likeReadCommentAmount": 535,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 28,
            "blogId": "a06dfd9a-fe06-4d27-a3ce-0ab48bd0eb9c",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 234,
            "commentAmount": 0,
            "likeReadCommentAmount": 234,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 25,
            "blogId": "29c2d7b3-ea41-469d-aede-3ea7c686bfa1",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 233,
            "commentAmount": 0,
            "likeReadCommentAmount": 233,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 20,
            "blogId": "25cdc66e-5f8e-42e5-973c-a99b1cc2afd9",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 213,
            "commentAmount": 0,
            "likeReadCommentAmount": 213,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 27,
            "blogId": "5d53fb6c-9ce1-4088-8b30-fc2857a25c0d",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 124,
            "commentAmount": 0,
            "likeReadCommentAmount": 124,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        }
    ],
    "map": {
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应
4.1 查询最新博文（时间降序）
博文实体类的字段说明

url 
GET	http://localhost:6868/public/selectLatestBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是博文列表以及博文的详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 74,
            "blogId": "612448cf-c35f-48ed-bb7c-fe96606dd8b8",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:46",
            "updateTime": "2022-12-17 23:37:46"
        },
        {
            "id": 65,
            "blogId": "78f73958-ac17-4211-8e0c-fdc658a191e5",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 66,
            "blogId": "cdedf265-9e37-49b5-9c69-5ec6cb9a7354",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 67,
            "blogId": "437507a9-8cdd-4658-a3d3-4d9456f34504",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 68,
            "blogId": "439b5c3b-9e8f-4aa8-bfc2-2c0d439a168b",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 69,
            "blogId": "f0decf8c-0e7c-4ee1-ace2-264f10dadc21",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 70,
            "blogId": "8cb01673-817d-43a2-87bd-8d37aa62aed1",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 71,
            "blogId": "4bc6135b-b085-4639-8459-d1a759296598",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 72,
            "blogId": "16af9602-6897-4ea8-9e74-c408d6e88fb6",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 73,
            "blogId": "d1ea34ca-89ee-403a-9532-ef85b0a033e1",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        }
    ],
    "map": {
        "total": 73,
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应

4.2 热门博文
博文实体类的字段说明

url 
GET	http://localhost:6868/public/selectHotBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是博文列表以及博文的详细数据
{
    "code": 200,
    "msg": "total（实际获取博文总数），热榜博文一次性全部获取，最多100篇, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 1,
            "blogId": "124214o1p2jr",
            "userId": "1",
            "title": "springboot后端企业开发框架",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 10029,
            "commentAmount": 25,
            "likeReadCommentAmount": 10054,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-10 23:00:47",
            "updateTime": "2022-12-10 23:00:47"
        },
        {
            "id": 40,
            "blogId": "2e0e0716-6ec0-4145-8022-872661a825ba",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 5745,
            "commentAmount": 0,
            "likeReadCommentAmount": 5745,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 34,
            "blogId": "96358b6f-bd96-42ac-8126-e762a6c9cad7",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 4234,
            "commentAmount": 0,
            "likeReadCommentAmount": 4234,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 12,
            "blogId": "214211124214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2432,
            "commentAmount": 1,
            "likeReadCommentAmount": 2433,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:21:57",
            "updateTime": "2022-12-11 17:21:57"
        },
        {
            "id": 23,
            "blogId": "feb3d261-1e12-4e66-8517-39660de18796",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 657,
            "commentAmount": 0,
            "likeReadCommentAmount": 657,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 35,
            "blogId": "bfc105f1-c298-40d5-b3b1-5060fdf4c7ef",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 535,
            "commentAmount": 0,
            "likeReadCommentAmount": 535,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 28,
            "blogId": "a06dfd9a-fe06-4d27-a3ce-0ab48bd0eb9c",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 234,
            "commentAmount": 0,
            "likeReadCommentAmount": 234,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 25,
            "blogId": "29c2d7b3-ea41-469d-aede-3ea7c686bfa1",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 233,
            "commentAmount": 0,
            "likeReadCommentAmount": 233,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 20,
            "blogId": "25cdc66e-5f8e-42e5-973c-a99b1cc2afd9",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 213,
            "commentAmount": 0,
            "likeReadCommentAmount": 213,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 27,
            "blogId": "5d53fb6c-9ce1-4088-8b30-fc2857a25c0d",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 124,
            "commentAmount": 0,
            "likeReadCommentAmount": 124,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 6,
            "blogId": "12",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 100,
            "commentAmount": 1,
            "likeReadCommentAmount": 101,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:13:14",
            "updateTime": "2022-12-11 17:13:14"
        },
        {
            "id": 31,
            "blogId": "035c3ca9-34b5-4353-b857-f3d8d40716ec",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 99,
            "commentAmount": 0,
            "likeReadCommentAmount": 99,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 29,
            "blogId": "94782e21-1618-4dcb-ae06-7680e3de340e",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 89,
            "commentAmount": 0,
            "likeReadCommentAmount": 234,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 30,
            "blogId": "e713ea15-4bfd-46d9-b79e-aef7155f32e6",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 79,
            "commentAmount": 0,
            "likeReadCommentAmount": 79,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 10,
            "blogId": "214214214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 67,
            "commentAmount": 1,
            "likeReadCommentAmount": 68,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:18:46",
            "updateTime": "2022-12-11 17:18:46"
        },
        {
            "id": 2,
            "blogId": "124214o1ps2jr",
            "userId": "1",
            "title": "后端",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 57,
            "commentAmount": 1,
            "likeReadCommentAmount": 58,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 16:40:56",
            "updateTime": "2022-12-11 16:40:56"
        },
        {
            "id": 21,
            "blogId": "a72ec30c-6f32-4e8b-84d1-15494e252537",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 56,
            "commentAmount": 0,
            "likeReadCommentAmount": 56,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 18,
            "blogId": "322ff35a-fbfe-4d4e-bee1-e652377f49ab",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 55,
            "commentAmount": 0,
            "likeReadCommentAmount": 55,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 26,
            "blogId": "5e613249-69af-42fd-9ff6-784a07350788",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 53,
            "commentAmount": 0,
            "likeReadCommentAmount": 53,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 7,
            "blogId": "123",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 44,
            "commentAmount": 1,
            "likeReadCommentAmount": 45,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:13:45",
            "updateTime": "2022-12-11 17:13:45"
        },
        {
            "id": 37,
            "blogId": "c20e4f42-43fe-4e23-8285-e5fade8ffe7e",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 44,
            "commentAmount": 0,
            "likeReadCommentAmount": 44,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 15,
            "blogId": "f10cc656-55c7-4958-a1ae-ac3bc386324f",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 43,
            "commentAmount": 0,
            "likeReadCommentAmount": 43,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 41,
            "blogId": "55868cd3-ab4d-40e2-a8c5-f7dae7b34cf8",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 43,
            "commentAmount": 0,
            "likeReadCommentAmount": 43,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 42,
            "blogId": "c769eac6-a3c8-469a-97f6-e4e8518a0447",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 42,
            "commentAmount": 0,
            "likeReadCommentAmount": 42,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 5,
            "blogId": "1242141421412",
            "userId": "1",
            "title": "java后端",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 36,
            "commentAmount": 1,
            "likeReadCommentAmount": 37,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:11:40",
            "updateTime": "2022-12-11 17:11:40"
        },
        {
            "id": 16,
            "blogId": "13e5a249-5115-40b0-bc09-a1b65af56520",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 34,
            "commentAmount": 0,
            "likeReadCommentAmount": 34,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 14,
            "blogId": "8ea0feb9-f28f-4459-93af-a452910c062f",
            "userId": "1",
            "title": "1111111111111111111",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 1,
            "collectAmount": 0,
            "readAmount": 33,
            "commentAmount": 1,
            "likeReadCommentAmount": 35,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:28",
            "updateTime": "2022-12-14 10:25:28"
        },
        {
            "id": 17,
            "blogId": "6c9f5f83-a88a-4f48-a51d-28b1a43768ac",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 33,
            "commentAmount": 0,
            "likeReadCommentAmount": 33,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 39,
            "blogId": "ea85a39f-0962-4b97-96bd-e9d49bb93712",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 23,
            "commentAmount": 0,
            "likeReadCommentAmount": 23,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 36,
            "blogId": "33b84bd7-2479-4a8b-9ccc-073e29e5667b",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 22,
            "commentAmount": 0,
            "likeReadCommentAmount": 22,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 38,
            "blogId": "0775e1f2-de67-4f85-ac63-71e61b523023",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 12,
            "commentAmount": 0,
            "likeReadCommentAmount": 12,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 43,
            "blogId": "94c69df3-470c-43e0-85dc-0a0f7a0ef5e4",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 12,
            "commentAmount": 0,
            "likeReadCommentAmount": 12,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30"
        },
        {
            "id": 8,
            "blogId": "123",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 6,
            "commentAmount": 1,
            "likeReadCommentAmount": 7,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:15:43",
            "updateTime": "2022-12-11 17:15:43"
        },
        {
            "id": 9,
            "blogId": "12421412",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 5,
            "commentAmount": 1,
            "likeReadCommentAmount": 6,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:17:46",
            "updateTime": "2022-12-11 17:17:46"
        },
        {
            "id": 22,
            "blogId": "85b718fa-7de9-436f-a841-c19a1ccfe2bd",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 5,
            "commentAmount": 0,
            "likeReadCommentAmount": 5,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 4,
            "blogId": "214253",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "后端只是很重要",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 4,
            "commentAmount": 1,
            "likeReadCommentAmount": 5,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:01:30",
            "updateTime": "2022-12-11 17:01:30"
        },
        {
            "id": 32,
            "blogId": "7e504639-c3cc-4793-9607-a0a29e4505d1",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 4,
            "commentAmount": 0,
            "likeReadCommentAmount": 4,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 3,
            "blogId": "2142521512",
            "userId": "1",
            "title": "如何提升代码效率2",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "企业开发框架",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2,
            "commentAmount": 1,
            "likeReadCommentAmount": 3,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 16:59:32",
            "updateTime": "2022-12-11 16:59:32"
        },
        {
            "id": 24,
            "blogId": "89ccb5ec-4c8e-46e4-b8db-35a8850b2543",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 33,
            "blogId": "ed8d659b-e4c3-4efe-8c47-5165dd171c88",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2,
            "commentAmount": 0,
            "likeReadCommentAmount": 2,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 13,
            "blogId": "aff32tr134t143g",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 1,
            "commentAmount": 1,
            "likeReadCommentAmount": 2,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:23:37",
            "updateTime": "2022-12-11 17:23:37"
        },
        {
            "id": 19,
            "blogId": "0dff0e01-1264-4935-9569-23964a7747e2",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 1,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29"
        },
        {
            "id": 44,
            "blogId": "dsgsrdgrshgsrhgsrhr",
            "userId": "2",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": null,
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": null,
            "ps": null,
            "createTime": "2022-12-16 16:40:56",
            "updateTime": "2022-12-16 16:40:56"
        },
        {
            "id": 45,
            "blogId": "6075e91e-b992-4175-9b80-740ae30cb022",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:42",
            "updateTime": "2022-12-17 23:37:42"
        },
        {
            "id": 46,
            "blogId": "447633e8-936c-42b6-95b0-066229f78eaa",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 47,
            "blogId": "086dca28-1901-4a0b-bb81-5f9120a93658",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 48,
            "blogId": "b7433293-c5af-4eb9-babd-f02a97356e42",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 49,
            "blogId": "1eecd4e9-d9aa-49cd-911a-2409d4a5177f",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 50,
            "blogId": "f713d9f1-df7b-4c81-815e-6e0a2a012bc2",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 51,
            "blogId": "973e9f8d-c286-4286-bf54-57757dec6e71",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 52,
            "blogId": "c45cb1e5-e787-4bc8-a780-fb6bd93e3912",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 53,
            "blogId": "bb93873b-9d87-4d15-be12-91df7a577ea5",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 54,
            "blogId": "180bc75b-9ce9-4c9e-b83e-2ef527692a0d",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:43",
            "updateTime": "2022-12-17 23:37:43"
        },
        {
            "id": 55,
            "blogId": "d750e5d2-94f2-477f-80fa-e8f46f1aa978",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 56,
            "blogId": "96abacb7-0692-4476-988f-53dc3249d412",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 57,
            "blogId": "fa93092c-537c-4c8b-8a42-891a555e6651",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 58,
            "blogId": "6a4ef355-d9fb-4956-b2d1-0b020eb174cd",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 59,
            "blogId": "ad8fab9d-446f-4d57-91c5-ff4ccbf89064",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 60,
            "blogId": "0bd41e58-1397-4a04-ac6b-6cdc963c6fec",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 61,
            "blogId": "e2f60eb3-507f-4ea2-8786-0f61f7f903de",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 62,
            "blogId": "7ea19c72-464f-4017-a04a-0d7ffb50f012",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 63,
            "blogId": "f6175cf9-50c0-4946-8311-b5534edef9df",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 64,
            "blogId": "9cd52383-16f9-464d-8e82-93c979db14a4",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:44",
            "updateTime": "2022-12-17 23:37:44"
        },
        {
            "id": 65,
            "blogId": "78f73958-ac17-4211-8e0c-fdc658a191e5",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 66,
            "blogId": "cdedf265-9e37-49b5-9c69-5ec6cb9a7354",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 67,
            "blogId": "437507a9-8cdd-4658-a3d3-4d9456f34504",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 68,
            "blogId": "439b5c3b-9e8f-4aa8-bfc2-2c0d439a168b",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 69,
            "blogId": "f0decf8c-0e7c-4ee1-ace2-264f10dadc21",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 70,
            "blogId": "8cb01673-817d-43a2-87bd-8d37aa62aed1",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 71,
            "blogId": "4bc6135b-b085-4639-8459-d1a759296598",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 72,
            "blogId": "16af9602-6897-4ea8-9e74-c408d6e88fb6",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 73,
            "blogId": "d1ea34ca-89ee-403a-9532-ef85b0a033e1",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:45",
            "updateTime": "2022-12-17 23:37:45"
        },
        {
            "id": 74,
            "blogId": "612448cf-c35f-48ed-bb7c-fe96606dd8b8",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-17 23:37:46",
            "updateTime": "2022-12-17 23:37:46"
        }
    ],
    "map": {
        "total": 73,
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应
4.3.1 个人 分类博文
博文实体类的字段说明

url 
GET	http://localhost:6868/blog/selectBlogListByUserIdCategoryId
请求示例
参数名	类型	值	说明
userId	String	1	用户id
categoryId	String	1	种类id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是博文列表以及博文的详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示专栏的博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 2,
            "blogId": "124214o1ps2jr",
            "userId": "1",
            "title": "如何提升代码效率1",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 16时40分56秒",
            "updateTime": "2022年12月11日 16时40分56秒"
        },
        {
            "id": 1,
            "blogId": "124214o1p2jr",
            "userId": "1",
            "title": "如何提升代码效率",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 5,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月10日 23时00分47秒",
            "updateTime": "2022年12月10日 23时00分47秒"
        }
    ],
    "map": {
        "total": 2,
        "havaLikedList": [
            false,
            false
        ]
    }
}
错误响应

4.3.2 首页 分类博文
博文实体类的字段说明

url 
GET	http://localhost:6868/blog/selectBlogListByCategoryId
请求示例
参数名	类型	值	说明
userId	String	1	用户id
categoryId	String	1	种类id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是博文列表以及博文的详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示专栏的博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 2,
            "blogId": "124214o1ps2jr",
            "userId": "1",
            "title": "如何提升代码效率1",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 16时40分56秒",
            "updateTime": "2022年12月11日 16时40分56秒"
        },
        {
            "id": 1,
            "blogId": "124214o1p2jr",
            "userId": "1",
            "title": "如何提升代码效率",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 5,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月10日 23时00分47秒",
            "updateTime": "2022年12月10日 23时00分47秒"
        }
    ],
    "map": {
        "total": 2,
        "havaLikedList": [
            false,
            false
        ]
    }
}
错误响应

4.4 根据专栏查询博文
博文实体类的字段说明

url 
GET	http://175.178.37.103:6868/blog/selectBlogListByColumnId
请求示例
参数名	类型	值	说明
userId	String	1	用户id
columnId	String	efgf34qt1t24grewg	专栏id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是博文列表以及博文的详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示专栏的博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 10,
            "blogId": "214214214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时18分46秒",
            "updateTime": "2022年12月11日 17时18分46秒"
        },
        {
            "id": 2,
            "blogId": "124214o1ps2jr",
            "userId": "1",
            "title": "如何提升代码效率1",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 16时40分56秒",
            "updateTime": "2022年12月11日 16时40分56秒"
        },
        {
            "id": 1,
            "blogId": "124214o1p2jr",
            "userId": "1",
            "title": "如何提升代码效率",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 5,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月10日 23时00分47秒",
            "updateTime": "2022年12月10日 23时00分47秒"
        }
    ],
    "map": {
        "total": 3,
        "havaLikedList": [
            false,
            false,
            false
        ]
    }
}
错误响应

4.5 查询个人用户的所有专栏情况
专栏实体类的字段说明

url 
GET	http://localhost:6868/blog/selectColumnByUserId
请求示例
参数名	类型	值	说明
userId	String	1	用户id
成功响应
状态码：200 OK
响应示例：data 中是用户的专栏列表以及详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示用户的专栏总数",
    "data": [
        {
            "id": "1",
            "columnId": "12421ji21or21",
            "column_name": "springboot",
            "discription": "比较流行的java后端企业开发框架",
            "userId": "1",
            "cover": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "blogAmount": 1
        },
        {
            "id": "2",
            "columnId": "efgf34qt1t24grewg",
            "column_name": "java",
            "discription": "比较流行的编程语言",
            "userId": "1",
            "cover": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "blogAmount": 3
        }
    ],
    "map": {
        "total": 2
    }
}
错误响应


4.6.1查询自己的审核通过的博文（分页查询，按时间降序）
博文实体类的字段说明

url 
GET	http://localhost:6868/blog/selectOwnBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是用户的专栏列表以及详细数据
{
    "code": 200,
    "msg": "用户的总博文数量在用户信息的中的blogAmount, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 13,
            "blogId": "aff32tr134t143g",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时23分37秒",
            "updateTime": "2022年12月11日 17时23分37秒"
        },
        {
            "id": 12,
            "blogId": "214211124214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时21分57秒",
            "updateTime": "2022年12月11日 17时21分57秒"
        },
        {
            "id": 11,
            "blogId": "2142114214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时19分43秒",
            "updateTime": "2022年12月11日 17时19分43秒"
        },
        {
            "id": 10,
            "blogId": "214214214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时18分46秒",
            "updateTime": "2022年12月11日 17时18分46秒"
        },
        {
            "id": 9,
            "blogId": "12421412",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时17分46秒",
            "updateTime": "2022年12月11日 17时17分46秒"
        },
        {
            "id": 8,
            "blogId": "123",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时15分43秒",
            "updateTime": "2022年12月11日 17时15分43秒"
        },
        {
            "id": 7,
            "blogId": "123",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时13分45秒",
            "updateTime": "2022年12月11日 17时13分45秒"
        },
        {
            "id": 6,
            "blogId": "12",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时13分14秒",
            "updateTime": "2022年12月11日 17时13分14秒"
        },
        {
            "id": 5,
            "blogId": "1242141421412",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时11分40秒",
            "updateTime": "2022年12月11日 17时11分40秒"
        },
        {
            "id": 4,
            "blogId": "214253",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 0,
            "commentAmount": 1,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022年12月11日 17时01分30秒",
            "updateTime": "2022年12月11日 17时01分30秒"
        }
    ],
    "map": {
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应


4.6.2查看自己的审核不通过的博文（分页查询，按时间降序）
博文实体类的字段说明

url 
GET	http://localhost:6868/blog/selectOwnFailBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "map中的 total : 用户的审核不通过的总博文数量",
    "data": [
        {
            "id": 8,
            "blogId": "1234",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 6,
            "commentAmount": 1,
            "likeReadCommentAmount": 7,
            "ableLook": 1,
            "status": 0,
            "ps": "有侵权嫌疑",
            "createTime": "2022-12-11 17:15:43",
            "updateTime": "2022-12-11 17:15:43",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 7,
            "blogId": "123",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 44,
            "commentAmount": 1,
            "likeReadCommentAmount": 45,
            "ableLook": 1,
            "status": 0,
            "ps": "有侵权嫌疑",
            "createTime": "2022-12-11 17:13:45",
            "updateTime": "2022-12-11 17:13:45",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        }
    ],
    "map": {
        "total": 2
    }
}
错误响应
4.6.3查看自己的草稿博文（分页查询，按时间降序）
博文实体类的字段说明

url 
GET	http://localhost:6868/blog/selectOwnDraftFailBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "map中的 total : 用户的草稿的总博文数量",
    "data": [
        {
            "id": 17,
            "blogId": "6c9f5f83-a88a-4f48-a51d-28b1a43768ac",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 33,
            "commentAmount": 0,
            "likeReadCommentAmount": 33,
            "ableLook": 1,
            "status": 3,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        }
    ],
    "map": {
        "total": 1
    }
错误响应
4.6.4查看自己的待审核的博文（分页查询，按时间降序）
博文实体类的字段说明

url 
GET	http://localhost:6868/blog/selectOwnNotAuditFailBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "map中的 total : 用户的待审核的的总博文数量",
    "data": [
        {
            "id": 27,
            "blogId": "5d53fb6c-9ce1-4088-8b30-fc2857a25c0d",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 124,
            "commentAmount": 0,
            "likeReadCommentAmount": 124,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 15,
            "blogId": "f10cc656-55c7-4958-a1ae-ac3bc386324f",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 43,
            "commentAmount": 0,
            "likeReadCommentAmount": 43,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 16,
            "blogId": "13e5a249-5115-40b0-bc09-a1b65af56520",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 34,
            "commentAmount": 0,
            "likeReadCommentAmount": 34,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 18,
            "blogId": "322ff35a-fbfe-4d4e-bee1-e652377f49ab",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 55,
            "commentAmount": 0,
            "likeReadCommentAmount": 55,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 21,
            "blogId": "a72ec30c-6f32-4e8b-84d1-15494e252537",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 56,
            "commentAmount": 0,
            "likeReadCommentAmount": 56,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 24,
            "blogId": "89ccb5ec-4c8e-46e4-b8db-35a8850b2543",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 14,
            "blogId": "8ea0feb9-f28f-4459-93af-a452910c062f",
            "userId": "1",
            "title": "1111111111111111111",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 1,
            "collectAmount": 0,
            "readAmount": 33,
            "commentAmount": 1,
            "likeReadCommentAmount": 35,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:28",
            "updateTime": "2022-12-14 10:25:28",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 13,
            "blogId": "aff32tr134t143g",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 1,
            "commentAmount": 1,
            "likeReadCommentAmount": 2,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-11 17:23:37",
            "updateTime": "2022-12-11 17:23:37",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 12,
            "blogId": "214211124214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2432,
            "commentAmount": 1,
            "likeReadCommentAmount": 2433,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-11 17:21:57",
            "updateTime": "2022-12-11 17:21:57",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        },
        {
            "id": 10,
            "blogId": "214214214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 67,
            "commentAmount": 1,
            "likeReadCommentAmount": 68,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-11 17:18:46",
            "updateTime": "2022-12-11 17:18:46",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": false
        }
    ],
    "map": {
        "total": 12
    }
}
错误响应
4.7 查询一篇博文的详细情况
博文实体类的字段说明

url 
GET	http://localhost:6868/blog/selectOneBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
blogId	String	124214o1p2jr	博文id
成功响应
状态码：200 OK
响应示例：data 中是用户的专栏列表以及详细数据
{
    "code": 200,
    "msg": "map中的haveLiked: true-点赞过了需标红，false-没点赞过",
    "data": {
        "id": 1,
        "blogId": "124214o1p2jr",
        "userId": "1",
        "title": "如何提升代码效率",
        "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
        "discription": "掌握一些巧妙的方法有助于开发",
        "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
        "tag": "java springboot",
        "likeAmount": 0,
        "collectAmount": 0,
        "readAmount": 0,
        "commentAmount": 5,
        "ableLook": 1,
        "status": 1,
        "ps": null,
        "createTime": "2022年12月10日 23时00分47秒",
        "updateTime": "2022年12月10日 23时00分47秒"
    },
    "map": {
        "havaLiked": false
    }
}
错误响应

4.8 综合排序 搜索 博文

url 
GET	http://localhost:6868/public/searchAdviceBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
keyword	String	springboot后端框架	输入的关键字
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是用户的专栏列表以及详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 1,
            "blogId": "124214o1p2jr",
            "userId": "1",
            "title": "springboot后端企业开发框架",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 10029,
            "commentAmount": 25,
            "likeReadCommentAmount": 10054,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-10 23:00:47",
            "updateTime": "2022-12-10 23:00:47"
        },
        {
            "id": 2,
            "blogId": "124214o1ps2jr",
            "userId": "1",
            "title": "后端",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 57,
            "commentAmount": 1,
            "likeReadCommentAmount": 58,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 16:40:56",
            "updateTime": "2022-12-11 16:40:56"
        },
        {
            "id": 5,
            "blogId": "1242141421412",
            "userId": "1",
            "title": "java后端",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 36,
            "commentAmount": 1,
            "likeReadCommentAmount": 37,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:11:40",
            "updateTime": "2022-12-11 17:11:40"
        },
        {
            "id": 4,
            "blogId": "214253",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "后端只是很重要",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 4,
            "commentAmount": 1,
            "likeReadCommentAmount": 5,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:01:30",
            "updateTime": "2022-12-11 17:01:30"
        },
        {
            "id": 3,
            "blogId": "2142521512",
            "userId": "1",
            "title": "如何提升代码效率2",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "企业开发框架",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2,
            "commentAmount": 1,
            "likeReadCommentAmount": 3,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 16:59:32",
            "updateTime": "2022-12-11 16:59:32"
        }
    ],
    "map": {
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应
4.9 最热优先 搜索 博文
博文实体类的字段说明

url 
GET	http://localhost:6868/public/searchHotBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
keyword	String	springboot后端框架	输入的关键字
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是用户的专栏列表以及详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 1,
            "blogId": "124214o1p2jr",
            "userId": "1",
            "title": "springboot后端企业开发框架",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 10029,
            "commentAmount": 25,
            "likeReadCommentAmount": 10054,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-10 23:00:47",
            "updateTime": "2022-12-10 23:00:47"
        },
        {
            "id": 2,
            "blogId": "124214o1ps2jr",
            "userId": "1",
            "title": "后端",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 57,
            "commentAmount": 1,
            "likeReadCommentAmount": 58,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 16:40:56",
            "updateTime": "2022-12-11 16:40:56"
        },
        {
            "id": 5,
            "blogId": "1242141421412",
            "userId": "1",
            "title": "java后端",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 36,
            "commentAmount": 1,
            "likeReadCommentAmount": 37,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:11:40",
            "updateTime": "2022-12-11 17:11:40"
        },
        {
            "id": 4,
            "blogId": "214253",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "后端只是很重要",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 4,
            "commentAmount": 1,
            "likeReadCommentAmount": 5,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:01:30",
            "updateTime": "2022-12-11 17:01:30"
        },
        {
            "id": 3,
            "blogId": "2142521512",
            "userId": "1",
            "title": "如何提升代码效率2",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "企业开发框架",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2,
            "commentAmount": 1,
            "likeReadCommentAmount": 3,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 16:59:32",
            "updateTime": "2022-12-11 16:59:32"
        }
    ],
    "map": {
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应

4.10 最新优先 搜索 博文
博文实体类的字段说明

url 
GET	http://localhost:6868/public/searchLatestBlog
请求示例
参数名	类型	值	说明
userId	String	1	用户id
keyword	String	springboot后端框架	输入的关键字
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：data 中是用户的专栏列表以及详细数据
{
    "code": 200,
    "msg": "map 中的 total 表示博文总数, map中的havaLikedList的值表示对应索引的博文是否被点赞过",
    "data": [
        {
            "id": 5,
            "blogId": "1242141421412",
            "userId": "1",
            "title": "java后端",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 36,
            "commentAmount": 1,
            "likeReadCommentAmount": 37,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:11:40",
            "updateTime": "2022-12-11 17:11:40"
        },
        {
            "id": 4,
            "blogId": "214253",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "后端只是很重要",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 4,
            "commentAmount": 1,
            "likeReadCommentAmount": 5,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 17:01:30",
            "updateTime": "2022-12-11 17:01:30"
        },
        {
            "id": 3,
            "blogId": "2142521512",
            "userId": "1",
            "title": "如何提升代码效率2",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "企业开发框架",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2,
            "commentAmount": 1,
            "likeReadCommentAmount": 3,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 16:59:32",
            "updateTime": "2022-12-11 16:59:32"
        },
        {
            "id": 2,
            "blogId": "124214o1ps2jr",
            "userId": "1",
            "title": "后端",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 57,
            "commentAmount": 1,
            "likeReadCommentAmount": 58,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-11 16:40:56",
            "updateTime": "2022-12-11 16:40:56"
        },
        {
            "id": 1,
            "blogId": "124214o1p2jr",
            "userId": "1",
            "title": "springboot后端企业开发框架",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 10029,
            "commentAmount": 25,
            "likeReadCommentAmount": 10054,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-10 23:00:47",
            "updateTime": "2022-12-10 23:00:47"
        }
    ],
    "map": {
        "havaLikedList": [
            false,
            false,
            false,
            false,
            false
        ]
    }
}
错误响应
4.11  博文浏览量+1
url 
PUT	http://localhost:6868/blog/blogReadAmountAddOne
请求示例
参数名	类型	值	说明
blogId	String	0c04d24d-36ba-4d27-a226-3cf13ed04eef	blogId
成功响应d
状态码：200 OK
响应示例：

错误响应


后台管理系统接口（端口：6666）
1. 获取已经审核记录
博文实体类的字段说明

url 
GET	http://175.178.37.103:7777/admin/getHaveAuditedBlogs
请求示例
参数名	类型	值	说明
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": null,
    "data": [
        {
            "id": 36,
            "blogId": "33b84bd7-2479-4a8b-9ccc-073e29e5667b",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 22,
            "commentAmount": 0,
            "likeReadCommentAmount": 22,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 43,
            "blogId": "94c69df3-470c-43e0-85dc-0a0f7a0ef5e4",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 12,
            "commentAmount": 0,
            "likeReadCommentAmount": 12,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 39,
            "blogId": "ea85a39f-0962-4b97-96bd-e9d49bb93712",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 23,
            "commentAmount": 0,
            "likeReadCommentAmount": 23,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 35,
            "blogId": "bfc105f1-c298-40d5-b3b1-5060fdf4c7ef",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 535,
            "commentAmount": 0,
            "likeReadCommentAmount": 535,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 42,
            "blogId": "c769eac6-a3c8-469a-97f6-e4e8518a0447",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 42,
            "commentAmount": 0,
            "likeReadCommentAmount": 42,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 34,
            "blogId": "96358b6f-bd96-42ac-8126-e762a6c9cad7",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 4234,
            "commentAmount": 0,
            "likeReadCommentAmount": 4234,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 37,
            "blogId": "c20e4f42-43fe-4e23-8285-e5fade8ffe7e",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 44,
            "commentAmount": 0,
            "likeReadCommentAmount": 44,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 38,
            "blogId": "0775e1f2-de67-4f85-ac63-71e61b523023",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 12,
            "commentAmount": 0,
            "likeReadCommentAmount": 12,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 40,
            "blogId": "2e0e0716-6ec0-4145-8022-872661a825ba",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 5745,
            "commentAmount": 0,
            "likeReadCommentAmount": 5745,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 41,
            "blogId": "55868cd3-ab4d-40e2-a8c5-f7dae7b34cf8",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 43,
            "commentAmount": 0,
            "likeReadCommentAmount": 43,
            "ableLook": 1,
            "status": 1,
            "ps": null,
            "createTime": "2022-12-14 10:25:30",
            "updateTime": "2022-12-14 10:25:30",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        }
    ],
    "map": {
        "total": 30
    }
}
错误响应

2. 获取待审核博文
博文实体类的字段说明

url 
GET	http://175.178.37.103:7777/admin/getAuditBlogs
请求示例
参数名	类型	值	说明
page	String	1	页码
size	String	10	大小
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": null,
    "data": [
        {
            "id": 27,
            "blogId": "5d53fb6c-9ce1-4088-8b30-fc2857a25c0d",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 124,
            "commentAmount": 0,
            "likeReadCommentAmount": 124,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 15,
            "blogId": "f10cc656-55c7-4958-a1ae-ac3bc386324f",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 43,
            "commentAmount": 0,
            "likeReadCommentAmount": 43,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 16,
            "blogId": "13e5a249-5115-40b0-bc09-a1b65af56520",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 34,
            "commentAmount": 0,
            "likeReadCommentAmount": 34,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 18,
            "blogId": "322ff35a-fbfe-4d4e-bee1-e652377f49ab",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 55,
            "commentAmount": 0,
            "likeReadCommentAmount": 55,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 21,
            "blogId": "a72ec30c-6f32-4e8b-84d1-15494e252537",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 56,
            "commentAmount": 0,
            "likeReadCommentAmount": 56,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 24,
            "blogId": "89ccb5ec-4c8e-46e4-b8db-35a8850b2543",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2,
            "commentAmount": 0,
            "likeReadCommentAmount": 0,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:29",
            "updateTime": "2022-12-14 10:25:29",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 14,
            "blogId": "8ea0feb9-f28f-4459-93af-a452910c062f",
            "userId": "1",
            "title": "1111111111111111111",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 1,
            "collectAmount": 0,
            "readAmount": 33,
            "commentAmount": 1,
            "likeReadCommentAmount": 35,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-14 10:25:28",
            "updateTime": "2022-12-14 10:25:28",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 13,
            "blogId": "aff32tr134t143g",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 1,
            "commentAmount": 1,
            "likeReadCommentAmount": 2,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-11 17:23:37",
            "updateTime": "2022-12-11 17:23:37",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 12,
            "blogId": "214211124214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 2432,
            "commentAmount": 1,
            "likeReadCommentAmount": 2433,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-11 17:21:57",
            "updateTime": "2022-12-11 17:21:57",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        },
        {
            "id": 10,
            "blogId": "214214214214",
            "userId": "1",
            "title": "如何提升代码效率3",
            "publishImage": "http://8.134.164.93:9001/file/20221201_1669893285_614.jpg",
            "discription": "掌握一些巧妙的方法有助于开发",
            "content": "怎么说呢，一言难尽，长话短说吧，不过说来话长 http://8.134.164.93:9001/file/20221201_1669893285_614.jpg 对吧。。。",
            "tag": "java springboot",
            "likeAmount": 0,
            "collectAmount": 0,
            "readAmount": 67,
            "commentAmount": 1,
            "likeReadCommentAmount": 68,
            "ableLook": 1,
            "status": 2,
            "ps": null,
            "createTime": "2022-12-11 17:18:46",
            "updateTime": "2022-12-11 17:18:46",
            "userName": "陈振宇",
            "headshot": null,
            "haveLiked": null
        }
    ],
    "map": {
        "total": 12
    }
}
错误响应


3. 获取待审核博文
博文实体类的字段说明

url 
POST	http://175.178.37.103:7777/admin/auditBlog
请求示例
参数名	类型	值	说明
userId	String	1	博文的用户id
blogId	String	123	博文id
status	int	0	审核状态
ps	String	有侵权嫌疑	审核说明
成功响应
状态码：200 OK
响应示例：
{
    "code": 200,
    "msg": "已完成这次审核",
    "data": null,
    "map": {}
}
错误响应
