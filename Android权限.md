### Android 权限

1. 权限的声明和授予
android targetSdkVersion 23 （6.0）是个分水岭
之前安装的时候列出需要的权限，如果不授予，停止安装。23以后只提示用户危险的权限，比如访问通讯录、短信、打电话等
在需要权限的时候需要动态获取。需要权限时系统弹框提示需要权限，如果拒绝后下次在授权框上会有不再提示选项，勾选后
下次将默认不给权限。并且不再提示。用户可以随时在系统设置中改变对某个权限的授权。所以，每次使用危险权限时都需要授权。


2. 权限分系统权限和自定义权限
自定义权限的级别： normal, signature, and dangerous permissions

## 权限的作用
1. 保护Activity和Service只被授权用户启动
2. 保护BroadcastReceiver只接受授权通知，保护发送的通知只被授权的receiver收到
3. 保护content provider指定uri的读写权限


## 临时授权
    访问邮件附件的场景

    Intent.FLAG_GRANT_READ_URI_PERMISSION 
    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
    android:grantUriPermissions
    <grant-uri-permissions> 


### 两个有用的命令

权限列表
adb shell pm list permissions -s

安装并授予所有权限
adb shell install -g MyApp.apk