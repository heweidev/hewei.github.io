# Fragment

参考文献
- [http://blog.csdn.net/buaaroid/article/details/48265105

## FragmentManager 

## FragmentTransaction
    - add
    - remove
    - attach
    - detach
    - show
    - hide
    - SaveState & Rebuild

## Fragment

## LiftStyle

    - view liftstyle
    - instance liftstyle
    - context style

    值得注意的是：如果你喜欢使用Fragment，一定要清楚这些方法，哪个会销毁视图，哪个会销毁实例，哪个仅仅只是隐藏，这样才能更好的使用它们。
    - a、比如：我在FragmentA中的EditText填了一些数据，当切换到FragmentB时，如果希望会到A还能看到数据，则适合你的就是hide和show；也就是说，希望保留用户操作的面板，你可以使用hide和show，当然了不要使劲在那new实例，进行下非null判断。
    - b、再比如：我不希望保留用户操作，你可以使用remove()，然后add()；或者使用replace()这个和remove,add是相同的效果。
    - c、remove和detach有一点细微的区别，在不考虑回退栈的情况下，remove会销毁整个Fragment实例，而detach则只是销毁其视图结构，实例并不会被销毁。那么二者怎么取舍使用呢？如果你的当前Activity一直存在，那么在不希望保留用户操作的时候，你可以优先使用detach。


    void onViewCreated (View view, 
                Bundle savedInstanceState)
Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view. This gives subclasses a chance to initialize themselves once they know their view hierarchy has been completely created. The fragment's view hierarchy is not however attached to its parent at this point.

    在onCreateView返回后立即调用，但是view树还未挂到父对象上

    几个问题：
        1. setRetainInstance后fragment的生命期会有变化
        2. onViewCreated和onActivityCreated
        3. 在哪里初始化数据

## FragmentPagerAdapter & FragmentStatePagerAdapter & FragmentTabHost

### FragmentPagerAdapter
适合少数页面 
页面不显示的时候不销毁Fragment对象

### FragmentStatePagerAdapter
适合多数页面，页面不使用后，直接销毁对象，只保存状态

### FragmentTabHost
跟上边两个不同。从TabHost继承， 是一个ViewGroup对象
但是里边对Fragment进行管理的代码类似
用的是attach和detach， 不销毁对象


### Fragment通信
1. Fragment 跟Container Activity通信
    找到对象后通过接口通信（OnAttach， findFragmentByTag, findFragmentById）

2. Fragment 跟Container Fragment通信
    找到对象后通过接口通信（getParentFragment， findFragmentByTag, findFragmentById）

3. Fragment 跟其他Fragment通信
    由于不清楚Fragment之间的关系，将无法直接通过直接调用对象接口的方法通信
    中转通信： 通过第三方中转（Activity）
    广播或者事件总线

