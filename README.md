## 项目简介

### 介绍

基于 SpringBoot 2.2.7 + SpringCloud Hoxton.SR4 + SpringCloudAlibaba 2.2.1主体框架开发的一个快速搭建微服务项目的父项目，集成Gateway、Nacos、Sentinel、Redis、RocketMQ、Swagger2等组件，快速实现服务统一鉴权、请求统一入口、网关限流、服务熔断、服务降级、分布式锁、接口API文档自动管理、消息事件驱动服务、接口、按钮级别的资源权限管理 等功能...

### 项目架构

软件架构说明

- SpringBoot 2.2.7.RELEASE + SpringCloud Hoxton.SR4 + SpringCloudAlibaba 2.2.1.RELEASE
- Gateway 统一服务访问网关
- Nacos 服务注册、服务治理中心 ，分布式配置中心,支持分布式系统中的外部化配置，配置更改时自动刷新
- Sentinel 服务熔断、降级框架， 使用流量控制、熔断降级、系统负载保护等多个维度来保障服务之间的稳定性。
- RocketMQ 消息驱动能力：基于 Spring Cloud Stream 为微服务应用构建消息驱动能力
- 基于Redisson实现的分布式锁，实现注解、手动控制。接口+代码块级别的细粒度分布式公平锁、可重入锁、读写锁控制
- Druid、Mybatis、MybatisPlus 持久层框架：阿里数据源、Mybatis 轻量ORM框架、MyBatisPlus MyBatis增强插件
- Redis ：基于内存的NoSql数据库、自定义集成FastJSON作为数据存储序列化方案
- Swagger2、Knife4j ：ResultFul API 管理、调试，Knife4j ：增强Swagger2原生UI实现
- PBKDF2 密码加密算法（美国政府机构已经将这个方法标准化，并且用于一些政府和军方的系统。 这个方案最大的优点是标准化，实现容易同时采用了久经考验的SHA算法。）
- Jwt Token 标准化跨域认证解决方案 
- 自定义注解+Starter实现服务API统一管理，零耦合，网关统一鉴权

### 目录结构

项目依赖使用Maven进行管理，基本结构概述：


```java
├─application-server-parent	/*应用程序服务 父Moudel项目*/
├─application-server-api-parent	/*服务统一远程调用依赖解耦封装 父Moudel项目*/
├─application-server-domain-parent	/*服务统一实体依赖解耦封装 父Moudel项目*/
├─common-boot-starter		/*自定义boot-starter、工具包 实现父Moudel项目*/
│  ├─common-spring-boot-starter		/*自定义项目通用工具包：统一响应格式，SpringMvc异常统一处理，自定义Token、加密工具类...*/
│  ├─locks-spring-boot-starter		/*自定义注解，基于AOP+Redisson实现分布式锁，支持：可重入锁、公平锁、读写锁*/
│  ├─mysql-spring-boot-starter		/*自定义持久层操作工具包：公共BaseService、基础CRUD、本地事务处理、数据源连接池、MyBatis返回类型转换工具*/
│  ├─redis-spring-boot-starter		/*自定义Redis操作工具包：封装了一些基本List、简单对象、Set、Map、ZSet操作工具类，简单List分页查询、自定义FastJSON序列化方案*/
│  ├─rocketmq-stream-spring-boot-starter	/*自定义Rocket操作工具包*/
│  ├─server-permission-apis-spring-boot-starter		/*自定义注解+自定义事件监听，实现零耦合服务上线API权限集合监听，统一推送，统一自动更新获取事件，实现GateWay不需要调用服务即可实现服务鉴权，授权服务自动更新所有服务Api接口、授权信息*/
│  └─swagger-spring-boot-starter	/*Swagger 自定义API接口管理方案*/                                     
```

### 依赖环境

1. Java 1.8
2. Nacos 1.2.1
3. MySQL 5.7
4. Redis 3.2
5. RocketMQ 4.7.1
6. Sintinel 1.7.2



