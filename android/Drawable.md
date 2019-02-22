# Drawable
    - setBounds 位置和大小   不设置bounds默认是0， 绘制的时候将看不到
    - getPadding
    - setState
    - setLevel
    - setCallback ?

    多个Drawable可以指向同一个ConstantState，Drawable本身不占用资源。
    Drawable 有cache通道，cache存储的是ConstantState的弱引用。不影像资源的生命期
    
    

    