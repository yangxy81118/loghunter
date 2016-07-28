
## Goal 目标
It's a log4j enhancement Middleware to make programers much easiler to change the Log4j config in real-time , in other word , without restarting any server. So you change the Logger Level you want and ,immediately, you can see the appropriate logs.

LogHunter旨在构建一个让程序员便捷的在分布式系统中定位到异常数据的中间件。这是一个远大而且可以无限持续优化的项目。
目前现阶段，已经实现，**让基于Log4j2的工程实时变更日志配置**，比如你可以将一个正常为INFO级别的类日志实时变为DEBUG级别，
这样可以快速检查各个系统的数据流入与流出的正确性。当然，这个也依赖程序员对Log日志的分级别埋点的良好习惯有关。

## 开发中
对client-server之间的心跳机制进行完善，保证信息同步与自动恢复重连  
对Log4j2的格式读取进行统一管理，优化控制台的log4j2修改流程  
尝试对早期版本的Log4j进行兼容


## 小试牛刀
### maven
待提交到中心仓库

### Spring 配置
在需要监控的应用中加上
```xml
<bean class="mine.xmz.loghunter.core.collector.ClientRegister">
    <property name="registerServerPort" value="控制台端口" />
	<property name="registerServerIp" value="控制台Ip / 域名" />
	<property name="registerClientPort" value="本应用自身主动push服务端口" />
    <property name="registerClientName" value="本应用名称" />
</bean>
```
注意：registerClientPort与registerClientName将结合作为控制台应用列表的主键，所以不同应用之间要保证这两者作为共同主键的唯一性


### 控制台使用
