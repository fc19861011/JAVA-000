# GC演练

演示环境：

![pc-detail](.\img\pc-detail.png)

各堆内存和对比（详细测试LOG和分析文件在logs目录下）：

![details](.\img\details.png)

生产对象横向对比图

![objects](.\img\objects.png)

![serial](.\img\serial.png)

![parallel1](.\img\parallel1.png)

![cms](.\img\cms.png)

![g1](.\img\g1.png)

综上可以看出：

1、串行GC在1G左右吞吐达到高点，同时产生的对象也最多，随着堆内存变高，性能衰减较为明显

2、并行GC在2G左右产生的对象最多，在2-4G之间吞吐量比较平缓，内存继续变大，整个运行期间只有Young GC，但生产对象数减少较为明显。

3、CMS GC在1G左右产生对象最多，同时吞吐也最高，随着内存变大，吞吐变化较为平缓，产生对象也较为平缓。

4、G1 GC在堆内存到达512M后，产生对象和吞吐都比较均匀，随着堆内存不断的变大，产生对象数越来越多，吞吐比较平缓。

综合来看：单线程，小内存更适合使用串行GC，多线程情况下适合使用并行GC，内存在4G往后，更适合使用CMS和G1。