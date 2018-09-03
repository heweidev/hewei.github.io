# Java Object的几个方法

hashCode
equals
    // 如果equals返回true， hashCode也应该返回true；反之如果equals返回false， hashCode可以相同
    // equals满足 自反性， 对称性、传递性
    // hashCode在一次启动过程中要保持持续性，不能变化

wait
notify
notifyAll

    // 需要配合synchronized使用，持有object的监视器（monitor）
     synchronized (obj) {
         while (<condition does not hold>)
             obj.wait();
         ... // Perform action appropriate to condition
     }
