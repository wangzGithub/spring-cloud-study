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
---
### 增加feign功能，RestTemplate+Ribbon和Feign可以实现同样功能
#### 简介
> Feign是一个声明式伪Http客户端，它使写Http客户端更加简单，使用Feign，只需要创建一个接口并注解。
> 具有可插拔的注解特性，可使用Feign注解和JAX-RS注解。
> Feign 支持可插拔的编码器和解码器。
> Feign 默认集成了Ribbon，与Eureka结合默认实现了负载均衡的效果
#### 创建feign模块service-feign,并在pom中引入依赖
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.wangz</groupId>
    <artifactId>service-feign</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>service-feign</name>
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
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
    </dependencies>

</project>
```
##### 在根pom中引入service-feign
#### 在application.yml中增加配置
```
server:
    port: 8765
spring:
    application:
        name: service-feign
eureka:
    client:
        service-url:
            defaultZone: http://localhost:8761/eureka/
```
#### 在程序的启动类增加配置开启Feign功能
```java
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceFeignApplication.class, args);
    }
}
```
#### 定义一个Feign接口，通过@FeignClient(service name) 来指定调用的服务
```java
@FeignClient(value="service-client")
public interface SchedualService {
    @RequestMapping(value="/hello", method=RequestMethod.GET)
    String sayHelloFromClientOne(@RequestParam(value = "name") String name);
}
```
#### 定义一个Controller，对外暴露一个sayHello接口，通过上一步定义的FeignService来消费服务
```java
@RestController
public class HelloController {
    @Autowired
    private SchedualService schedualService;
    
    @GetMapping(value="hello")
    public String sayHello(@RequestParam String name) {
        return schedualService.sayHelloFromClientOne(name);
    }
}
```
#### 依次启动eureka-service,以不同端口号启动两次eureka-client,再启动service-feign
> 多次访问http://localhost:8765/hello?name=123
>> 可以看到返回的端口号发生变化，实现了负载均衡的效果
### 为服务增加断路器功能
> Hystrix断路器会在调用服务发现服务不可用后，fallback返回一个固定值避免连锁故障，更好的控制容器的线程阻塞
#### 在ribbon中增加断路器
##### 在配置文件中引入断路器Hystrix
```
<dependency>
    <groupId>org.springframework.cloud<groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```
##### 在启动类中开启Hystrix
```
@EnableHystrix
```
##### 改造方法，在方法上加入@HystrixCommand注解
> @HystrixCommand方法创建了熔断器功能，并指定了fallback方法，在发生错误时调用此方法
```
@HystrixCommand(fallbackMethod = "errorReturn")
public String testService(String name) {
    return restTemplate.getForObject("http://EUREKA-CLIENT/hello?name=" + name, String.class);
}

public String errorReturn(String name) {
        return "sorry, " + name + " ,there is a error message!";
    }
```
##### 启动服务测试
> 依次启动eureka-server, eureka-client, service-ribbon，访问http://localhost:8764/hello?name=a,返回正常结果，
关闭eureka-client后再次访问返回"sorry, a ,there is a error message!"表示断路器正常工作
#### 在feign中开启断路器
> feign 自带Hystrix但默认关闭，需要在配置文件中手动开启断路器功能
```
feign:
    hystrix:
        enabled: true
```
##### 在service接口的注释中加入fallback的指定类，此类必须实现service接口并注入到IOC容器
```java
@FeignClient(value = "eureka-client", fallback = SchedualServiceHystrix.class)
public interface SchedualService {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String sayHelloFromClientOne(@RequestParam(value = "name") String name);
}
```
```java
@Component
public class SchedualServiceHystrix implements SchedualService {
    @Override
    public String sayHelloFromClientOne(String name) {
        return "sorry, " + name + ", this client has error, the message send by feign hystrix";
    }
}
```
##### 启动服务测试
> 依次启动eureka-server, eureka-cient, service-feign，访问http://localhost:8765/hello?name=a，返回正常结果，
关闭eureka-client后再次访问返回"sorry, a, this client has error, the message send by feign hystrix"表示断路器正常工作
### 增加Zuul组件
> Zuul组件主要功能是路由转发和过滤器，默认和Ribbon结合实现负载均衡
#### 新建service-zuul项目，在pom中加入如下代码并将项目引入根pom
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.wangz</groupId>
    <artifactId>service-zuul</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    
    <name>service-zuul</name>
    <description>Zuul Demo for Spring Cloud Project</description>
    
    <parent>
        <groupId>com.wangz<groupId>
        <artifactId>sc-f-step1</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </denpendency>
        <denpendency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </denpendency>
        <denpendency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </denpendency>
    </denpendencies>
</project>
```
#### 在启动类上加入Zuul注解开启Zuul功能
```java
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class ServiceZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceZuulApplication.class, args);
    }
} 
```
#### 在配置文件中加入如下代码
```
server:
    port: 8766
spring:
    application:
        name: service-zuul
eureka:
    client:
        service-url:
            defaultZone: http://localhost:8761/eureka/
zuul:
    routes:
        api-a:
            path: /api-a/**
            serviceId: service-ribbon
        api-b:
            path: /api-b/**
            serviceId: service-feign
```
> 首先指定服务注册中心地址为http://localhost:8761/eureka,服务端口为8766，服务名为service-zuul,以api-a开头的请求
转发给service-ribbon服务，以api-b开头的请求转发给service-feign服务。
>> 依次运行五个服务，在浏览器中分别访问http://localhost:8766/api-a/hello?name=a,
http://localhost:8766/api-b/hello?name=a,通过结果看到zuul服务起了作用
#### 使用zuul的过滤功能
##### 修改service-zuul服务，增加继承ZuulFilter的过滤类并注入IOC容器
```java
@Component
public class MyFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 0;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("%s>>>%s", request.getMethod(), request.getRequestURL().toString());
        String token = request.getParamter("token");
        if (token == null) {
            logger.warn("tokan is null");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try{
                ctx.getResponse().getWriter().write("token is null");
            } catch (Exception e) {
            }
            return null;
        }
        logger.info("OK");
        return null;
    }
}
```
> filterType
>> 返回一个字符串代表过滤器的类型，zuul中定义了4种不同生命周期的过滤器类型
>>> pre: 路由之前;routing: 路由之时;post: 路由之后;error: 发送错误调用
---
> filterOrder 过滤的顺序
---
> shouldFilter 写逻辑判断，时候要过滤，true为永远过滤
---
> run 具体的逻辑，此处根据token判断时候允许访问
##### 启动项目测试
> 访问http://localhost:8766/api-a/hello?name=a,返回“token is null”,
访问http://localhost:8766/api-a/hello?name=a&token=1,可以得到正常返回结果，表示过滤器生效。