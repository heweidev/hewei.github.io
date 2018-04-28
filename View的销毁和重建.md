# View的销毁和重建

1. Activity中的Fragment会自动重建，不需要人工干预。在Activity的onCreate方法中判断savedInstanceState
2. View通过OnSaveXXX 和onRestoreXX 方法进行保存合重建。
    Actvity -> Fragment -> View

3. *保存Parcelable类型的数据没问题，恢复的时候会出现class not found 异常，原因不明*
4. *在onRetoreXXX 方法中抛异常不会引起crash， 从而引起数据恢复异常，需要留意logcat日志* 


# View重建和DataBing的问题
    Fragment重建的过程中，onViewCreated会先执行，执行完后onViewStateRestored 完成view的重建
    在onViewCreated中setData（Binding）后view重建， view应该显示的是原来的值。但是实际上不是。
    这里有个陷阱：
     *setData（Binding）是异步的*
     所以要特别注意，如果要达到同步效果，还需要执行executePendingBindings 方法


# view有id和没有id有什么区别？


     