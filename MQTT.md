# 消息推送
> 一句话描述推送：server和client建立一个TCP长连接，然后利用长连接进行通信

连接的可靠性 （断线重连）
Android后台进程保活困难，所以依赖后台进程进行消息通知，实时性很难保证。
但是前台进程不受此限制

- 第三方推送（友盟、极光、个推）
    简单、开发门槛低、关注业务
    信息会暴露跟第三方平台

- 自搭推送平台
    复杂性略高
    紧密契合业务场景，信息保密

# 协议介绍

> MQTT协议是为大量计算能力有限，且工作在低带宽、不可靠的网络的远程传感器和控制设备通讯而设计的*轻量级*协议，它具有以下主要的几项特性：
- 1、使用*发布/订阅*消息模式，提供一对多的消息发布
- 2、使用 TCP/IP 提供网络连接；
- 3、有三种消息发布服务质量（QoS）：
    - “至多一次”，消息发布完全依赖底层 TCP/IP 网络。会发生消息丢失或重复。这一级别可用于如下情况，环境传感器数据，丢失一次读记录无所谓，因为不久后还会有第二次发送。
    - “至少一次”，确保消息到达，但消息重复可能会发生。
    - “只有一次”，确保消息到达一次。这一级别可用于如下情况，在计费系统中，消息重复或丢失会导致不正确的结果。
- 4、小型传输，开销很小（固定长度的头部是 2 字节），协议交换最小化，以降低网络流量；
- 5、使用 Last Will 和 Testament 特性通知有关各方客户端异常中断的机制；[2] 


## 代理人模式（broker）
- 所有的client都只和代理人通信
- 通信以消息(message)为单位
- 消息都有一个主题（topic)
- 某个client发布了一个消息，所有订阅此topic的client都将收到此消息
- client可以订阅多个topic

![订阅](https://eclipse.org/community/eclipse_newsletter/2014/february/images/febarticle2.1.png)

![发布](https://eclipse.org/community/eclipse_newsletter/2014/february/images/febarticle2.2.png)


# 消息类型

CONNECT：客户端连接到MQTT代理
CONNACK：连接确认
PUBLISH：新发布消息
PUBACK：新发布消息确认，是QoS 1给PUBLISH消息的回复
PUBREC：QoS 2消息流的第一部分，表示消息发布已记录
PUBREL：QoS 2消息流的第二部分，表示消息发布已释放
PUBCOMP：QoS 2消息流的第三部分，表示消息发布完成
SUBSCRIBE：客户端订阅某个主题
SUBACK：对于SUBSCRIBE消息的确认
UNSUBSCRIBE：客户端终止订阅的消息
UNSUBACK：对于UNSUBSCRIBE消息的确认
PINGREQ：心跳
PINGRESP：确认心跳
DISCONNECT：客户端终止连接前优雅地通知MQTT代理

# 演示

- 订阅
mosquitto_sub -d -t 'floor-5/temperature'
- 发布
mosquitto_pub -d -t 'floor-5/temperature' -m '15'

# 聊天室

- 用户身份
- 信息审查和过滤
    每个房间对应一个topic
    房间中所有用户订阅该topic，只有读权限
    房间中所有用户通过专门的topic跟stoker通信，只有写权限
- 安全
    - MQTT brokers may require username and password authentication from clients to connect. 
    用户名密码认证
    - To ensure privacy, the TCP connection may be encrypted with SSL/TLS.
    数据流加密