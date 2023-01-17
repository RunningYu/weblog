# weblog
博客平台

技术栈：


- **jwt + redis + token** : 用户权限 
- **Caffeine+Redis** :实现两级缓存，对热门博文和最新博文进行优化，提升查询效率，减轻数据库压力
  - **Caffeine**：用本地进程缓存来缓存数据，（由于存储在内存中）实现快速的数据的读取，大量减少对数据库的访问，减少数据库的压力
- **RabbitMQ**：进行数据一致性和可靠性处理；同步业务，异步处理，提高客户端的响应速度；
- 定时任务 + 监听器：
  - **线程池**：定时更新热点帖子的缓存（每天 2 : 00 定时更新）
    - 线程池代码：[(10条消息) 线程池应用_定时执行任务_其然乐衣的博客-CSDN博客](https://blog.csdn.net/QRLYLETITBE/article/details/127352326?spm=1001.2014.3001.5501)
    - 注解方式：[(10条消息) java中如何实现一个定时任务_码到成功@的博客-CSDN博客_java定时任务怎么启动](https://blog.csdn.net/guliudeng/article/details/119742287)
  - **Quartz**： Quartz任务调度框架和Springboot的 监听器 共同实现任务调度功能，并实现了定时计算帖子分数、更新热门帖子等，对系统热门进行优化
- **MinIO**: 在云服务器上安装**MinIO**高性能对象存储来对项目中的图片进行存储
- **Elasticsearch**：搜索引擎提供便捷、快速、多功能的查询API，如聚合、过滤、范围、模糊等查询

weblog项目的业务设计
![image](https://user-images.githubusercontent.com/105648852/212812633-9f9f759c-b45d-495a-9bbc-38bbcedd84da.png)
