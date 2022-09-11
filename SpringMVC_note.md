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
    
    > **视图解析器**：
    >
    > 1. 获取了ModelAndView的数据。
    >
    > 2. 解析ModelAndView的视图名字。
    >
    > 3. 拼接视图名，找到对应的视图。
    
    
    
10. 视图解析器将解析的逻辑视图名传给DispatcherServlet。

11. DispatcherServlet根据视图解析器解析的视图结果，调用具体的视图。

12. 最终视图呈现给用户。

