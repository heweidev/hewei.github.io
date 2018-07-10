# Activity

 >android:name 属性是唯一必需的属性—它指定 Activity 的类名。应用一旦发布，即不应更改此类名，否则，可能会破坏诸如应用快捷方式等一些功能（请阅读博客文章 Things That Cannot Change [不能更改的内容]）。

 >如果 Activity 处于暂停或停止状态，系统可通过要求其结束（调用其 finish() 方法）或直接终止其进程，将其从内存中删除。（将其结束或终止后）再次打开 Activity 时，必须重建。

- 整个生命期
- 可见生命期
- 前台生命期

 ![Lifestyle](https://developer.android.google.cn/images/activity_lifecycle.png)

 >因此 Activity 创建后，onPause() 必定成为最后调用的方法，然后才能终止进程 — 如果系统在紧急情况下必须恢复内存，则可能不会调用 onStop() 和 onDestroy()。因此，您应该使用 onPause() 向存储设备写入至关重要的持久性数据（例如用户编辑）。不过，您应该对 onPause() 调用期间必须保留的信息有所选择，因为该方法中的任何阻止过程都会妨碍向下一个 Activity 的转变并拖慢用户体验。

 >注：无法保证系统会在销毁您的 Activity 前调用 onSaveInstanceState()，因为存在不需要保存状态的情况（例如用户使用“返回”按钮离开您的 Activity 时，因为用户的行为是在显式关闭 Activity）。 如果系统调用 onSaveInstanceState()，它会在调用 onStop() 之前，并且可能会在调用 onPause() 之前进行调用。


# 协调 Activity
当一个 Activity 启动另一个 Activity 时，它们都会体验到生命周期转变。第一个 Activity 暂停并停止（但如果它在后台仍然可见，则不会停止）时，同时系统会创建另一个 Activity。 如果这些 Activity 共用保存到磁盘或其他地方的数据，必须了解的是，在创建第二个 Activity 前，第一个 Activity 不会完全停止。更确切地说，启动第二个 Activity 的过程与停止第一个 Activity 的过程存在重叠。
生命周期回调的顺序经过明确定义，当两个 Activity 位于同一进程，并且由一个 Activity 启动另一个 Activity 时，其定义尤其明确。 以下是当 Activity A 启动 Activity B 时一系列操作的发生顺序：
- Activity A 的 onPause() 方法执行。
- Activity B 的 onCreate()、onStart() 和 onResume() 方法依次执行。（Activity B 现在具有用户焦点。）
- 然后，如果 Activity A 在屏幕上不再可见，则其 onStop() 方法执行。

您可以利用这种可预测的生命周期回调顺序管理从一个 Activity 到另一个 Activity 的信息转变。 例如，如果您必须在第一个 Activity 停止时向数据库写入数据，以便下一个 Activity 能够读取该数据，则应在 onPause() 而不是 onStop() 执行期间向数据库写入数据。


### Context

- SharedPreference
- 目录管理 getFileDir
- 启动四大组件
- 权限管理
- 资源管理

> 一个程序中context的个数 = Activity数 + Service数 + 1

```
public class ContextWrapper extends Context
```

可以包装一个Context, 从而改变其某些配置


### [Intent匹配](https://developer.android.google.cn/guide/components/intents-filters.html)

1. Action
    如果IntentFilter声明有Action，则Intent必须匹配某个Action   （同有同无原则）

2. Category
    若要使 Intent 通过类别测试，则 Intent 中的每个类别均必须与过滤器中的类别匹配。反之则未必然，Intent 过滤器声明的类别可以超出 Intent 中指定的数量，且 Intent 仍会通过测试。 因此，不含类别的 Intent 应当始终会通过此测试，无论过滤器中声明何种类别均是如此。

    >注：Android 会自动将 CATEGORY_DEFAULT 类别应用于传递给 startActivity() 和 startActivityForResult() 的所有隐式 Intent。因此，如需 Activity 接收隐式 Intent，则必须将 "android.intent.category.DEFAULT" 的类别包括在其 Intent 过滤器中（如上文的 <intent-filter> 示例所示）。

3. Data 

    每个 <data> 元素均可指定 URI 结构和数据类型（MIME 媒体类型）。 URI 的每个部分均包含单独的 scheme、host、port 和 path 属性：

        <scheme>://<host>:<port>/<path>

    例如：

    content://com.example.project:200/folder/subfolder/etc

    在此 URI 中，架构是 content，主机是　com.example.project，端口是 200，路径是 folder/subfolder/etc。

    在 <data> 元素中，上述每个属性均为可选，但存在线性依赖关系：

    如果未指定架构，则会忽略主机。
    如果未指定主机，则会忽略端口。
    如果未指定架构和主机，则会忽略路径。
    将 Intent 中的 URI 与过滤器中的 URI 规范进行比较时，它仅与过滤器中包含的部分 URI 进行比较。 例如：

    - 如果过滤器仅指定架构，则具有该架构的所有 URI 均与该过滤器匹配。
    - 如果过滤器指定架构和权限，但未指定路径，则具有相同架构和权限的所有 URI 都会通过过滤器，无论其路径如何均是如此。
    - 如果过滤器指定架构、权限和路径，则仅具有相同架构、权限和路径的 URI 才会通过过滤器。
    - 注：路径规范可以包含星号通配符 (*)，因此仅需部分匹配路径名即可。

    数据测试会将 Intent 中的 URI 和 MIME 类型与过滤器中指定的 URI 和 MIME 类型进行比较。 规则如下：

    - 仅当过滤器未指定任何 URI 或 MIME 类型时，不含 URI 和 MIME 类型的 Intent 才会通过测试。
    - 对于包含 URI 但不含 MIME 类型（既未显式声明，也无法通过 URI 推断得出）的 Intent，仅当其 URI 与过滤器的 URI 格式匹配、且过滤器同样未指定 MIME 类型时，才会通过测试。
    - 仅当过滤器列出相同的 MIME 类型且未指定 URI 格式时，包含 MIME 类型、但不含 URI 的 Intent 才会通过测试。
    - 仅当 MIME 类型与过滤器中列出的类型匹配时，同时包含 URI 类型和 MIME 类型（通过显式声明，或可以通过 URI 推断得出）的 Intent 才会通过测试的 MIME 类型部分。 如果 Intent 的 URI 与过滤器中的 URI 匹配，或者如果 Intent 具有 content: 或 file: URI 且过滤器未指定 URI，则 Intent 会通过测试的 URI 部分。 换言之，如果过滤器只是列出 MIME 类型，则假定组件支持 content: 和 file: 数据。
