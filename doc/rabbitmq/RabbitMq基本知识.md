## RabbitMQ基本知识

### RabbitMQ几个基本名词：

- 消息：有效载荷（payload）+标签（label）
- 有效载荷：就是想要传输的数据
- 标签：可以用来表述这条消息（一个交换器的名称和可选的主题标记）【不会传输给消费者】

- 消费者：消息队列中消费消息
- 生产者：消息队列中生成消息
- 代理：代理服务器（即MQ） 起到一个路由作用
- 交换器：【消息投递到队列前，通过绑定在交换器上确定的规则（路由键）后将他们投递到对应的队列中】
- 队列：消息最终到达的地方，并且给消费者接收的地方
- 绑定：规则（路由键）绑定到交换机上
- 虚拟主机（vhost）：一个消息队列可以创建多个虚拟主机（虚拟主机之间相互独立，类似于很多小的MQ 各自有各自的 交换器，路由键和队列）

### RabbitMQ是怎么工作的：

应用程序和RabbitMQ建立一个TCP连接，在连接建立并且已经通过认证的情况下，应用程序便会创建一个AMQP信道【每个信道都有一个独立的ID 由AMQP库记录】（信道和建立在”真实的“的TCP连接中的虚拟连接）。每个AMQP指令（发送消息，订阅队列或接收消息）都是通过信道发送出去的。

- **为什么每次是创建信道而不是创建一个TCP连接：**如果是建立TCP连接，那么应用程序每个线程都需要建立连接，性能消耗便会十分大
- **队列中有多个消费者的情况下，队列中的消息是怎么发送给消费者的：**队列收到的消息将以循环的方式发送给消费者
- **交换器的几种类型：**direct【空白字符串交换器，路由键匹配】，fanout【扇区-例子买东西会扣钱和加积分，扣钱和加积分两个队列便可以同时绑定到一个fanout交换器上】，topic【订阅】，headers
- **持久化消息**
- **发送方确认模式**
- **死信队列：** 未看

😂暂时就这些-2019/6/13😂