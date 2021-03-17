# 生活记录项目
## 项目介绍
生活记录项目是一套微服务架构系统，
采用了 Spring Cloud Hoxton & Alibaba、Spring Boot、MyBatis、Docker、Netty、Redis等核心技术，
同时提供了基于Vue的快速搭建客户端模块。生活记录项目在日常业务的基础集成了注册中心、配置中心、监控中心、
网关等系统功能，并内置了即使聊天室模块。

## 组织结构
```
index --- 前端模块
nanmeishu-common -- 工具类及通用代码模块
nanmeishu-im -- 内置即使聊天室模块服务
nanmeishu-tale -- 故事/日记服务
nanmeishu-transaction -- 代办事务服务
nanmeishu-upload -- 文件上传模块服务
nanmeishu-user -- 用户服务
parent -- 版本管理模块（用于管理依赖版本）
```

## 项目演示
- [web端演示](http://www.baidu.com)
- [安卓APK](http://www.jd.com)

## 技术选型
#### 后端技术

技术 | 说明
---|---
Jdk8 | 开发语言
Mysql | 数据库技术
Redis | 分布式缓存
Spring Boot | 容器+MVC框架
Mybatis-Plus | ORM框架
Lombok | 简化对象封装工具
Nacos | 注册中心/配置中心
Gateway | 网关
Feign | 服务调用
Druid | 数据库连接池
Maven | 项目管理工具
Netty | 网络框架

#### 前端技术
技术 | 说明
---|---
Vue | 前端框架
Vue-cli | 脚手架
Vue-router|路由框架
Vant | UI框架
Axios | 前端HTTP库
Vuex | 全局状态管理
Web Socket | 通信技术

#### 其他技术
技术 | 说明
---|---
CentOS7 | 服务器环境
Docker | 容器技术
Git | 版本管理技术
OSS | 文件存储

## 模块介绍

### 网关模块
#### 部署
##### docker部署
- 创建一个文件夹用于存放网关服务运行环境
- 上传打包好的jar文件
- vi Dockerfile   ---编写Dockerfile文件
```
FROM java:8
LABEL author="lzy"
EXPOSE 8888
ADD nanmeishu-gateway-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","application.jar"]
```

```
docker build -t nanmeishu-gateway:1.0 .    ---构建镜像
docker run -d --name nanmeishu-gateway -p 8888:8888 nanmeishu-gateway:1.0 ---运行容器
```

### 用户模块
#### 部署
##### docker部署
- 创建一个文件夹用于存放用户服务运行环境
- 上传打包好的jar文件
- vi Dockerfile   ---编写Dockerfile文件

```
FROM java:8
LABEL author="lzy"
ADD nanmeishu-user-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","application.jar"]
```

```
docker build -t nanmeishu-user:1.0 .    ---构建镜像
docker run -d --name nanmeishu-user nanmeishu-user:1.0 ---运行容器
```
- 运行成功后在浏览器中输入链接测试 

```
http://服务器地址:8888/area/listAreaAndDetails 
```
### 故事/日记模块
#### 部署
##### docker部署
- 创建一个文件夹用于存放故事/日记服务运行环境
- 上传打包好的jar文件
- vi Dockerfile   ---编写Dockerfile文件

```
FROM java:8
LABEL author="lzy"
ADD nanmeishu-tale-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","application.jar"]
```

```
docker build -t nanmeishu-tale:1.0 .    ---构建镜像
docker run -d --name nanmeishu-tale nanmeishu-tale:1.0 ---运行容器
```
- 运行成功后在浏览器中输入链接测试 
 
```
http://服务器地址:8888/tale/getCaiHongPi
```






## 数据表解析
## 用户服务（user）
### 用户表结构（uu_user）

| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| userId | long | 用户id | 无 |
| username | string | 用户名 |无 |
| password | string | 密码（加密） |123456 |
| createTime | datetime | 创建时间 |无 |
| identityCard | string | 身份证号 |无 |
| age | int | 年龄 |无 |
| sex | int | 性别： 0:未知 1：男 2：女 |0 |
| address | string | 详细地址 |无 |
| areaDetailsId | long | 户籍所在地ID |无 |
| dateOfBirth | date | 出生日期 |无 |
| isDelete | int | 是否已删除 0 ：否 1： 是 |0 |
| ipNumber | string | 最后登陆ip |无 |
| roleId | long | 角色ID |无 |
| phone | string | 手机号 |无 |
| headPortrait | string | 头像Url |无 |
| qqKey | string | QQ key |无 |
| wxKey | string | 微信Key |无 |

#### 表创建sql

```
CREATE TABLE `uu_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户iD',
  `username` varchar(31) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码（加密）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `identity_card` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` smallint(6) NOT NULL DEFAULT '0' COMMENT '性别： 0:未知 1：男 2：女',
  `address` varchar(60) CHARACTER SET ujis DEFAULT '' COMMENT '详细地址',
  `area_details_id` bigint(20) DEFAULT NULL COMMENT '户籍所在地ID',
  `date_of_birth` date DEFAULT NULL COMMENT '出生日期',
  `is_delete` smallint(6) NOT NULL DEFAULT '0' COMMENT '是否已删除 0 ：否 1： 是',
  `ip_number` varchar(20) DEFAULT NULL COMMENT '最后登陆ip',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `phone` varchar(13) NOT NULL COMMENT '手机号',
  `head_portrait` varchar(255) DEFAULT NULL COMMENT '头像Url',
  `qq_key` varchar(255) DEFAULT NULL COMMENT 'QQkey',
  `wx_key` varchar(255) DEFAULT NULL COMMENT '微信key',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1338679112490074115 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
```

### 地区表（uu_area）
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| areaId | long | 地区iD | 无 |
| name | string | 地区名 |无 |
| isDelete | int | 0:可用 1：不可用 |0 |
| create_time | date | 创建时间 |无 |
| br | string | 备注 |无 |

#### 地区表创建sql

```
CREATE TABLE `uu_area` (
  `area_id` bigint(20) NOT NULL COMMENT '地区iD',
  `name` varchar(15) NOT NULL COMMENT '地区名',
  `is_delete` smallint(6) NOT NULL DEFAULT '0' COMMENT '0:可用 1：不可用',
  `br` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`area_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
```

### 地区明细表（uu_area_details）
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| areaDetailsId | long | iD | 无 |
| name | string | 地区详情名 |无 |
| isDelete | int | 0:可用 1：不可用 |0 |
| br | string | 备注 |无 |
| create_time | date | 创建时间 |无 |
| areaId | long | 所属地区ID | 无|

#### 地区明细表创建sql
```
CREATE TABLE `uu_area_details` (
  `area_details_id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '地区详情名',
  `br` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_delete` smallint(6) NOT NULL DEFAULT '0' COMMENT '0 :可用 1：不可用',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `area_id` bigint(20) NOT NULL COMMENT '地区ID',
  PRIMARY KEY (`area_details_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
```
### 角色表（uu_role）
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| roleId | long | iD | 无 |
| name | string | 角色名 |无 |
| br | string | 备注 |无 |

#### 角色表创建sql

```
CREATE TABLE `uu_role` (
  `role_id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '角色名',
  `br` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```
### 资源路径表(uu_path)
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| pathId | long | iD | 无 |
| name | string | 权限路径描述 |无 |
| url | string | 具体接口路径 |无 |

#### 资源路径创建sql

```
CREATE TABLE `uu_path` (
  `path_id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '权限路径描述',
  `url` varchar(100) NOT NULL COMMENT '具体接口路径',
  PRIMARY KEY (`path_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

#### 角色 -- 资源路径表多对多关系表 (uu_role_path)
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| rolePathId | long | iD | 无 |
| roleId | long | 角色id |无 |
| pathId | long | 资源id |无 |

#### 角色--资源表创建sql

```
CREATE TABLE `uu_role_path` (
  `role_path_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `path_id` bigint(20) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_path_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```
#### 好友关系表 (uu_friend)
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| friendId | long | iD | 无 |
| userId | long | 好友的user_id |无 |
| myUserId | long | 本人user_id |无 |
| createTime | datetime | 添加时间 |无 |
| groupId | int | 所属分组  -1 为 默认分组 |-1 |
| statuss| int | 状态 0：正常 1：消息不显示 2：拉黑 3：消息显示不通知|0|
| brName | string | 备注名 |无 |
| mesTop | int | 特别关心 0 否 1是 |0 |
| isMyYour | int | 我是否看他分享 0 是 1 否 |0 |
| isYourMy | int | 他是否能看我分享 0 是 1 否 |0 |
```
CREATE TABLE `uu_friend` (
  `friend_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '好友的user_id',
  `my_user_id` bigint(20) NOT NULL COMMENT '本人user_id',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `group_id` int(11) NOT NULL DEFAULT '-1' COMMENT '所属分组  -1 为 默认分组',
  `statuss` smallint(1) NOT NULL DEFAULT '0' COMMENT '状态 0：正常 1：消息不显示 2：拉黑 3：消息显示不通知',
  `br_name` varchar(255) DEFAULT NULL COMMENT '备注名',
  `mes_top` smallint(1) NOT NULL DEFAULT '0' COMMENT '特别关心 0 否 1是',
  `is_my_your` smallint(1) NOT NULL DEFAULT '0' COMMENT '我是否看他分享 0 是 1 否',
  `is_your_my` smallint(1) NOT NULL DEFAULT '0' COMMENT '他是否看我分享 0 是 1 否',
  PRIMARY KEY (`friend_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
```
## 事务服务（transaction）
### 事务表结构（s_transaction）

| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| transactionId| long | 事务id | 无 |
| content | string | 内容 |无 |
| createTime | datetime | 创建时间 |无 |
| duration | int | 时长 单位秒，-1未永久性任务 |-1 |
| statuss | int | 状态 0 ：未完成 1：已完成 2：暂停 |0 |
| extendDate | int | 延长时间 单位秒 |无 |
| userId | long | 所属用户id |无|
| startDate | date | 开始日期 2000-01-01 为每日必做 |无 |
| startTime | time | 开始时间 00.00.44 为不规定时间 |无 |
| commitDateTime | datetime | 完成时间 |无 |

#### 表创建sql

```
CREATE TABLE `s_transaction` (
  `transaction_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `duration` int(11) NOT NULL DEFAULT '-1' COMMENT '时长 单位秒，-1未永久性任务，默认值：-1',
  `statuss` smallint(1) NOT NULL DEFAULT '0' COMMENT '状态 0 ：未完成 1：已完成 2：暂停 默认值 0',
  `extend_date` int(11) DEFAULT NULL COMMENT '延长时间 单位秒',
  `user_id` bigint(20) NOT NULL COMMENT '所属用户id',
  `start_date` date NOT NULL COMMENT '开始日期 2000-01-01 为每日必做',
  `start_time` time NOT NULL COMMENT '开始时间 00.00.44 为不规定时间',
  `commit_date_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;
```
### 事务明细表（s_transaction_detail）
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| transactionDetailId | long | 事务明细iD | 无 |
| content | string | 内容 |无 |
| commitTime | datetime | 完成时间 |无 |
| statuss | int | 0:未完成 1：已完成 2：暂停 3：已过时  |0 |
| transaction_id | long | 所属事务id |无 |

#### 创建sql

```
CREATE TABLE `s_transaction_detail` (
  `transaction_detail_id` bigint(255) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL COMMENT '内容',
  `commit_time` datetime NOT NULL COMMENT '完成时间',
  `statuss` smallint(1) NOT NULL DEFAULT '0' COMMENT '0:未完成 1：已完成 2：暂停 3：已过时 ',
  `transaction_id` bigint(20) NOT NULL,
  PRIMARY KEY (`transaction_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
```
## 故事服务
### 故事表结构
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| taleId | long | 故事ID | 无 |
| createTime | dateTime | 创建时间 |无 |
| updateTime | dateTime | 修改时间 |无 |
| frontDate | date | 故事开始日期 |无 |
| endDate | date | 故事结束日期 | 无 |
| isDelete | int | 是否删除 0:否 1：是 |0 |
| type | int | 类型 0：日记 1：经历 2：亿人 3 ：亿事 4 ：其他 |无 |
| objectt | String | 对象（与类型对应） |无 |
| userId | long | 所属用户id |无 |

#### 表创建sql

```
CREATE TABLE `tt_tale` (
  `tale_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `front_date` date NOT NULL COMMENT '故事开始日期',
  `end_date` date NOT NULL COMMENT '故事结束日期',
  `is_delete` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0:否 1：是',
  `type` smallint(1) NOT NULL COMMENT '类型 0：日记 1：经历 2：亿人 3 ：亿事 4 ：其他',
  `objectt` varchar(50) DEFAULT NULL COMMENT '对象（与类型对应）',
  `user_id` bigint(20) NOT NULL COMMENT '所属用户id',
  PRIMARY KEY (`tale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### 故事明细表结构
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| taleDetailsId | long | 故事明细ID | 无 |
| content | String | 内容 |无 |
| taleTitle | String | 标题---不超过15个字 |无 |
| titleHead | String | 简单描述---不超过50个字 |无 |
| taleId | Long | 故事id|无 |


#### 表创建sql

```
CREATE TABLE `tt_tale_details` (
  `tale_details_id` bigint(20) NOT NULL COMMENT '故事明细id',
  `content` text NOT NULL COMMENT '内容',
  `tale_title` varchar(30) NOT NULL COMMENT '标题---不超过15个字',
  `title_head` varchar(100) DEFAULT NULL COMMENT '简单描述---不超过50个字',
  `tale_id` bigint(20) NOT NULL COMMENT '故事id',
  PRIMARY KEY (`tale_details_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```
#### 心情表（tt_statu）
| 字段 | 类型 | 备注 | 默认值 |
| :-----:| :----: | :----: | :----: |
| statuId | long | iD | 无 |
| name | string | 心情名 |无 |
| statuDescribe | string | 描述 |无 |
| statuUrl | string | 对应连接地址 |无 |
| isDelete | int | 是否启用 0 是 1 否 |0 |
| sort | int | 排序数值越大排前面|无 |

#### 心情表創建sql

```
CREATE TABLE `tt_statu` (
  `statu_id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(10) NOT NULL COMMENT '心情名',
  `statu_describe` varchar(60) DEFAULT NULL COMMENT '描述',
  `statu_url` varchar(255) NOT NULL COMMENT '对应连接地址',
  `is_delete` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否启用 0 是 1 否',
  `sort` int(2) DEFAULT NULL COMMENT '排序 数值越大排前面',
  PRIMARY KEY (`statu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```





