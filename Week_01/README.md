# JVM

## 一、JVM的数据类型

​		JAVA语言是一种静态的、面向对象、编译执行，有VM/GC和运行时、跨平台的高级语言。因为java是静态类型的，因此它影响字节码指令的设计，这样指令就会期望对特定类型的值进行操作。例如add指令就会有好几种，用于两个数的相加：iadd、ladd、fadd、dadd。它们期望的操作数分别是int、long、float、double。

### jvm定义的数据类型：

#### 基本类型：

- 数值类型

  byte（8位）、short（16位）、int（32位）、long（64位）、char（16位无符号Unicode）、float（32位IEEE 754 单精度浮点型）、double（64-bit IEEE 754双精度浮点型）

- 布尔类型

- 指针类型

#### 引用类型

- 类
- 数组
- 接口

#### 注意点：

​		在字节码中布尔类型的支持是受限的。没有结构能够直接操作布尔值。布尔值被替换成int是通过编译器来执行的，并且最终还是被转换成int结构。

## 二、class字节码

> 字节码指令集的简单性很大程度上是由于Sun设计了基于堆栈的VM架构，而不是基于PC寄存器架构。有各种各样的进程使用基于JVM的内存组件，但基本上只有JVM堆需要详细检查字节码指令。

### 定义

java bytecode由单字节（byte）的指令组成，理论上最多支持256个操作码（opcode）。实际上java只是用了200左右的操作码，还有一些操作码则保留给调试操作。

### 分类

根据指令的性质，主要分为四大类:

1、栈操作指令，包括与局部变量交互的指令。

2、程序流程控制指令。

3、对象操作指令，包括方法调用指令。

4、算术运算以及类型转换指令。

### 字节码相关指令

编译指令：javac 

查看字节码指令：javap -c 

### 示例

代码：

```
package homework.one;

public class TestJavaByteCode {

    public final String a = "final";

    public static String b = "static";

    public void main(String[] args) {
        int n1 = 4;
        int n2 = 5;
        int n3 = n1 + n2;
        System.out.println(n3);
        long l1 = 3L;
        long l2 = 7L;
        long l3 = l2 - l1;
        System.out.println(l3);
        double d1 = 2.2D;
        double d2 = 1.1D;
        double d3 = d2 / d1;
        System.out.println(d3);
        this.doLoop();
        System.out.println(a + b);
    }

    public void doLoop() {
        int num = 1;
        for (int i = 0; i < 100; i++) {
            num = num + i;
        }
        System.out.println(num);
    }
}

```

class文件：

```
Compiled from "TestJavaByteCode.java"
public class homework.one.TestJavaByteCode {
  public final java.lang.String a;

  public static java.lang.String b;

  public homework.one.TestJavaByteCode();   // -- 构造函数
    Code:
       0: aload_0                           // -- 将this装载到操作数栈中
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: ldc           #2                  // String final --jvm将常量压入栈中
       7: putfield      #3                  // Field a:Ljava/lang/String;
      10: return

  public void main(java.lang.String[]);
    Code:
       0: iconst_4
       1: istore_2
       2: iconst_5
       3: istore_3
       4: iload_2
       5: iload_3
       6: iadd
       7: istore        4
       9: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
      12: iload         4
      14: invokevirtual #5                  // Method java/io/PrintStream.println:(I)V
      17: ldc2_w        #6                  // long 3l
      20: lstore        5
      22: ldc2_w        #8                  // long 7l
      25: lstore        7
      27: lload         7
      29: lload         5
      31: lsub
      32: lstore        9
      34: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
      37: lload         9
      39: invokevirtual #10                 // Method java/io/PrintStream.println:(J)V
      42: ldc2_w        #11                 // double 2.2d
      45: dstore        11
      47: ldc2_w        #13                 // double 1.1d
      50: dstore        13
      52: dload         13
      54: dload         11
      56: ddiv
      57: dstore        15
      59: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
      62: dload         15
      64: invokevirtual #15                 // Method java/io/PrintStream.println:(D)V
      67: aload_0
      68: invokevirtual #16                 // Method doLoop:()V
      71: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
      74: new           #17                 // class java/lang/StringBuilder
      77: dup
      78: invokespecial #18                 // Method java/lang/StringBuilder."<init>":()V
      81: ldc           #2                  // String final
      83: invokevirtual #20                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/Stri
ngBuilder;
      86: getstatic     #21                 // Field b:Ljava/lang/String;
      89: invokevirtual #20                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/Stri
ngBuilder;
      92: invokevirtual #22                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      95: invokevirtual #23                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      98: return

  public void doLoop();
    Code:
       0: iconst_1
       1: istore_1
       2: iconst_0
       3: istore_2
       4: iload_2
       5: bipush        100
       7: if_icmpge     20
      10: iload_1
      11: iload_2
      12: iadd
      13: istore_1
      14: iinc          2, 1
      17: goto          4
      20: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
      23: iload_1
      24: invokevirtual #5                  // Method java/io/PrintStream.println:(I)V
      27: return

  static {};
    Code:
       0: ldc           #24                 // String static
       2: putstatic     #21                 // Field b:Ljava/lang/String;
       5: return
}

```



