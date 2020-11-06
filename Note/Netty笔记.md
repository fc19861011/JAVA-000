# Netty笔记

## 一、核心知识

### I/O模型

I/O请求可以分为两个阶段，分别位调用阶段和执行阶段

- I/O调用阶段，即用户进程向内核发起系统调用

- I/O执行阶段，此时，内核等待I/O请求处理完成返回。

  该阶段分为两个过程：

  1、等待数据就绪，并写入内核缓冲区

  2、将内核缓冲区的数据拷贝至用户态缓冲区

### 同步阻塞I/O（BIO）

![BIO](F:\99_workspace\github\JAVA-000\Note\img\BIO.png)

### 同步非阻塞I/O（NIO）

![NIO](F:\99_workspace\github\JAVA-000\Note\img\NIO.png)

### I/O多路复用![I_O多路复用](F:\99_workspace\github\JAVA-000\Note\img\I_O多路复用.png)

多路复用实现了**一个线程处理多个I/O句柄的操作**。多路指的是多个**数据通道**，复用指的是使用一个或者多个固定线程来处理每一个Socket。select、poll、epoll都是I/O多路复用的具体实现，线程一次select调用可以获取内核态中多个数据通道的数据状态。多路复用解决了BIO和NIO的问题是一种非常高效的I/O模型。

### 信号驱动I/O