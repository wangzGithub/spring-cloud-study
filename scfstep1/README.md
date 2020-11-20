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
---
### 增加ribbon负载均衡
#### 新建模块，service-ribbon,在pom中增加以下代码，并在根pom中加入引用
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.wangz</groupId>
    <artifactId>service-ribbon</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>service-ribbon</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.wangz</groupId>
        <artifactId>sc-f-step1</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
    </dependencies>

</project>
```
#### 在application.yml中加入配置
```
eureka:
    client:
        service-url:
            defaultZone: http://localhost:8761/eureka/
server:
    port: 8764
spring:
    application:
        name: service-ribbon
```
#### 在启动类中通过EnableDiscoveryClient向注册中心注册，并向程序的ioc中注入bean RestTemplate, 通过LoadBalanced注解表明通过RestTemplate开启负载均衡
```java
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoverClient
public class ServiceRibbonApplication {
    public static void main(String[] args) {
        SpringApplication.run( ServiceRibbonApplication.class, args );
    }
    
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```
#### 写一个测试服务，通过RestTemplate来消费eureka-client中的测试接口，这里使用服务名选择实例
```java
@Service
public class TestService {
    @Autowired
    private RestTemplate restTemplate;
    
    public String testService(String name) {
        return restTemplate.getForObject("http://EUREKA-CLIENT/hello?name=" + name, String.class);
    }
}
```
#### 写一个controller,在Controller中调用Service中的方法
```java
@RestController
public class TestController {
    @Autowired
    TestService testService;
    
    @GetMapping(value = "hello")
    public String hello(@RequestParam String name) {
        return testService.testService(name);
    }
}
```
#### 测试步骤
##### 启动eureka-server
##### 启用eureka-client,运行之后将eureka-client中的端口修改为8763(原端口为8762),再次运行一个eureka-client实例
> idea 在application运行配置中取消勾选Single instance only可以让一个实例以不同端口多次运行
##### 运行service-ribbon实例
##### 访问http://localhost:8764/hello?name=123 可以看到下列信息交替出现：
> Hello, the name you press is: lei, this message from :eureka-client, port is: 8762
---
> Hello, the name you press is: lei, this message from :eureka-client, port is: 8763
---
> 即表示当前已经通过调用service-ribbon的restTemplate.getForObject()方法实现了负载均衡，访问了不同端口的实例
