# 系统简介
## 概述
在线影视系统是基于Spring Boot、Spring Cloud & Alibaba、Vue等技术的前后端分离的系统，内置模块暂时有演员模块、影片分类模块、影片列表、在线播放、Echarts图表等。

# 技术选型
## 后端技术栈
`Mybatis、Spring、Spring Boot、Spring Cloud & Alibaba、Nacos、OSS`
## 前端技术栈
`VUE-CLI、Echarts`

# 系统服务骨架
```
cinema-online 
├── abo-gateway     				  // 网关模块 [7000]
├── abo-common       			    // 通用模块
│       └── common-util       // 通用工具包
│       └── service-base      // 基础API
├── abo-service            	  // 业务模块
│       └── service-actor     // 演员模块 [8081]
│       └── service-movies    // 影片模块 [8084]
│       └── service-oss       // OSS模块  [8082]
│       └── service-subject   // 分类模块 [8083]
│       └── service-video     // 视频模块 [8085]
├──pom.xml             		    // 公共依赖
```
