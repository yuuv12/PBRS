### 童书绘儿童绘本检索系统使用说明文档

---

#### 📚 项目概述

本项目是一个基于Maven、MyBatis、MySQL、Python、Java、JavaScript、HTML、CSS、Tomcat构建的绘本图书检索系统

---

#### ©️ 版权声明

该项目的版权归项目小组所有。未经授权，不得用于商业用途。代码的修改和再发布需保留原作者信息，并注明出处。

---

#### 1. 🛠️ 系统使用说明

**1.1 🌐 环境要求**

- 操作系统：Windows 11
- Java JDK 17或以上版本
- MySQL 8.0或以上版本
- Tomcat 8.0或以上版本
- Python 3.10或以上版本
- 浏览器：推荐使用Chrome、edge浏览器

**1.2 📥 安装和配置**

1. **下载项目代码**：
    - 从代码仓库下载最新版本的项目代码。

2. **配置数据库**：
    - 启动MySQL服务器。
    - 创建数据库并导入`/sql/picturebookretrievalsystem4children.sql`文件。
      ```sql
      CREATE DATABASE my_database;
      USE my_database;
      SOURCE path/to/picturebookretrievalsystem4children.sql;
      ```

3. **配置项目**：
    - 配置项目的数据库连接信息，修改`src/main/resources/jdbc.cj.properties`文件中的数据库连接配置。
      ```properties
      driver=YourMySqlDriver
      url=YourMySqlUrl
      username=YourUserName
      password=YourPassword
      ```

4. **编译和打包**：
    - 使用Maven编译和打包项目。
      ```sh
      mvn clean install
      ```

5. **部署到Tomcat**：
    - 将生成的WAR文件部署到Tomcat的`webapps`目录下。

6. **启动Tomcat**：
    - 启动Tomcat服务器，访问 <http://localhost:8080/PBRS> 进行测试。

---
#### 2. 使用系统

1. **访问系统**：
    - 在浏览器中打开系统首页。

2. **语音检索**：
    - 点击麦克风图标，允许浏览器访问麦克风，开始语音输入。
    - 系统会将语音转换为文本并进行搜索。

3. **文本检索**：
    - 在搜索框中输入关键词，点击搜索按钮或按下回车键进行检索。

4. **分面筛选**：
    - 在检索结果页面，通过左侧或顶部的筛选选项进行分面筛选。

---

#### 3. 系统维护和更新

**3.1 监控和维护**

- 定期检查系统日志，确保没有错误和警告。
- 监控服务器性能，确保系统能够高效运行。
- 定期备份数据库和项目代码。

**3.2 更新和优化**

- 根据用户反馈和使用情况，定期更新系统功能和优化性能。
- 更新依赖库和框架版本，修复已知漏洞和问题。

---

#### 4. 版权和贡献者

**4.1 版权声明**

- 本项目代码的版权归项目小组所有。未经授权，不得用于商业用途。

#### 5. 支持和联系

如果在使用过程中遇到问题或有任何建议，请联系项目组：

- 邮箱：shenkang.zhai@outlook.com
- 项目主页：[GitHub Repository](https://github.com/yuuv12/PBRS.git)

---

感谢您使用我们的网页检索系统，希望您能有良好的体验！
