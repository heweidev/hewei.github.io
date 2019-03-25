# HashMap

## HashMap & HashTable
基本相同，除了没有同步和支持null key
except that it is unsynchronized and permits nulls

## 两个主要参数
- initial capacity  
    the size of buckets when create
- load factor  
    当entry数量大于 初始容量*加载因子  会导致rehash

    默认情况下 initial capacity = 16， load factor = 0.75

## 多线程
    没有同步
    多线程同时访问时，如果有改变结构的行为，必须要用同步锁。
    通常可以使用Collections.synchronizedMap
    在迭代过程中另外的线程改变结构会触发异常，但是程序的行为不能依赖该异常。

## HashSet
    hashset基于hashmap实现。 所有的value都用同一个final值。

