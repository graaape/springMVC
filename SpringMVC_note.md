﻿﻿﻿﻿﻿# Spring MVC 笔记
## 什么是MVC
- MVC是模型（Model）、视图（View）、控制器（Controller）的简写，是一种软件设计规范。
	- **Model（模型）**：数据模型，提供要展示的数据，因此包含数据和行为，可以认为是领域模型或JavaBean组件（包含数据和行为），不过现在一般都分离开来：Value Object（数据Dao） 和 服务层（行为Service）。也就是模型提供了模型数据查询和模型数据的状态更新等功能，包括数据和业务。

	- **View（视图）**：负责进行模型的展示，一般就是我们见到的用户界面，客户想看到的东西。

	 - **Controller（控制器）**：接收用户请求，委托给模型进行处理（状态改变），处理完毕后把返回的模型数据返回给视图，由视图负责展示。也就是说控制器做了个调度员的工作。
- MVC是一种架构模式。
### MVC框架需要做的事：
1.  将url映射到java类或java类的方法。
    
2.  封装用户提交的数据。
    
3.  处理请求--调用相关的业务处理--封装响应数据。
    
4.  将响应的数据进行渲染 . jsp / html 等表示层数据。
## SpringMVC执行流程
![SpringMVC执行流程图](https://s1.ax1x.com/2022/09/11/vO70iT.jpg)
1.  DispatcherServlet表示前置控制器，是整个SpringMVC的控制中心。用户发出请求，DispatcherServlet接收请求并拦截请求。
    
	>假设请求的url为 : http://localhost:8080/SpringMVC/hello
	>
    >**如上url拆分成三部分：**
    >
    >服务器域名：http://localhost:8080
    >
	>  部署在服务器上的web站点 ：SpringMVC
    >
    >控制器：hello
    >
    >则如上url可表示为：请求位于服务器localhost:8080上的SpringMVC站点的hello控制器。
    
2.  HandlerMapping为处理器映射。DispatcherServlet调用HandlerMapping，HandlerMapping根据请求url查找Handler。
    
3.  HandlerExecution表示具体的Handler，其主要作用是根据url查找控制器。如上url被查找控制器为：hello。
    
4.  HandlerExecution将解析后的信息传递给DispatcherServlet，如解析控制器映射等。
    
5.  HandlerAdapter表示处理器适配器，其按照特定的规则去执行Handler。
    
6.  Handler让具体的Controller执行。
    
7.  Controller将具体的执行信息返回给HandlerAdapter,如ModelAndView。
    
8.  HandlerAdapter将视图逻辑名或模型传递给DispatcherServlet。
    
9.  DispatcherServlet调用视图解析器(ViewResolver)来解析HandlerAdapter传递的逻辑视图名。
    
    > ##### 视图解析器：
    >
    > 1. 获取了ModelAndView的数据。
    >
    > 2. 解析ModelAndView的视图名字。
    >
    > 3. 拼接视图名，找到对应的视图。
    
    
    
10. 视图解析器将解析的逻辑视图名传给DispatcherServlet。

11. DispatcherServlet根据视图解析器解析的视图结果，调用具体的视图。

12. 最终视图呈现给用户。

## 配置开发SpringMVC

**配置web.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
<!--   配置DispatcherServlet：SpringMVC的核心-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<!--        要绑定SpringMVC的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
<!--        启动级别：1-->
        <load-on-startup>1</load-on-startup>
    </servlet>
<!--
在SpringMVC中， /和/*的区别
/：只匹配所有请求，不会去匹配jsp页面
/*：匹配所有请求，包括jsp页面
-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

**SpringMVC的配置文件：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
<!--    映射器-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
<!--    适配器-->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>
    <!--视图解析器:DispatcherServlet给他的ModelAndView-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--Handler-->
    <bean id="/hello" class="com.kuang.controller.HelloController"/>
</beans>
```

**Controller类：**

```java
//注意：这里先导入Controller接口
public class HelloController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView 模型和视图
        ModelAndView mv = new ModelAndView();

        //封装对象，放在ModelAndView中。Model
        String result="HelloSpringMVC!";
        mv.addObject("msg",result);
        //封装要跳转的视图，放在ModelAndView中
//        mv.setViewName("hello");
        mv.setViewName("test"); //: /WEB-INF/jsp/hello.jsp
        return mv;
    }

}
```

## 注解开发SpringMVC

**配置web.xml：**同“配置开发SpringMVC”

**SpringMVC的配置文件：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 自动扫描包，让指定包下的注解生效,由IOC容器统一管理 -->
    <context:component-scan base-package="com.kuang.controller"/>
    <!-- 让Spring MVC不处理静态资源 -->
    <mvc:default-servlet-handler />
    <!--
    支持mvc注解驱动
        在spring中一般采用@RequestMapping注解来完成映射关系
        要想使@RequestMapping注解生效
        必须向上下文中注册DefaultAnnotationHandlerMapping
        和一个AnnotationMethodHandlerAdapter实例
        这两个实例分别在类级别和方法级别处理。
        而annotation-driven配置帮助我们自动完成上述两个实例的注入。
     -->
    <mvc:annotation-driven />

    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id="internalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

**Controller类：**

```java
@Controller
public class HelloController{
    @RequestMapping("/h1")
    public String hello(Model model){
        model.addAttribute("msg","HelloSpringMVCAnnotation!!");
        return "hello";//被视图解释器处理
    }
}
```

### @Controller

```java
@Controller//代表这个类会被Spring接管，被@Controller所注解的类中的所有方法，如果返回值为String，且有具体的页面可跳转，那么就会被视图解析器解析
public class ControllerTest2 {
//    视图可以复用
    @RequestMapping("/t2")
    public String test2(Model model){
        model.addAttribute("msg","Hello Controller Test2!!");
        return "test";
    }
    @RequestMapping("/t3")
    public String test3(Model model){
        model.addAttribute("msg","Test3！！！");
        return "test";
    }
}
```

### @RequestMapping

同时注解类和方法：

```java
@Controller
@RequestMapping("/c3")
public class ControllerTest3 {
    @RequestMapping("/t1")
    public String test1(Model model){
        model.addAttribute("msg","ResquestMappingTest!!!");
        return "test";
    }
}
```

访问路径：localhost:8080/项目名/c3/h1

## RESTful 风格

RESTful风格是一个资源定位及资源操作的风格。

使用@PathVariable注解，让方法参数对应绑定到一个URI模板变量上。

```java
@RequestMapping("/add2/{a}/{b}")
    public String test2(@PathVariable int a,@PathVariable int b, Model model){
        int res=a+b;
        model.addAttribute("msg","结果为"+res);
        return "test";
    }
```

方法级别的注解变体如下：

```java
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PatchMapping
```

## 转发和重定向

### 不使用视图解析器

```java
@RequestMapping("/m1/t1")
public String test1(Model model){
    model.addAttribute("msg","ModelTest1");
    //转发
    return "/WEB-INF/jsp/test.jsp";
}

@RequestMapping("/m1/t2")
public String test2(Model model){
    model.addAttribute("msg","ModelTest1");
    //转发
    return "forward:/WEB-INF/jsp/test.jsp";
}

@RequestMapping("/m1/t3")
public String test3(Model model){
     model.addAttribute("msg","ModelTest1");
     //重定向
     return "redirect:/index.jsp";
 }
```

### 使用视图解析器

```java
@RequestMapping("/h1")
public String hello(Model model){
    model.addAttribute("msg","HelloSpringMVCAnnotation!!");
    //转发
    return "hello";//被视图解释器处理
}
@RequestMapping("/m1/t3")
public String test3(Model model){
    model.addAttribute("msg","ModelTest1");
    //重定向
    return "redirect:/index.jsp";
}
```

## 接收请求参数及数据回显

### 处理提交数据

#### 提交的域名称和处理方法的参数名一致

提交：http://localhost:8080/user/t1?name=xiaoming

处理方法：

```java
@RequestMapping("/t1")
    public String test1(String name, Model model){
//        1.接收前端参数
        System.out.println("接收到前端的参数为："+name);
//        2.将返回的结果传递给前端
        model.addAttribute("msg",name);
//        3.视图跳转
        return "test";
    }
```

#### 提交的域名称和处理方法的参数名不一致

提交：http://localhost:8080/user/t1?username=xiaoming

处理方法：

```java
 @RequestMapping("/t1")
    public String test1(@RequestParam("username") String name, Model model){
//        1.接收前端参数
        System.out.println("接收到前端的参数为："+name);
//        2.将返回的结果传递给前端
        model.addAttribute("msg",name);
//        3.视图跳转
        return "test";
    }
```

#### 提交一个对象

要求提交的表单域和对象的属性名一致  , 参数使用对象即可。

实体类：

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
}
```

处理方法：

```java
//    前端接收的是一个对象
    @RequestMapping("/t2")
    public String test2(User user){
        System.out.println(user);
        return "test";
    }
```

提交：http://localhost:8080/user/t2?name=xiaohong&id=123&age=12

输出：User(id=123, name=xiaohong, age=12)

**注意**：前端传递的参数名需要和对象中的属性名一致，否则返回默认值。

比如，提交：...t2?**name1**=xiaohong&**id1**=123&age=12

​			输出：User(id=0, name=null, age=12)

### 数据显示到前端

#### ModelMap

继承了 LinkedMap ，除了实现了自身的一些方法，同时继承 LinkedMap 的方法和特性。

```java
@RequestMapping("/t3")
public String test3(@RequestParam("username") String name,ModelMap map){
    map.addAttribute("name",name);
    System.out.println(name);
    return "test";
}
```

#### Model

简化了对Model对象的操作。

```java
@Controller
public class HelloController{
    @RequestMapping("/h1")
    public String hello(Model model){
        model.addAttribute("msg","HelloSpringMVCAnnotation!!");
        return "hello";//被视图解释器处理
    }
}
```

#### ModelAndView

ModelAndView 可以在储存数据的同时，可以进行设置返回的逻辑视图，进行控制展示层的跳转。

```java
public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView 模型和视图
        ModelAndView mv = new ModelAndView();

        //封装对象，放在ModelAndView中。Model
        String result="HelloSpringMVC!";
        mv.addObject("msg",result);
        //封装要跳转的视图，放在ModelAndView中
//        mv.setViewName("hello");
        mv.setViewName("test"); //: /WEB-INF/jsp/hello.jsp
        return mv;
    }
```

## 使用过滤器解决乱码问题

在web.xml配置：

```xml
<filter>
    <filter-name>encoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>encoding</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

## JSON

### 什么是JSON

- JSON(JavaScript Object Notation, JS 对象标记) 是一种轻量级的数据交换格式。
- 采用完全独立于编程语言的**文本格式**来存储和表示数据。

### 使用Jackson解析json

```java
@Controller
public class UserController {
    @RequestMapping("/j1")
    @ResponseBody//不会走视图解析器，会直接返回一个字符串
    public String json1() throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        User user=new User("小明",3,"男");
        String str = mapper.writeValueAsString(user);
        return str;
    }
}
```

使用@RestController注解就不需要再加@ResponseBody注解。

#### 解决json乱码问题

```xml
<!--    解决json乱码问题配置-->
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```

#### 编写JsonUtil工具类

可以省去重复代码。

```java
public class JsonUtils {
    public static String getJson(Object obj){
        return getJson(obj,"yyyy-MM-dd HH:mm:ss");
    }
    public static String getJson(Object obj,String dateFormat){
        ObjectMapper mapper=new ObjectMapper();
        //使用ObjectMapper格式化输出，关闭时间戳
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //自定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        mapper.setDateFormat(sdf);
        //ObjectMapper时间解析的默认格式为Timestamp 时间戳
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

### 使用fastjson解析json

```java
@RequestMapping("/j4")
@ResponseBody
public String json4(){
    List<User> users = new ArrayList<User>();
    User user1=new User("小明1",3,"男");
    User user2=new User("小明2",3,"男");
    User user3=new User("小明3",3,"男");
    User user4=new User("小明4",3,"男");
    users.add(user1);
    users.add(user2);
    users.add(user3);
    users.add(user4);
    String str = JSON.toJSONString(users);
    return str;
}
```

## SSM整合

