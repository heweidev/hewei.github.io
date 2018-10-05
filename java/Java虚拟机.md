# Java虚拟机

## 特点
    stack base   (Android的虚拟机是基于寄存器的)
    定义了数据的大小
    网络字节序 (Bit endian)
    垃圾收集
    Symbolic Reference


## classloader
    层次结构
    代理模式 （先从父classloader找，找不到后调用当前classloader）
    可见性 父classloader不能看到children的class
    不能卸载class，但是可以remove classloader

    bootloader 
    extention
    system
    user-define

    class的加载过程

    Verify -> Prepare -> Resolve -> Init

    

