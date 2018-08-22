import android.content.Intent;
import android.os.Bundle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface TargetView {
    String value();
}

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@interface Key {
    String value();
}

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@interface Code {
    int value();
}

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@interface Flag {
    int value();
}

interface Navigator {
    @TargetView("com.baoneng.wss.CustomerDetail")
    void showCustomerDetail(Object source,
                            @Key("cust_no") String custNo,
                            @Key("cust_data") Bundle data);
}

class NavigatorHelper {
    public <T> T createNavigator(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[] {cls},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Intent intent = new Intent();




                return null;
            }
        });
    };
}


class Test {
    public static void main(String[] args) {

    }
}