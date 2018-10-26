# classloader和热更新


## [classloader](https://docs.oracle.com/javase/7/docs/api/)

    三个重要的方法：
    loadClassData
    findClass
    defineClass

    class NetworkClassLoader extends ClassLoader {
         String host;
         int port;

         public Class findClass(String name) {
             byte[] b = loadClassData(name);
             return defineClass(name, b, 0, b.length);
         }

         private byte[] loadClassData(String name) {
             // load the class data from the connection
              . . .
         }
     }

## Resin
热更新前
[2018.10.26 10:27:51.782]2018-10-26 10:27:51,782 INFO  com.hewei.test.Word  - classloader: EnvironmentClassLoader[web-app:http://localhost:8080]
[2018.10.26 10:27:51.782]2018-10-26 10:27:51,782 INFO  com.hewei.test.Hello  - a = 196, this = com.hewei.test.Hello@52a15d3c
[2018.10.26 10:28:35.956]2018-10-26 10:28:35,955 INFO  com.hewei.test.Word  - classloader: EnvironmentClassLoader[web-app:http://localhost:8080]
[2018.10.26 10:28:35.956]2018-10-26 10:28:35,956 INFO  com.hewei.test.Hello  - a = 196, this = com.hewei.test.Hello@52a15d3c

热更新后
[2018.10.26 10:30:40.002]2018-10-26 10:30:40,002 INFO  com.hewei.test.Word  - classloader: EnvironmentClassLoader[web-app:http://localhost:8080]
[2018.10.26 10:30:40.002]2018-10-26 10:30:40,002 INFO  com.hewei.test.Hello  - a = 196, this = com.hewei.test.Hello@2843d7ed
[2018.10.26 10:30:41.492]2018-10-26 10:30:41,492 INFO  com.hewei.test.Word  - classloader: EnvironmentClassLoader[web-app:http://localhost:8080]
[2018.10.26 10:30:41.492]2018-10-26 10:30:41,492 INFO  com.hewei.test.Hello  - a = 197, this = com.hewei.test.Hello@2843d7ed

可以看到Word类的**静态变量**值变化了。也就是说加载了新的Hello.class并且实例化
