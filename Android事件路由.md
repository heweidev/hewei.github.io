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
