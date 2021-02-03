## 一、JVM是什么，能解决什么问题?

 JVM是Java Virtual Machine（Java虚拟机）的缩写，JVM是一种用于计算机的**规范**，它是一个虚构出来的计算机，是通过在实际计算机上仿真模拟各种计算机功能来实现的。

JVM帮助编程语言屏蔽与具体平台（OS）相关的信息，使得由各中编程语言编写的编译程序只需在JVM上生成运行的字节码，就可以在多种平台上运行（WORA, Write Once Run Anywhere）。

## 二、JVM体系结构

 JVM主要分为类加载器子系统、运行时数据区、执行引擎、本地方法接口以及垃圾收集模块。

<img src=".\img\structure.png" alt="structure" style="zoom: 80%; margin-left: 0px;" />

### 3.1 类加载器（ClassLoader）

#### 3.1.1 类加载器作用

负责加载class文件，class文件**在文件开头有特定的文件标识**（比如说编译的版本信息），将class文件的字节码内容加载到内存中，并将这些内容转换成方法区中的运行时数据结构，classLoader只负责class文件的加载，至于它是否可以运行，则有Execution Engine（执行引擎）决定。

#### 3.1.2 字节码

 jvm不能执行java文件，我们编写的java文件需要经过javac编译器编译成字节码（.class）,然后再由JVM执行。`
`java bytecode 由单字节（byte）的指令组成，理论上最多支持256个操作码（opcode）。但是实际上java只使用了200左右的操作码，还有一些操作码则保留给调试操作。ClassLoader将.class文件加载并初始化后，得到当前类的**Class模板**，并将其存放在**方法区**

- 使用字节码有什么优势？
  java语言通过字节码的方式，在一定程度上解决了传统解释语言执行效率低的问题，同时又保留了解释型语言可移植的特点。所以java程序运行时比较高效，而且由于字节码并不专对一种特定的机器，因此，java程序无须重新编译便可在多种不同的计算机上运行。

> 解释型语言：在运行的时候将程序翻译成**机器语言**。解释型语言的程序不需要在运行前编译（java语言是需要提前编译成字节码的），在程序运行的时候才进行翻译，专门的解释器负责在每个语句执行的时候解释程序代码。这样解释型语言每执行一次就要翻译一次，效率比较低。（例如：python、php）

#### 3.1.3 ClassLoader种类

- JVM自带的加载器（系统类加载器，加载当前应用的classpath的所有类）
  - 启动类加载器（也叫根加载器Bootstrap）:C++编写，存储位置：$JAVAHOME/jre/lib/rt.jar
  - 扩展类加载器（Extension）:Java编写，javax开头的，都是扩展包，存储位置：$JAVAHOME/jre/lib/ext/*.jar
  - 应用程序类加载器（AppClassLoader）,平时程序中自定义的类，new出来的。
- 用户自定义加载器
  - java.lang.ClassLoader的子类

Java的类加载机制，启动类加载器 --> 拓展类加载器 --> 应用程序类加载器

#### 3.1.4 双亲委派的机制

**工作原理：**

1. 如果一个类加载器收到了类加载请求，它并不会自已先去加载，而是把这个请求委托给父类的加载器去执行；
2. 如果父类加载器还存在其父类加载器，则进一步向上委托，依次递归，请求最终将到达顶层的启动类加载器；
3. 如果父类加载器可以完成类加载任务，就成功返回，若父类加载器无法完成此加载任务，子加载器才尝试自已去加载；

<img src=".\img\parent_delegation.jpg" alt="img" style="zoom:80%;margin-left:0" />

**为啥使用双亲委派机制：**

- 防止内存中出现多份同样的字节码。如果没有使用双亲委派机制，由各个类加载器自行进行类加载，会使得java类型中最基础的类出现混乱，比如用户自定义java.lang.Object类，并放在Classpath中，那系统就会出现多个Object,程序就会变得混乱。

#### 3.1.5 沙箱安全机制

通过双亲委派机制，类的加载永远都是从 启动类加载器开始，依次下放，保证你所写的代码，不会污染Java自带的源代码，所以出现了双亲委派机制，保证了沙箱安全

### 3.2  运行时数据区（Runtime Data Area）

#### 3.2.1 分代模型



#### 3.2.2 方法区（Method Area）

#### 3.2.3 栈（栈帧）

#### 3.2.4 本地方法栈（Native Method Stack）

#### 3.2.5 程序计数器（PC寄存器）

### 3.3  执行引擎（Execution Engeine）

### 3.4 本地方法接口（Native Interface） 

## 四、垃圾回收机制

## 五、性能调优



#### 思考：

java类是什么时候被加载到jvm的，加载和执行的过程是什么？



https://www.cnblogs.com/zongheng14/p/12041005.html

## java类的运行过程

 https://zhuanlan.zhihu.com/p/291027786
https://zhuanlan.zhihu.com/p/291044796 

https://www.cnblogs.com/caoxb/p/12735527.html