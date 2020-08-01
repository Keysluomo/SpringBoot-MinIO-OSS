# SpringBoot+MinIO+OSS

## OSS

阿里云对象存储服务（Object Storage Service，简称 OSS）为您提供基于网络的数据存取服务。使用 OSS，您可以通过网络随时存储和调用包括文本、图片、音频和视频等在内的各种非结构化数据文件。

## MinIO

MinIO是一种高性能的分布式对象存储系统。它是软件定义的，可在行业标准硬件上运行，并且在Apache V2许可下是100％开放源代码。

MinIO的不同之处在于，它从一开始就被设计为私有云对象存储的标准。由于MinIO是专门为仅服务对象而构建的，因此单层体系结构可实现所有必需的功能而不会受到影响。结果是一台同时具有性能，可伸缩性和轻量级的云原生对象服务器。

尽管MinIO在传统对象存储用例（例如**辅助存储**，**灾难恢复和归档**）方面表现出色，但在克服与机器学习，分析和云原生应用程序工作负载相关的私有云挑战方面却独树一帜。

## 项目简介

通过SpringBoot 将两种存储环境进行集成，通过application.yml文件定义的属性进行两种环境的切换。我也在其中集成了Swagger，这样我们就可以轻松的访问http://localhost:8080/swagger-ui.html 进行测试。两种不同的存储仅仅实现了简单的上传和下载，如果想使用存储的其他API我们可以访问如下官网：

- [Oss SDK](https://www.alibabacloud.com/help/zh/doc-detail/32011.htm?spm=a2c63.p38356.b99.216.5d66219fTxEx2U)

- [MinIO SDK](https://docs.min.io/docs/java-client-api-reference.html)

将相关API添加至我们的**MinIOClientSlave**和**OssClientSlave**中去，相信你肯定能很快上手。

在我们的application.yml文件中我们只需要配合MinIO或者是Oss相关属性即可切换我们底层的存储。

**application.yml**

```
cloudclient:
  status: 1             # 0：启动OSS 1: 启动MinIO
  url: http://127.0.0.1:9000  #OSS URL 或者是MinIOURL
  accesskey: minioadmin    #登录OSS账户 或者是MinIO账户
  secretkey: minioadmin   #登录OSS账户  或者是MinIO密码
  bucketName: xxxx    #代表你存储的bucket名字
  storeDir: /         # storeDir代表路径
```

