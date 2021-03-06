## I/O模型

I/O请求可以分为两个阶段，分别位调用阶段和执行阶段

- I/O调用阶段，即用户进程向内核发起系统调用

- I/O执行阶段，此时，内核等待I/O请求处理完成返回。

  该阶段分为两个过程：

  1、等待数据就绪，并写入内核缓冲区

  2、将内核缓冲区的数据拷贝至用户态缓冲区

### 同步阻塞I/O（BIO）

<img src=".\img\BIO.png" alt="BIO" style="zoom: 33%;" />

### 同步非阻塞I/O（NIO）

<img src=".\img\NIO.png" alt="NIO" style="zoom: 33%;" />

### I/O多路复用

<img src=".\img\I_O多路复用.png" alt="I_O多路复用" style="zoom: 33%;" />

多路复用实现了**一个线程处理多个I/O句柄的操作**。多路指的是多个**数据通道**，复用指的是使用一个或者多个固定线程来处理每一个Socket。select、poll、epoll都是I/O多路复用的具体实现，线程一次select调用可以获取内核态中多个数据通道的数据状态。多路复用解决了BIO和NIO的问题是一种非常高效的I/O模型。

### 信号驱动I/O

<img src=".\img\sigio.png" alt="sigio" style="zoom: 33%;" />

信号驱动 I/O 并不常用，它是一种半异步的 I/O 模型。在使用信号驱动 I/O 时，当数据准备就绪后，内核通过发送一个 SIGIO 信号通知应用进程，应用进程就可以开始读取数据了。

### 异步I/O

<img src=".\img\async.png" alt="async" style="zoom: 33%;" />

异步 I/O 最重要的一点是从内核缓冲区拷贝数据到用户态缓冲区的过程也是由系统异步完成，应用进程只需要在指定的数组中引用数据即可。异步 I/O 与信号驱动 I/O 这种半异步模式的主要区别：**信号驱动 I/O 由内核通知何时可以开始一个 I/O 操作，而异步 I/O 由内核通知 I/O 操作何时已经完成**。

