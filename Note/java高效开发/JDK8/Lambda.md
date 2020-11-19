# JAVA Lambda表达式





Lambda起源

java8的Lambda

java里，函数不是第一等公民，需要封装到接口里。（面向接口的语言）



## 常见 Lambda表达式

1. 不需要参数，返回值为5 

   () -> 5

2. 接收一个参数（数字类型），返回其2倍的值

3. 接收2个参数（数字），并返回他们的差值

4. 接收2个int型整数，返回他们的和

5. 接受一个string对象，并在控制台打印，不返回任何值（看起来像是返回void）





问题：

什么是闭包？

简单来说就是一个代码块，他的所有类型，变量都被限定在当前包内

闭包解决什么问题

函数式编程使用闭包来实现颗粒细化



> extends 可以用& 来约束多继承

## 参考：

FunctionInterface的用法

https://www.cnblogs.com/bigbaby/p/12116886.html



Lambda 表达式如何演化，简化代码用法

https://www.zhihu.com/question/20125256/answer/324121308

https://www.cnblogs.com/bigbaby/p/12113741.html