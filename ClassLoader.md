## ClassLoader

# 双亲委托
    先委托给父类加载，父类加载失败再交由委托者加载

    优点：
    1. 避免重复加载
    2. 安全因素（防止系统class被替换）

## 检查是否同一个类
    类名相同，且classLoader相同


## Android的classloader

- PathClassLoader
>Provides a simple ClassLoader implementation that operates on a list of files and directories in the local file system, but does not attempt to load classes from the network. Android uses this class for its system class loader and for its application class loader(s).

- DexClassLoader    
>A class loader that loads classes from .jar and .apk files containing a classes.dex entry. This can be used to execute code not installed as part of an application.

DexClassLoader可以指定自己的optimizedDirectory，所以它可以加载外部的dex，因为这个dex会被复制到内部路径的optimizedDirectory；而PathClassLoader没有optimizedDirectory，所以它只能加载内部的dex，这些大都是存在系统中已经安装过的apk里面的。

通过以上的分析，我们可以得出二者功能上的区别
DexClassLoader：能够加载未安装的jar/apk/dex 
PathClassLoader：只能加载系统中已经安装过的apk
