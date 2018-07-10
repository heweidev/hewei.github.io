# Android架构组件

核心思想：
1. 不要把数据保存在Activity和Fragment中
2. 用model驱动UI

>The second important principle is that you should drive your UI from a model, preferably a persistent model. Persistence is ideal for two reasons: your users won't lose data if the OS destroys your app to free up resources and your app will continue to work even when a network connection is flaky or not connected. Models are components that are responsible for handling the data for the app. They are independent from the Views and app components in your app, hence they are isolated from the lifecycle issues of those components. Keeping UI code simple and free of app logic makes it easier to manage. Basing your app on model classes with well-defined responsibility of managing the data will make them testable and your app consistent.

## ViewModel

ViewModelProvider  创建ViewModel
ViewModelStore  保存ViewModel

ViewModelStore的宿主可以是Fragment或者Activity
如果Fragment或者Activity是Store Owner直接返回相关对象，否则创建空的HolderFragment（这个技巧可以借鉴）add到FragmentManager中。保证HolderFragment和宿主fragment生命期一致。Glide中也是用这个方法实现生命周期管理。


