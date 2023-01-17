# weblog
**博客平台简介：**
本平台是一个共享资源的平台，所以也为游客设置了查看博文的权限，主要在于博客大厅。为了方便访问者能快速找到自己想看的博文，博客大厅对博文进行了分类查询，比如，前端、后端、Andriod等。根据博文的相关数据（浏览量、点赞量、评论量、发布时间）还设置了综合查询、最新查询、热榜博文这三个查询。同时还设置了搜索框，可进行关键字查询，而且后台对输入的关键字进行分词，以及对博文的标题和概述进行了分词，然后用输入关键字的分词组对标题和概述的分析组进行模糊查询，实现了更全面、可靠的博文搜索功能。

**技术栈：**
- **jwt + redis + token** : 用户权限 
- **Caffeine+Redis** :实现两级缓存，对热门博文和最新博文进行优化，提升查询效率，减轻数据库压力
  - **Caffeine**：用本地进程缓存来缓存数据，（由于存储在内存中）实现快速的数据的读取，大量减少对数据库的访问，减少数据库的压力
- **RabbitMQ**：进行数据一致性和可靠性处理；同步业务，异步处理，提高客户端的响应速度；
- 定时任务 + 监听器：
  - **线程池**：定时更新热点帖子的缓存（每天 2 : 00 定时更新）
  - **Quartz**： Quartz任务调度框架和Springboot的 监听器 共同实现任务调度功能，并实现了定时计算帖子分数、更新热门帖子等，对系统热门进行优化
- **MinIO**: 在云服务器上安装**MinIO**高性能对象存储来对项目中的图片进行存储
- **Elasticsearch**：搜索引擎提供便捷、快速、多功能的查询API，如聚合、过滤、范围、模糊等查询

**weblog项目的业务设计**
![image](https://user-images.githubusercontent.com/105648852/212812633-9f9f759c-b45d-495a-9bbc-38bbcedd84da.png)
