# Overview of memory management

https://developer.android.google.cn/topic/performance/memory-overview

## gc
> Android’s memory heap is a generational one, meaning that there are different buckets of allocations that it tracks, based on the expected life and size of an object being allocated. For example, recently allocated objects belong in the Young generation. When an object stays active long enough, it can be promoted to an older generation, followed by a permanent generation.

堆按照次代来管理内存，加快gc速度。新产生的内存被回收的可能性比较大
Young generation
older generation
permanent generation

## 共享内存

1. 每个App从Zygote fork出来，共享了大部分代码和资源
2. Most static data is mmapped into a process. （**odex， resouce， so**）， 可以在必要时候page out
3. In many places, Android shares the same dynamic RAM across processes using explicitly allocated shared memory regions (either with ashmem or gralloc). For example, **window surfaces** use shared memory between the app and screen compositor, and **cursor buffers** use shared memory between the content provider and client.

## Switch apps
App内存cache， 后台进程按照LRU cache


# Manage your app's memory

## Memory Profiler 
- See how your app allocates memory over time. The Memory Profiler shows a realtime graph of how much memory your app is using, the number of allocated Java objects, and when garbage collection occurs. （实时查看内存占用）
- Initiate garbage collection events and take a snapshot of the Java heap while your app runs. （内存快照）
- Record your app's memory allocations and then inspect all allocated objects, view the stack trace for each allocation, and jump to the corresponding code in the Android Studio editor. （获得对象的关系和调用栈）

## Use more memory-efficient code constructs (高效使用内存)
1. 及时地停止Service  （JobScheduler && IntentService）
2. 优化数据结构  （SparseArray替代HashMap）
3. 优化抽象   （java的方法默认是虚方法，尽可能的用静态方法）
4. Avoid memory churn减少内存闯动
5. 减少无用资源
6. 减少Apk大小
7. 优化使用第三方库   （无用资源，无用代码）

## Graphics内存
[Graphics内存](https://blog.csdn.net/msf568834002/article/details/78881341)
- EGL memtrack
You will see this column when display driver’s memtrack module is enabled
Before Lollipop5.1, this column is named “Graphics”.
EGL memtrack memory is the summary of all surface buffers(the surface buffer increases to triple buffer after Android 4.1) and the size of the Atlas buffer. 
However, Atlas buffer is actually a shared memory and shouldn’t be accounted into each UI process’ memory usage to overcount the memory usage. 
Both surface buffer and Atlas buffer’s memory quota is reserved in project’s memory estimation, thus the memory usage of these buffers should be separately 
accounted from process’ memory usage. So when you measure process’ memory usage, you can ignore this column.

- GL memtrack

You will see this column when display driver’s memtrack module is enabled
Before Lollipop5.1, this column is named “GL”.
HW acceleration memory is partially counted in process PSS. For example, for QCT platform the HW acceleration memory is partially counted in the PSS of /dev/kgsl-3d0 
as we mentioned in the “Gfx dev” section. GL memtrack memory usage calculates the unaccounted /dev/kgsl-3d0 memory regions which PSS value equals 0.
Please be noticed that the summation of GL memtrack and Gfx dev doesn’t reflect the complete HW acceleration memory since the full HW acceleration memory usage 
should be counted with the VSS of /dev/kgsl-3d0. So the “TOTAL” value of dumpsys meminfo is smaller than actual physical memory usage.


## 实践

一个近似空的app，只有一个Acitvity

dumpsys meminfo
    前台
           Java Heap:     2020
         Native Heap:     5408
                Code:     3384
               Stack:      256
            Graphics:    14952
       Private Other:      920
              System:     3831

               TOTAL:    30771       TOTAL SWAP PSS:       57
    后台
           Java Heap:     2572
         Native Heap:     5480
                Code:     3476
               Stack:      260
            Graphics:     3740
       Private Other:      652
              System:     4093

               TOTAL:    20273       TOTAL SWAP PSS:       55

    从前台到后台， 主要的变化是Graphics减少了近10M

WSS
    前台
           Java Heap:    25256          + 23M
         Native Heap:     8484          + 3M
                Code:    30100          + 30M
               Stack:      704          + 0.5M
            Graphics:    34960          + 20M
       Private Other:     3812          + 3M
              System:     8147          + 5M

               TOTAL:   111463       TOTAL SWAP PSS:      120

    后台
           Java Heap:    20020          + 17M
         Native Heap:     8212          + 3M
                Code:    30256          + 27M
               Stack:      688
            Graphics:     6828          + 3M
       Private Other:     4060
              System:     8267

               TOTAL:    78331       TOTAL SWAP PSS:      120

    从前台到后台 Java heap少了近5M， Graphics减少近30M

    今日头条：
                           Pss(KB)
                        ------
           Java Heap:    29092
         Native Heap:    26132
                Code:    34124
               Stack:     2224
            Graphics:    21720
       Private Other:    16072
              System:    16873

               TOTAL:   146237       TOTAL SWAP PSS:       69

WSS 跟 空App相比， 内存的主要增长点是：
   Java heap    | 更多的Java对象
   Code         | 更大的安装包
   Graphics     | 更多的图片资源


所以内存优化可以从以下几点着手
    尽可能减少内存分配
    控制安装包大小
    优化图片等资源的使用
    用内存池防止内存碎片
    复用已有内存，防止内存闯动
    
