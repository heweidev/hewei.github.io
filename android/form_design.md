# Form

总体思路：
    form是垂直方向的线性布局
    表单的每一项是block
    block之间不直接交互
    
block：
    block可以绑定一个数据
    block是复用的单位，所以应该尽可能业务无关
    block通过事件跟form通信
    form通过数据引起block变化

block分类
    自己完成数据的收集
        Eidt， Spinner, RadioButton, Checkbox 等

    需要打开activity或者dialog收集

    
