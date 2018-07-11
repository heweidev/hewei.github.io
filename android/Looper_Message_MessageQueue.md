# Looper & Message & MessageQueue & Handler

## 概述
    1. Looper 存在用一个线程中
    2. Looper不断从MessageQueue中取Message，然后交由hander处理

    > A Handler allows you to send and process {@link Message} and Runnable
    objects associated with a thread's {@link MessageQueue}. 

    > Message Defines a message containing a description and arbitrary data object that can be
    sent to a {@link Handler}. 


## MessageQueue
    链表结构, 按时间顺序排列
    boolean enqueueMessage(Message msg, long when) {
        Message p = mMessages;
        boolean needWake;
        if (p == null || when == 0 || when < p.when) {
            // New head, wake up the event queue if blocked.
            msg.next = p;
            mMessages = msg;
            needWake = mBlocked;
        } else {
            // Inserted within the middle of the queue.  Usually we don't have to wake
            // up the event queue unless there is a barrier at the head of the queue
            // and the message is the earliest asynchronous message in the queue.
            needWake = mBlocked && p.target == null && msg.isAsynchronous();
            Message prev;
            for (;;) {
                prev = p;
                p = p.next;
                if (p == null || when < p.when) {
                    break;
                }
                if (needWake && p.isAsynchronous()) {
                    needWake = false;
                }
            }
            msg.next = p; // invariant: p == prev.next
            prev.next = msg;
        }
    }

    1. 如果Stalled by a barrier， 取第一个异步消息.否则取第一个消息
    2. 如果消息到达触发时间，直接处理。否则用nativePollOnce阻塞nextPollTimeoutMillis。如果调用了idleHandler， 阻塞时间为0
        
    nativePollOnce(ptr, nextPollTimeoutMillis);

        synchronized (this) {
            // Try to retrieve the next message.  Return if found.
            final long now = SystemClock.uptimeMillis();
            Message prevMsg = null;
            Message msg = mMessages;
            if (msg != null && msg.target == null) {
                // Stalled by a barrier.  Find the next asynchronous message in the queue.
                do {
                    prevMsg = msg;
                    msg = msg.next;
                } while (msg != null && !msg.isAsynchronous());
            }
            if (msg != null) {
                if (now < msg.when) {
                    // Next message is not ready.  Set a timeout to wake up when it is ready.
                    nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                } else {
                    // Got a message.
                    mBlocked = false;
                    if (prevMsg != null) {
                        prevMsg.next = msg.next;
                    } else {
                        mMessages = msg.next;
                    }
                    msg.next = null;
                    if (DEBUG) Log.v(TAG, "Returning message: " + msg);
                    msg.markInUse();
                    return msg;
                }
            } else {
                // No more messages.
                nextPollTimeoutMillis = -1;
            }
        }

## 异步消息

> Certain operations, such as view invalidation, may introduce synchronization barriers into the Looper's message queue to prevent subsequent messages from being delivered until some condition is met. In the case of view invalidation, messages which are posted after a call to View.invalidate() are suspended by means of a synchronization barrier until the next frame is ready to be drawn. The synchronization barrier ensures that the invalidation request is completely handled before resuming.

> Asynchronous messages are exempt from synchronization barriers. They typically represent interrupts, input events, and other signals that must be handled independently even while other work has been suspended.

> Note that asynchronous messages may be delivered out of order with respect to synchronous messages although they are always delivered in order among themselves. If the relative order of these messages matters then they probably should not be asynchronous in the first place. Use with caution.