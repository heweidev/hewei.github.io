# Google IO 官方App研究

几个重要的点
Kotlin
RecylerView
ConstrainsLayout
DataBinding
自定义控件
LiveData


## 动效：
Lottie库
    动画文件体积小，使用简单。
    能够以静态或动态的方式加载
    动画形式灵活多样


折叠效果：
    Transition动画
        TransitionManager.beginDelayedTransition(root.parent as ViewGroup, toggle)

## 基于ItemDecoration对RecylerView进行装饰，实现特殊效果。
    该工程中的标题悬挂效果
    流程列表的导向线

