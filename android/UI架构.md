# UI架构

## MVC
model和view的分离，model变化引起view变化，单项传递

Java后端的MVC有所区别

## MVP

用接口对Model和View进行隔离，从而方便各个部分单独测试

避免大的View接口
生命周期问题

## MVVM
强调数据的绑定  数据驱动UI
ViewModel对数据进行整合
必要的时候可以引入Preseneter处理相关逻辑


## 参考
https://tech.meituan.com/2016/11/11/android-mvvm.html
http://zjutkz.net/2016/04/13/%E9%80%89%E6%8B%A9%E6%81%90%E6%83%A7%E7%97%87%E7%9A%84%E7%A6%8F%E9%9F%B3%EF%BC%81%E6%95%99%E4%BD%A0%E8%AE%A4%E6%B8%85MVC%EF%BC%8CMVP%E5%92%8CMVVM/