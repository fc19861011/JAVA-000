项目地址：https://github.com/apache/shardingsphere

git clone 时遇到 Filename too long的报错，解决办法如下：

1、打开git bash

2、输入  git config --global core.longpaths true 

发生错误的原因：

git有可以创建4096长度的文件名，然而在windows最多是260，因为git使用了旧版本的windows api，所以长度超过260，就会报错。