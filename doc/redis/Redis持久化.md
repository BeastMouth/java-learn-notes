## Redis持久化问题

**Redis的持久化机制有两个：快照 和 AOF日志**

- 快照：**一次全量备份**

- AOF日志：**连续的增量备份**（数据库每次重启都要加载AOF日志进行指令重放，这个时间上的消耗将会无比的漫长，所以需要定期进行AOF重写，给AOF日志瘦身）



- 快照原理：（不阻塞线上业务）（rdb）

  一边持久化，一边响应客户端的请求（在服务线上请求的同时，Redis还要进行内存快照-内存快照要求Redis必须进行文件I/O操作-文件I/O操作不能使用多路复用API）【文件I/O会严重拖累服务器性能】

  这样就会出现 一个大型的 hash 字典正在持久化，这时候一个请求要将它删除

  此时使用 操作系统的 **多进程COW**



​		利用操作系统的 fork 函数产生一个子进程

​		子进程和父进程尽可能共享内存资源（所以fork创建的时候，内存资源消耗几乎没有增加）

​		接下来运用到了多进程的COW

​		共享的数据段由很多操作系统的页面组成【每页4KB】

​		然后 此时父进程接着服务于线上服务，不断地修改数据。**每次修改数据都会从原来的共享的页上复制一份		分离出来，然后父进程在这个分离出来的页上修改数据**【父进程不断地修改数据，就会不断地复制页出来。		但是不会超过原来内存的两倍】

​		而子进程仍旧在原来的页上进行操作（页上的数据还是跟一开始fork分离出来的数据一样）【这就是为什么		被成为快照的原因】



- AOF：**AOF存储的是Redis服务器的顺序指令序列**（只记录对内存修改的指令记录）

  所以对一个空的Redis实例顺序执行所有指令（“重放”），来恢复Redis当前实例的内存数据结构的状态



​		Redis收到客户端的指令之后，先进行参数校验，逻辑处理（执行）。如果没有问题的话，就会将该指令文本		存储到AOF日志中	先执行指令在存盘

​		Redis 运行越久，AOF日志就会越长。如果宕机之后，恢复起来就需要特别长的时间（此时无法对外提供服		务）所以要定期对AOF日志进行瘦身（bgrewriteaof）—开启一个子进程对内存进行遍历，转换成一系列的		Redis操作指令，序列化到一个新的AOF日志文件中。当序列化完毕后再将操作期间的日志追加进去



​		**如果在刷新过程中，机器突然宕机，就会导致Redis备份失败。**

​		利用 fsync(int fd)  来强制将指定文件的内容从内核缓存刷到磁盘

​		通常情况下 Redis 是每个1s左右执行一次fsync操作



- Redis**混合持久化**（rdb【快照】快但是会丢失数据，AOF慢）

  将rdb文件的内容和增量的AOF日志文件（只记录从持久化开始到持久化结束的这段时间发生的增量AOF日志）存在一起

  redis重启的时候，先加载rdb内容再重放增量AOF日志

