# JiangYang Flink Module

江阳科技 Flink 任务管理模块，提供 Flink 任务的提交、监控和管理功能。

## 功能特性

- ✅ Flink 任务提交
- ✅ 任务状态监控
- ✅ 任务取消
- ✅ 运行中任务列表
- ✅ 任务详情查询
- ✅ 简单的示例任务

## 快速开始

### 1. 构建项目

```bash
cd jiangYang-module-flink
mvn clean package
```

### 2. 启动服务

```bash
mvn spring-boot:run
```

### 3. 提交示例任务

#### 提交简单的 WordCount 任务

```bash
curl -X POST http://localhost:48166/flink/job/submit-simple-wordcount
```

#### 提交 Kafka 流处理任务

```bash
curl -X POST http://localhost:48166/flink/job/submit-kafka-stream
```

#### 提交自定义任务

```bash
curl -X POST http://localhost:48166/flink/job/submit-custom \
  -H "Content-Type: application/json" \
  -d '{
    "jarName": "your-job.jar",
    "entryClass": "com.example.YourJobClass",
    "jobName": "Your Custom Job",
    "parallelism": 2,
    "programArgs": "--input-topic test --output-topic result"
  }'
```

### 4. 监控任务

#### 获取运行中的任务列表

```bash
curl http://localhost:48166/flink/job/list-running
```

#### 获取任务状态

```bash
curl http://localhost:48166/flink/job/status?jobId=your-job-id
```

#### 获取任务详情

```bash
curl http://localhost:48166/flink/job/info?jobId=your-job-id
```

#### 取消任务

```bash
curl -X POST http://localhost:48166/flink/job/cancel?jobId=your-job-id
```

## 配置说明

### Flink 配置

在 `application-dev.yaml` 中配置：

```yaml
jiangyang:
  flink:
    job-manager-url: http://localhost:8081  # Flink JobManager REST API 地址
    jar-path: /app/flink/jars              # JAR 包存储路径
    task-timeout: 60000                    # 任务超时时间（毫秒）
```

### 示例任务

#### SimpleWordCountJob

一个简单的 WordCount 示例，演示基本的 Flink 流处理：

- 输入：预定义的字符串数组
- 处理：单词计数
- 输出：控制台打印结果

#### KafkaStreamJob

一个 Kafka 流处理示例：

- 输入：Kafka 主题 `input-topic`
- 处理：转换为大写并添加时间戳
- 输出：控制台打印处理结果

## API 接口

### 任务管理接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/flink/job/submit` | 提交 Flink 任务 |
| POST | `/flink/job/submit-simple-wordcount` | 提交 WordCount 示例任务 |
| POST | `/flink/job/submit-kafka-stream` | 提交 Kafka 流处理任务 |
| POST | `/flink/job/submit-custom` | 提交自定义任务 |
| POST | `/flink/job/cancel` | 取消任务 |
| GET | `/flink/job/status` | 获取任务状态 |
| GET | `/flink/job/info` | 获取任务详情 |
| GET | `/flink/job/list-running` | 获取运行中任务列表 |

### 数据加载接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/flink/load/status` | 获取数据加载状态 |
| GET | `/flink/load/info` | 获取数据加载详情 |

## 开发指南

### 创建新的 Flink Job

1. 在 `src/main/java/com/flink/jiangyang/module/flink/job/` 目录下创建新的 Job 类
2. 实现 `main` 方法作为入口点
3. 在 `FlinkJobBuilder` 中添加构建方法
4. 在 `FlinkJobController` 中添加提交接口

### 示例：创建新的 Job

```java
public class MyCustomJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // 你的 Flink 处理逻辑
        DataStream<String> stream = env.fromElements("Hello", "World");
        stream.print();
        
        env.execute("My Custom Job");
    }
}
```

### 构建 JAR 包

项目已配置 Maven Shade 插件，运行以下命令构建包含所有依赖的可执行 JAR：

```bash
mvn clean package
```

生成的 JAR 包位于 `target/` 目录下。

## 注意事项

1. 确保 Flink 集群已启动并可访问
2. 确保 JAR 包路径配置正确
3. 对于 Kafka 任务，确保 Kafka 集群可访问
4. 任务提交后可以通过 Flink Web UI 查看详细日志

## 故障排除

### 常见问题

1. **任务提交失败**
   - 检查 Flink 集群状态
   - 检查 JAR 包是否存在
   - 查看 Flink 日志

2. **任务运行失败**
   - 检查任务日志
   - 检查资源配置
   - 检查依赖项

3. **连接超时**
   - 检查网络连接
   - 调整超时配置
   - 检查防火墙设置

## 版本信息

- Spring Boot: 3.2.5
- Flink: 1.18.1
- Java: 17 