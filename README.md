# Hadess制品管理工具
hadess是一款开源、免费的制品管理工具，提供了高效、安全的制品存储和版本控制。无论是小型团队，还是大规模企业，Hadess 都能帮助开发者从容应对复杂的制品管理需求

# 功能介绍
### 全面的制品管理
Hadess 支持多种格式的制品管理，如 Maven、NPM、Docker、helm 等，可以轻松存储和分发制品。每个制品的版本信息都一目了然，方便团队跟踪版本更新及历史记录
各类型制品库管理
![](https://image.tiklab.net/img/g1/homes/8fb3567a53bbbad9)

### 各类型制品库管理
支持多种格式的制品库，如 Maven、NPM、Docker 等，轻松管理和存储开发过程中生成的各种制品
![](https://image.tiklab.net/img/g1/homes/eaaa0d9d6a7cd6f4)

### 制品扫描
通过安全扫描功能，Hadess 能够自动检测制品中的安全漏洞和潜在风险
![](https://image.tiklab.net/img/g1/homes/52334a717485c2a8)

# 安装
### 系统要求
* Java 16+
* Maven 3.4+
### 克隆仓库
```
git clone https://gitee.com/tiklab/tiklab-hadess.git

cd tiklab-hadess
```
### 配置MAVEN仓库
配置maven的settings.xml文件的远程仓库为一下内容
```
<mirror>
    <id>hadess</id>
    <name>hadess</name>
    <url>https://mirror.tiklab.net/repository/tiklab-maven</url>  
    <mirrorOf>*</mirrorOf>
</mirror> 
```

### 构建项目

   * MAC系统：mvn clean package -P system-mac,env-dev
   * Linux系统：mvn clean package -P system-linux,env-dev
   * Windows系统：mvn clean package -P system-windows,env-dev

# 快速使用

1. 启动 HadessApplication类 
2. 浏览器访问：http://localhost:8080
3. 登录信息，用户名：admin 密码：123456

# 贡献

我们欢迎社区的贡献！如果你有好的建议或发现了问题，请通过以下方式联系我：

联系我们

邮箱: tiklab@126.com

如需了解更多信息，请访问我们的官方网站或加入我们的社区讨论：

官网：https://tiklab.net

# 进阶使用

文档地址：https://doc.tiklab.net/document/a9abc0b02ca7
演示地址：https://demo.tiklab.net/hadess