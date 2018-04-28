# SwipeRefreshLayout

1. loading的处理  显示、动画

    MaterialProgressDrawable

2. 布局
    mLoadingView mTarget
    padding  
    loading的位置可定义
        public void setProgressViewOffset(boolean scale, int start, int end)

    
3. 输入事件
    滑动过程中offset loading的位置

    canChildScrollUp
    setOnChildScrollUpCallback

    
4. 嵌套滑动

    NestedScrollingParent,
    NestedScrollingChild

    处理了NestedScrollingParent， 响应子view的滑动事件。
    实现了NestedScrollingChild接口，将嵌套滑动事件转发给parent

## Q&A
> 一次互动事件序列是从ACTION_DOWN开始的， 如果后代子孙(比如listview和scrollview)接受该事件。那么事件要想回到SwipeRefreshLayout，必须通过嵌套滑动机制。
如果后代子孙不处理ACTION_DOWN，那么该事件会最终透传到SwipeRefreshLayout

setVisibility能否触发onLayout？


## 嵌套滑动的几个概念

    target  触发嵌套滑动的view
    child   本view中包含target的直接child

    参数中deta数值：
        deta = pre - cur
    
    所以， 如果值是负的，表示滑动是想着坐标增大的方向。
    具体来说，如果滑动轴是垂直方向。dy是负的表示向下滑动， dy是正的表示向上滑动
    如果滑动轴是水平方向。dx是负的表示向右，dx是正表示向左
    
    public interface NestedScrollingParent {
        public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes);

        public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes);

        public void onStopNestedScroll(View target);

        public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                int dxUnconsumed, int dyUnconsumed);

        public void onNestedPreScroll(View target, int dx, int dy, int[] consumed);

        public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed);

        public boolean onNestedPreFling(View target, float velocityX, float velocityY);

        public int getNestedScrollAxes();
    }


