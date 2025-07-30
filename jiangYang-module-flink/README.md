# JiangYang Flink Module

江阳科技 Flink 模块，提供 Flink 任务管理和执行功能。

## 功能特性

- ✅ Flink Job 提交和管理
- ✅ 多种预定义 Job 类型
- ✅ 自动 JAR 包查找
- ✅ 服务状态轮询监控
- ✅ RESTful API 接口

## 预定义 Job 类型

### 1. WordCount Job
简单的单词计数示例任务。

**接口：** `POST /flink/job/submit-simple-wordcount`

### 2. Kafka Stream Job
Kafka 流处理示例任务。

**接口：** `POST /flink/job/submit-kafka-stream`

### 3. Service Polling Job ⭐ **新增**
服务轮询查询任务，定期检查已注册服务的状态。

**接口：** `POST /flink/job/submit-service-polling`

**功能：**
- 每 10 秒轮询一次已注册的服务
- 检查服务状态（RUNNING、HEALTHY、WARNING、UNHEALTHY）
- 输出服务状态监控结果

## 快速开始

### 1. 编译打包

```bash
cd jiangYang-module-flink
mvn clean package
```

### 2. 启动应用

```bash
# 使用 local profile
java -jar target/jiangyang-module-flink-2.4.2-SNAPSHOT.jar --spring.profiles.active=local
```

### 3. 提交服务轮询任务

```bash
# 提交服务轮询任务
curl -X POST http://localhost:48166/flink/job/submit-service-polling
```

### 4. 查看任务状态

```bash
# 获取运行中的任务列表
curl -X GET http://localhost:48166/flink/job/list-running

# 获取任务详情
curl -X GET "http://localhost:48166/flink/job/info?jobId=YOUR_JOB_ID"
```

## API 接口

### Job 管理接口

| 接口 | 方法 | 描述 |
|------|------|------|
| `/flink/job/submit` | POST | 提交自定义 Flink 任务 |
| `/flink/job/submit-simple-wordcount` | POST | 提交 WordCount 任务 |
| `/flink/job/submit-kafka-stream` | POST | 提交 Kafka 流处理任务 |
| `/flink/job/submit-service-polling` | POST | 提交服务轮询任务 |
| `/flink/job/cancel` | POST | 取消任务 |
| `/flink/job/status` | GET | 获取任务状态 |
| `/flink/job/info` | GET | 获取任务详情 |
| `/flink/job/list-running` | GET | 获取运行中的任务列表 |

### 测试接口

| 接口 | 方法 | 描述 |
|------|------|------|
| `/flink/test/jar-info` | GET | 查看 JAR 包查找信息 |
| `/flink/test/build-wordcount` | GET | 测试构建 WordCount Job |
| `/flink/test/build-service-polling` | GET | 测试构建服务轮询 Job |

## 配置说明

### application-local.yaml

```yaml
# Flink Job 配置
flink:
  job:
    # JAR 包路径，如果为空则自动查找
    jar-path: D:/jiangyang-jdk17/jiangYang-module-flink/target/jiangyang-module-flink-2.4.2-SNAPSHOT.jar
    # 是否启用自动查找 JAR 包
    auto-find-jar: false
    # 自定义 JAR 包查找路径列表
    jar-search-paths:
      - D:/jiangyang-jdk17/jiangYang-module-flink/target
      - target
      - ../jiangYang-module-flink/target
```

## 服务轮询 Job 详解

### 功能描述

`ServicePollingJob` 是一个持续运行的任务，用于监控已注册服务的状态：

1. **轮询频率：** 每 10 秒执行一次
2. **监控服务：** 模拟监控以下服务
   - jiangyang-gateway:8080
   - jiangyang-module-system:8081
   - jiangyang-module-crm:8082
   - jiangyang-module-erp:8083
   - jiangyang-module-bpm:8084
   - jiangyang-module-flink:48166
   - jiangyang-module-ai:8085（随机出现）

3. **状态检查：** 返回以下状态之一
   - RUNNING：服务正常运行
   - HEALTHY：服务健康
   - WARNING：服务警告
   - UNHEALTHY：服务不健康

### 输出示例

```
Service Polling Result> (jiangyang-gateway:8080, RUNNING)
Service Polling Result> (jiangyang-module-system:8081, HEALTHY)
Service Polling Result> (jiangyang-module-crm:8082, WARNING)
Service Polling Result> (jiangyang-module-erp:8083, UNHEALTHY)
```

## 开发指南

### 添加新的 Job 类型

1. 在 `src/main/java/com/flink/jiangyang/module/flink/job/` 下创建新的 Job 类
2. 在 `FlinkJobBuilder` 中添加构建方法
3. 在 `FlinkJobController` 中添加提交接口
4. 更新 `pom.xml` 中的 `mainClass`（如果需要）

### 自定义服务轮询

可以修改 `ServicePollingJob` 中的 `getRegisteredServices()` 方法来：
- 连接真实的注册中心（如 Nacos、Consul）
- 实现真实的健康检查逻辑
- 添加告警和通知功能

## 故障排除

### 常见问题

1. **JAR 包找不到**
   - 检查 `application-local.yaml` 中的 `jar-path` 配置
   - 确保 JAR 包已正确编译

2. **类找不到**
   - 确保 Job 类在正确的包路径下
   - 检查 `pom.xml` 中的 `mainClass` 配置

3. **任务提交失败**
   - 检查 Flink JobManager 是否正常运行
   - 查看 Flink Web UI 中的错误日志

## 版本信息

- **Spring Boot:** 3.2.5
- **Flink:** 1.18.1
- **Java:** 17

## 许可证

MIT License 