# Spring MVC 笔记
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

**配置web.xml**：同“配置开发SpringMVC”

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
