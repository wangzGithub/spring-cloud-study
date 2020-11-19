# 了解Eureka Server和Eureka Client
## 步骤
### 新建root项目，在项目中以模块的形式运行Eureka服务器和Eureka客户端项目
#### idea新建maven项目，在pom中增加以下代码
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modeVersion>4.0.0</modeVersion>
    
    <groupId>com.wangz</groupId>
    <artifactId>sc-f-step1</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    
    <name>sc-f-step1</name>
    <description>Demo project for Spring Cloud</description>
    
    <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>2.0.3.RELEASE</version>
      <relativePath />
    </parent>
    
    <!-- 引入子模块pom -->
    <modules>
      <module>eureka-server</module>
      <module>eureka-client</module>
    </modules>
    
    <properties>
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
      <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
      <java.version>1.8</java.version>
      <spring-cloud.version>Finchley.RELEASE</spring-cloud.version>
    <properties>
    
    <dependencies>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
      </dependency>
    </dependencies>
    
    <dependencyManagement>
      <dependencies>
        <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-dependencies</artifactId>
          <version>${spring-cloud.version}</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
      </dependencies>
    </dependencyManagement>
    
    <build>
      <plugins>
        <plugin>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-maven-plugin</artifactId>
        </plufin>
      <plugins>
    </build>
    
</project>
```
#### 在项目中新建子模块，eureka-server，作为服务器
##### 在eureka-server的pom.xml中加入以下代码
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.wangz</groupId>
        <artifactId>sc-f-step1</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <groupId>com.wangz</groupId>
    <artifactId>eureka-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>eureka-server</name>
    <description>Demo project for Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
    </dependencies>

</project>
```
##### 修改application.yml，增加以下配置
```
server:
  port: 8761
  
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

spring:
  application:
    name: eureka-server
```
##### 在启动类上增加注解
```
@EnableEurekaServer
```
#### 在项目中新建子模块eureka-client,作为客户端
##### 在eureka-client的pom中加入以下代码
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.wangz</groupId>
        <artifactId>sc-f-step1</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <groupId>com.wangz</groupId>
    <artifactId>eureka-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>eureka-client</name>
    <description>Demo project for Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```
##### 在application.yml中加入以下配置
```
server:
  port: 8762

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka

spring:
  application:
    name: eureka-client
```
##### 在启动类上增加注解
```
@EnableEurekaClient
```
##### 新建controller类进行测试
