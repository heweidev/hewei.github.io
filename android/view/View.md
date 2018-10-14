# View



## ViewGroup
1. focus
    焦点问题，谁将获得焦点，自己或者子孙
    getDescendantFocusability
    setDescendantFocusability  

    可选项：
        FOCUS_BEFORE_DESCENDANTS
        FOCUS_AFTER_DESCENDANTS
        FOCUS_BLOCK_DESCENDANTS

    public void requestChildFocus(View child, View focused)

2. contextMenu
    showContextMenuForChild

3. actionMode

4. startActivity
    view.startActivityForResult()
    onActivityResult()
    dispatchActivityResult()

5. drag & drop

6. systemUI & windowSystemUi changed

7. hover & key & short key & trackball event dispatch

8. **dispatchTouchEvent**

    View.dispatchTouchEvent

            ListenerInfo li = mListenerInfo;
            if (li != null && li.mOnTouchListener != null
                    && (mViewFlags & ENABLED_MASK) == ENABLED
                    && li.mOnTouchListener.onTouch(this, event)) {
                result = true;
            }

            if (!result && onTouchEvent(event)) {
                result = true;
            }

            先处理注册事件，然后自己的回调

    ViewGroup.dispatchTouchEvent
        相关回调
        
            > Implement this method to intercept all touch screen motion events. This allows you to **watch** events as they are dispatched to your children, and **take ownership** of the current gesture **at any point**.

          onIntercepTouchEvent
                final boolean intercepted;
                if (actionMasked == MotionEvent.ACTION_DOWN
                        || mFirstTouchTarget != null) {
                    final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
                    if (!disallowIntercept) {
                        intercepted = onInterceptTouchEvent(ev);
                        ev.setAction(action); // restore action in case it was changed
                    } else {
                        intercepted = false;
                    }
                } else {
                    // There are no touch targets and this action is not an initial down
                    // so this view group continues to intercept touches.
                    intercepted = true;
                }


          onTouchEvent
              ViewGroup没有override该方法


## ScrollView 事件调度





## 杂项

