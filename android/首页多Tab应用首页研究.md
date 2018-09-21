## 多Tab架构
1. 今日头条
    TabHost， TabHost的默认行为是attach和detach，切换fragment会引起view的销毁和重建，
    但是fragment实例不销毁
    经过dumpsys，发现tabhost应该是自定义的。切换tab采用的也是隐藏fragment的方式

2. 腾讯新闻
    每个页面是一个Fragment，Fragment切换采用的是show和hide的方式

    备注：
    腾讯新闻的闪屏页也是在主Activity中实现的


# 工具
UIAutomatorView  
dumpsys activity top    
