# Android Binder

> Binder是什么？
binder是Android进程间通信（IPC）和跨进程调用（RPC）的重要工具组件

>IPC是RPC的基础

## IPC
    如何实现RPC?
    /dev/binder  共享内存

    同步or异步？
    同步

    怎么识别不同的进程？
    token

    怎么保持实时性？
    启动独立的线程同步处理

    数据怎么序列化？
    Parcel

## RPC    
    如何发现服务？
    所有服务都注册到ServiceManager中，然后在ServiceManager中查找对应的服务

    如何访问ServiceManager？ServiceManger本身是一种服务，如何访问ServiceManager呢？
    ServiceManager运行在一个守护进程中，该进程随系统启动而启动
    启动后会注册自己提供的服务IServiceManger，并且该服务的id比较独特0
    
    BpXXX和BnXXX
    BpXXX运行在Client中，BnXXX运行在server中

    client如何找到server？
    通过ServiceManager
    因为server自己把提供的接口注册到了ServiceManager


### 多进程
    serviceManager 如何区分不同的进程
    多个进程通过binder跟serviceManager通信，产生以下几个问题：
    1. 所有进程共享相同的共享内存 （设备是相同的， 从而衍生一个问题，同一设备可以映射多个共享内存吗？）
    2. 如果是共享相同内存，多个会话是如何同步的？
    

