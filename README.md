# weblog
**1. 博客平台简介：**
本平台是一个共享资源的平台，所以也为游客设置了查看博文的权限，主要在于博客大厅。为了方便访问者能快速找到自己想看的博文，博客大厅对博文进行了分类查询，比如，前端、后端、Andriod等。根据博文的相关数据（浏览量、点赞量、评论量、发布时间）还设置了综合查询、最新查询、热榜博文这三个查询。同时还设置了搜索框，可进行关键字查询，而且后台对输入的关键字进行分词，以及对博文的标题和概述进行了分词，然后用输入关键字的分词组对标题和概述的分析组进行模糊查询，实现了更全面、可靠的博文搜索功能。

**2. 技术栈：** SpringBoot/MybatisPlus/Caffeine/Redis/RabbitMQ/Elasticsearch/Minio/Quartz

**3. 技术方案：**
- **jwt + redis + token** : 用户权限 
- **Caffeine+Redis+ES** :实现三级缓存，对热门博文和最新博文进行优化，提升查询效率，减轻数据库压力
  - **Caffeine**：用本地进程缓存来缓存数据，（由于存储在内存中）实现快速的数据的读取，大量减少对数据库的访问，减少数据库的压力
- **RabbitMQ**：
    - 进行数据一致性和可靠性处理；同步业务，异步处理，提高客户端的响应速度；
    - 采用RabbitMQ的**死信交换机**实现消息的延迟投递，如删除博文时，对应的所有评论延迟删除。采用**失败重试机制**避免当消费者出现异常后消息不断requeue而给mq带来不必要的压力。
- Quartz + 监听器：
  - **Quartz**： Quartz任务调度框架和Springboot的 监听器 共同实现任务调度功能，并实现定时计算统计更新博客的排名以及进行数据同步保证
- **MinIO**: 在云服务器上安装**MinIO**高性能对象存储，并通过Springboot整合来对项目中的图片文件进行存储
- **Elasticsearch**：搜索引擎提供便捷、快速、多功能的查询API，如聚合、过滤、范围、模糊等查询


**4. weblog项目的业务设计**

![image](https://user-images.githubusercontent.com/105648852/212812633-9f9f759c-b45d-495a-9bbc-38bbcedd84da.png)

博客大厅：
![image](https://user-images.githubusercontent.com/105648852/226074993-3e15baf2-f8e1-430b-9ef5-d90676ea9045.png)
![image](https://user-images.githubusercontent.com/105648852/226075294-83d468c7-d8ec-4c8c-baec-9e9745b06b15.png)

个人主页：
![image](https://user-images.githubusercontent.com/105648852/226075032-58374e46-c546-4c07-adf6-b57a4f602f36.png)
![image](https://user-images.githubusercontent.com/105648852/226075061-74125735-3a3a-4651-a619-0e750145f8e2.png)
![image](https://user-images.githubusercontent.com/105648852/226075112-47cbf7cb-1985-43d6-9d54-93f60314383c.png)
![image](https://user-images.githubusercontent.com/105648852/226075126-2107f06b-69a5-4749-b3eb-d16e87e031ad.png)
![image](https://user-images.githubusercontent.com/105648852/226075138-f5d92a4a-0c5b-4aad-8718-03ae73f1f409.png)
![image](https://user-images.githubusercontent.com/105648852/226075198-ae6641d9-a158-451b-8336-3dcd0ff0a1fc.png)
![image](https://user-images.githubusercontent.com/105648852/226075220-bfc7e820-8f9e-4c6a-84b0-0a8406626d11.png)
![image](https://user-images.githubusercontent.com/105648852/226075239-ae61a5a8-bfb8-4f40-9c61-639f5aa561a3.png)
![image](https://user-images.githubusercontent.com/105648852/226075258-e0a132d2-f472-4068-bf20-6680f696d632.png)


