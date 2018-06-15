# RecylerView

1. Adapter
    position (adapter position & layout position)
    index
    id
    notifyDataSetChanged
        更精确的更新粒度
        item changes & structural changes

        item insert, move, removed
        range insert, removed

        partical & full bind 
        payload
        
2. LayoutManger



## RecylerView & ListView
列表页展示界面，需要支持动画，或者频繁更新，局部刷新，建议使用RecyclerView，更加强大完善，易扩展；
其它情况(如微信卡包列表页)两者都OK，但ListView在使用上会更加方便，快捷。

ListView相比RecyclerView，有一些优点：
    - addHeaderView(), addFooterView()添加头视图和尾视图。
    - 通过”android:divider”设置自定义分割线。
    - setOnItemClickListener()和setOnItemLongClickListener()设置点击事件和长按事件。

这些功能在RecyclerView中都没有直接的接口，要自己实现（虽然实现起来很简单），因此如果只是实现简单的显示功能，ListView无疑更简单。

RecyclerView相比ListView，有一些明显的优点：
    - 默认已经实现了View的复用，不需要类似if(convertView == null)的实现，而且回收机制更加完善。
    - 默认支持局部刷新。
    - 容易实现添加item、删除item的动画效果。
    - 容易实现拖拽、侧滑删除等功能。
    - RecyclerView是一个插件式的实现，对各个功能进行解耦，从而扩展性比较好。

参考文献：
1. https://mp.weixin.qq.com/s?__biz=MzA3NTYzODYzMg==&mid=2653578065&idx=2&sn=25e64a8bb7b5934cf0ce2e49549a80d6&chksm=84b3b156b3c43840061c28869671da915a25cf3be54891f040a3532e1bb17f9d32e244b79e3f&scene=21#wechat_redirect

2. http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0116/7039.html