# 事件路由

三个重要方法：
```
public boolean dispatchTouchEvent(MotionEvent event) 
public boolean onTouchEvent(MotionEvent event)
public boolean onInterceptTouchEvent(MotionEvent ev)
```

两个嵌套FrameLayout
1. 点击内部的view，默认情况下事件序列如下

03-23 11:00:14.798 26679-26679/com.hewei.nestedscrollviewtest D/Container: onInterceptTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=568.0, y[0]=777.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2290887859, downTime=2290887859, deviceId=2, source=0x1002 }
03-23 11:00:14.798 26679-26679/com.hewei.nestedscrollviewtest D/Child: onInterceptTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=178.0, y[0]=207.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2290887859, downTime=2290887859, deviceId=2, source=0x1002 }
03-23 11:00:14.799 26679-26679/com.hewei.nestedscrollviewtest D/Child: onTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=178.0, y[0]=207.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2290887859, downTime=2290887859, deviceId=2, source=0x1002 }
03-23 11:00:14.799 26679-26679/com.hewei.nestedscrollviewtest D/Container: onTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=568.0, y[0]=777.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2290887859, downTime=2290887859, deviceId=2, source=0x1002 }
03-23 11:00:14.799 26679-26679/com.hewei.nestedscrollviewtest D/Activity: onTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=568.0, y[0]=967.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2290887859, downTime=2290887859, deviceId=2, source=0x1002 }
03-23 11:00:14.831 26679-26679/com.hewei.nestedscrollviewtest D/Activity: onTouchEvent ret = false, event = MotionEvent { action=ACTION_UP, actionButton=0, id[0]=0, x[0]=568.0, y[0]=967.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2290887905, downTime=2290887859, deviceId=2, source=0x1002 }

事件先分给container， container不拦截，交给child， child也不拦截，交给目标view， 目标不处理ACTION_DOWN, 回溯到parent的ACTION_DOWN， parent不处理，转给activity


2. 点击外部view
03-23 11:04:10.960 26679-26679/com.hewei.nestedscrollviewtest D/Container: onInterceptTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=827.0, y[0]=369.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291124021, downTime=2291124021, deviceId=2, source=0x1002 }
03-23 11:04:10.961 26679-26679/com.hewei.nestedscrollviewtest D/Container: onTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=827.0, y[0]=369.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291124021, downTime=2291124021, deviceId=2, source=0x1002 }
03-23 11:04:10.961 26679-26679/com.hewei.nestedscrollviewtest D/Activity: onTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=827.0, y[0]=559.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291124021, downTime=2291124021, deviceId=2, source=0x1002 }
03-23 11:04:11.023 26679-26679/com.hewei.nestedscrollviewtest D/Activity: onTouchEvent ret = false, event = MotionEvent { action=ACTION_MOVE, actionButton=0, id[0]=0, x[0]=829.0, y[0]=558.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291124089, downTime=2291124021, deviceId=2, source=0x1002 }
03-23 11:04:11.023 26679-26679/com.hewei.nestedscrollviewtest D/Activity: onTouchEvent ret = false, event = MotionEvent { action=ACTION_UP, actionButton=0, id[0]=0, x[0]=829.0, y[0]=558.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291124097, downTime=2291124021, deviceId=2, source=0x1002 }

同1， 只是路由的层级少啦


3. Child的ACTION_DOWN返回true

03-23 11:13:29.148 26679-26679/com.hewei.nestedscrollviewtest D/Container: onInterceptTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=552.0, y[0]=814.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291682203, downTime=2291682203, deviceId=2, source=0x1002 }
03-23 11:13:29.149 26679-26679/com.hewei.nestedscrollviewtest D/Child: onInterceptTouchEvent ret = false, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=162.0, y[0]=244.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291682203, downTime=2291682203, deviceId=2, source=0x1002 }
03-23 11:13:29.149 26679-26679/com.hewei.nestedscrollviewtest D/Child: onTouchEvent ret = true, event = MotionEvent { action=ACTION_DOWN, actionButton=0, id[0]=0, x[0]=162.0, y[0]=244.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291682203, downTime=2291682203, deviceId=2, source=0x1002 }
03-23 11:13:29.180 26679-26679/com.hewei.nestedscrollviewtest D/Container: onInterceptTouchEvent ret = false, event = MotionEvent { action=ACTION_UP, actionButton=0, id[0]=0, x[0]=552.0, y[0]=814.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291682253, downTime=2291682203, deviceId=2, source=0x1002 }
03-23 11:13:29.180 26679-26679/com.hewei.nestedscrollviewtest D/Child: onTouchEvent ret = false, event = MotionEvent { action=ACTION_UP, actionButton=0, id[0]=0, x[0]=162.0, y[0]=244.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291682253, downTime=2291682203, deviceId=2, source=0x1002 }
03-23 11:13:29.180 26679-26679/com.hewei.nestedscrollviewtest D/Activity: onTouchEvent ret = false, event = MotionEvent { action=ACTION_UP, actionButton=0, id[0]=0, x[0]=552.0, y[0]=1004.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=2291682253, downTime=2291682203, deviceId=2, source=0x1002 }

一旦ACTION_DOWN返回true，后续的事件首先通过parent的onInterceptTouchEvent，然后到达child的onTouchEvent， 如果onTouchEvent不返回true，事件会回溯到parent的onTouchEvent，直到Activity


### 嵌套滑动
    总体思路： child接收到滑动时间后，发起嵌套滑动， 然后递归parent直到找到一个接收嵌套滑动事件的parent，
    然后把滑动距离传给目标祖先， 目标祖先决定消费多少滑动距离， 传入的数组参数，child获取到剩余的滑动距离，
    然后，自身消费一定的距离后，再次将剩余的滑动距离传递给目标祖先。最后child结束嵌套滑动，并通过回调通知目标祖先。


### CoordinatorLayout
    

跟AppBarLayout配合使用
里边需要包含一个有ScrollingViewBehavior的子view

 * AppBarLayout also requires a separate scrolling sibling in order to know when to scroll.
 * The binding is done through the {@link ScrollingViewBehavior} behavior class, meaning that you
 * should set your scrolling view's behavior to be an instance of {@link ScrollingViewBehavior}.
 * A string resource containing the full class name is available.

 > NestedScrollView 的大小是屏幕大小，直接放在header下边。滚动的时候 NestedScrollView 跟着滚动调整offset，整个过程header和NestedScrollView的大小都没有调整！！！利用整个技巧避免了重复的Layout，而Layout过程本身是很耗性能的过程。


### OnMeasure, OnLayout, OnDraw

setLayoutParams -> requestLayout -> onMeasure -> onLayout
requestLayout从上往下执行的