## 二、ClassLoader



## 三、内存模型

> JVM内存布局规定了JAVA在运行过程中内存申请、分配、管理的策略，保证了JVM的高效稳定运行。
>
> 一般来说，jvm优化95%是优化堆区，5%优化的是方法区。

### 程序计数器（PC寄存器）：

​		对于java程序中每个运行的线程，都有一个PC寄存器用于存储指令相关的现场信息。CPU只有把数据装载到寄存器才能够运行。jvm中的PC寄存器是对物理PC寄存器的一种抽象模拟。

<img src=".\img\program_counter_register.png" alt="program_counter_register" style="zoom:50%;" />

#### 作用：

PC寄存器是用来存储指向下一条指令的地址，也即将要执行的执行代码。由执行引擎读取下一条指令。

- 它是一块很小的内存空间，几乎可以忽略不计。也是运行速度最快的存储区域。
- 在jvm规范中，每个线程都有它自己的程序计数器，是线程私有的，生命周期与线程的生命周期保持一致。
- 在任何时间一个线程都只有一个方法在执行，也就是所谓的**当前方法**。程序计数器会存储当前线程正在执行的java方法的jvm指令地址；或者，如果是在执行native方法，则是末指定值（undefined）。
- 它是程序控制流的指示器，分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器来完成。
- 字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令。
- 它是唯一一个在java虚拟机规范中没有规定任何OOM情况的区域。

### JVM栈：

​		对于每个线程，都会分配一个栈/线程栈（JVM Stack），用于存储栈帧（Frame）。

​		**优势：**存取速度比较快，仅次于CPU中的寄存器；

​		**缺点：**存在栈中的数据大小与生存期必须是确定的，缺乏灵活性。

### 栈帧（Frame）：

​		栈帧由返回值、操作数栈、局部变量数组以及一个class引用组成。

​		<u>局部变量数组</u>：索引从0到它的长度-1，长度是由编译器计算的，一个局部变量可以保存任何变量的值，long和double类型的值占用两个局部变量。

​		<u>操作数栈</u>：存储指令的操作数，或者方法调用的参数。

### 堆（Heap）：

​		所有线程共享的内存和存储对象（类实例和数组）。对象回收是由垃圾收集器管理地。

​		<u>优势：</u>可以动态的分配内存大小，所有使用new xxx()构造出来的对象都在堆中存储，生存期也不必事先告诉编译器，GC会自动收走不再使用的数据。

​		<u>缺点：</u>由于要在运行时动态分配内存，存取速度较慢。

### 方法区：

对于每个已加载的类，它存储方法的代码和一个符号表（例如对字段或方法的引用）和常量池。

![JVM内存模型以及内存参数关系](.\img\JVM内存模型以及内存参数关系.png)

![](.\img\direct.png)

![no-direct](.\img\no-direct.png)

## 四、启动参数

## 五、JVM命令行

## 六、JDK内置图形化工具

### jconsole

### jvisualvm

### visualGC

### jmc

## 七、GC原理

### 算法：

标记清除

复制算法

标记整理算法

### GC分类：

| GC收集器           | 串行、并行 OR 并发 | 新生代/老年代 | 算法               | 目标         | 适用场景                                                     |
| ------------------ | ------------------ | ------------- | ------------------ | ------------ | ------------------------------------------------------------ |
| Serial GC          | 串行               | 新生代        | 复制算法           | 响应速度优先 | 单CPU环境下的Client模式                                      |
| Serial Old         | 串行               | 老年代        | 标记-整理          | 响应速度优先 | 单CPU环境下的Client模式、CMS的后备预案                       |
| ParNew             | 并行               | 新生代        | 复制算法           | 响应速度优先 | 多CPU环境时在Server模式下与CMS配合                           |
| Parallel  Scavenge | 并行               | 新生代        | 复制算法           | 吞吐量优先   | 在后台运算而不需要太多交互的任务                             |
| Parallel Old       | 并行               | 老年代        | 标记-整理          | 吞吐量优先   | 在后台运算而不需要太多交互的任务                             |
| CMS                | 并发               | 老年代        | 标记-清除          | 响应速度优先 | 集中在互联网站或B/S系统服务端上的JAVA应用                    |
| G1                 | 并发               | both          | 标记-整理+复制算法 | 响应速度优先 | 面向服务端应用，将来替换CMS                                  |
| ZGC                | 并发               | both          |                    | 响应速度优先 | 通过着色指针和读屏障，实现几乎全部的并发执行，几毫秒级别的延迟，线性可扩展； |
| Epsilon            |                    |               |                    |              | 实验性的 GC，供性能分析使用                                  |
| Shenandoah         | 并发               | both          |                    | 响应速度优先 | G1 的改进版本，跟 ZGC 类似                                   |

## 参考资料：

[1、JVM_03.程序计数器+虚拟机栈+本地方法栈](https://blog.csdn.net/lucylala007/article/details/108073696)

2、https://www.javadoop.com/post/metaspace

3、https://blog.csdn.net/star20100906/article/details/94405805

4、https://www.oschina.net/translate/introduction-to-java-bytecode

https://www.cnblogs.com/zhenbianshu/p/10210597.html

