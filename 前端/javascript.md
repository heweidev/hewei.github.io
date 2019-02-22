# Javascript

## 作用域
函数中定义的变量是函数作用域，否则是全局作用域。不同的js中的全局变量会根据引入顺序覆盖

## [prototype0(https://developer.mozilla.org/zh-CN/docs/Learn/JavaScript/Objects/Object_prototypes)
>在javascript中，函数可以有属性。 每个函数都有一个特殊的属性叫作原型（prototype） 

> 注意: 理解对象的原型（可以通过Object.getPrototypeOf(obj)或者已被弃用的__proto__属性获得）与构造函数的prototype属性之间的区别是很重要的。前者是每个实例上都有的属性，后者是构造函数的属性。也就是说，Object.getPrototypeOf(new Foobar())和Foobar.prototype指向着同一个对象。


Object.create() 实际做的是从指定原型对象创建一个新的对象。这里以 person1 为原型对象创建了 person2 对象。
 person2.__proto__ = person1



## Object.defineProperty()



## [this](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Operators/this)
### 全局环境
    window or global

### 函数环境
    非严格模式，继承全局；严格模式undefined

    apply, call, 和bind可以设置函数的环境到某个对象

### 箭头函数
    函数创建的时候的环境
    > 在箭头函数中，this与封闭词法环境的this保持一致。在全局代码中，它将被设置为全局对象：    

### 对象中的方法
    对象自身

### prototype
    调用对象

### 构造函数
    当一个函数用作构造函数时（使用new关键字），它的this被绑定到正在构造的新对象。

### DOM事件处理函数
    事件的触发者

### 内联事件处理函数
    事件的触发者


