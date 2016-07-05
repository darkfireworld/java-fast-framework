# java-fast-framework

## 配置流程

1. 用idea打开该项目
2. 勾选maven配置为debug
3. 添加启动器，指定为Application
4. 修改 Main class:org.darkgem.jetty.Launcher
5. 修改Before launch->Run Maven command 'test'


## Server设计原则

* SOA设计
* 维护性
* 安全(数据，通信，接口)
* 分布式
* 灾容
* 一致性
* 数据备份
* 测试和正式
* 快速迭代（数据库，磁盘文件，协议兼容，自动升级，废弃版本）
* 文档
* 日志