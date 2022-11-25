# 【SSM+VUE】极简玩具DEMO

## 后端基本开发流程

### 0 准备项目框架以及配置依赖

创建好所需要的目录。

![image-20221125145529528](https://tva1.sinaimg.cn/large/008vxvgGgy1h8hdotoxzvj30ba09rwem.jpg)

准备好相关的依赖：

```xml
<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.2.10.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.2.10.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.2.10.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.6</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>1.3.0</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.47</version>
    </dependency>

    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.16</version>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>

    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.9.0</version>
    </dependency>
  </dependencies>
```

### 1 准备数据以及数据库

在 mysql 中执行 sql 脚本：

```mysql
CREATE TABLE books (
id INT NOT NULL auto_increment,
type VARCHAR ( 20 ),
bookname VARCHAR ( 20 ),
description VARCHAR ( 20 ),
PRIMARY KEY ( id ) 
);
INSERT books
VALUES
	( NULL, '游戏类', '德玛西亚', '好玩的城邦' );
INSERT books
VALUES
	( NULL, '游戏类', '约德尔', '可爱的城邦' );
INSERT books
VALUES
	( NULL, '游戏类', '诺克萨斯', '凶狠的城邦' );
INSERT books
VALUES
	( NULL, '计算机类', '设计模式', '讲设计模式的书' );
```

创建简单的 `book` 表。

![image-20221125145612561](https://tva1.sinaimg.cn/large/008vxvgGgy1h8hdpjg3iwj30b003mq2x.jpg)

### 2 定义POJO

在 `domain` 包下，定义 POJO 类。

```java
/**
 * @projectName Demo
 * @package tech.songjian.domain
 * @className tech.songjian.domain.Book
 */
package tech.songjian.domain;

/**
 * Book
 * @description Book POJO类
 * @author SongJian
 * @date 2022/11/25 14:56
 * @version
 */
public class Book {

    private int id;
    private String bookName;
    private String type;
    private String description;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    // setter & getter
}
```

### 3 编写配置

配置文件均写在 `config` 中。本 demo 采用的框架是 `Spring` 、`SpringMVC` 和 `Mybatis`，因此共有：

- `Spring` ：
  - `SpringConfig` 主配置文件
- `SpringMVC` ：
  - `SpringMvcConfig` ：SpringMVC 配置类
  - `ServletConfig` ：web 容器配置。加载 Spring 和 SpringMVC 配置，设置SpringMVC请求拦截路径
- `MyBatis` ：
  - `jdbc.properties` ：JDBC 连接相关配置
  - `MyBatisConfig` ：SqlSessionFactoryBean 和 MapperScannerConfigurer 配置
  - `JdbcConfig` ：数据源配置、平台事务管理配置

`SpringConfig` 主配置文件：

```java
@Configuration
@ComponentScan("tech.songjian.service")
@Import({JdbcConfig.class,MyBatisConfig.class})
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
public class SpringConfig {
}
```

`SpringMvcConfig` 配置

```java
@Configuration
@ComponentScan("tech.songjian.controller")
@EnableWebMvc
public class SpringMvcConfig {
}
```

`jdbc.properties`  JDBC连接相关配置

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=你的url
jdbc.user=你的username
jdbc.password=你的password
```

`JdbcConfig` 配置数据源与平台事务管理

```java
public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    /**
     * @title dataSource
     * @author SongJian
     * @updateTime 2022/11/25 15:59
     * @return: javax.sql.DataSource
     * @throws
     * @description 获取数据源，他的返回值应该被 Spring 管理
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * @title platformTransactionManager
     * @author SongJian
     * @param: dataSource
     * @updateTime 2022/11/25 16:02
     * @return: org.springframework.transaction.PlatformTransactionManager
     * @throws
     * @description 平台事务管理对象，他的返回值应该被 Spring 管理
     */

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
```

`MyBatisConfig` 配置 `SqlSessionFactoryBean` 和 `MapperScannerConfigurer`

```java
public class MyBatisConfig {

    /**
     * @title
     * @author SongJian
     * @updateTime 2022/11/25 16:19
     * @throws
     * @description 定义bean，SqlSessionFactoryBean，用于产生SqlSessionFactory对象
     */

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("tech.songjian.domain");
        return sqlSessionFactoryBean;
    }

    /**
     * @title mapperScannerConfigurer
     * @author SongJian
     * @updateTime 2022/11/25 16:19
     * @return: org.mybatis.spring.mapper.MapperScannerConfigurer
     * @throws
     * @description  定义bean，返回MapperScannerConfigurer对象
     */

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("tech.songjian.dao");
        return mapperScannerConfigurer;
    }
}
```

### 4 编写Dao

这里直接使用注解的方式完成，增删查改的业务。

```java
/**
 * @projectName Demo
 * @package tech.songjian.dao
 * @className tech.songjian.dao.BookDao
 */
package tech.songjian.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tech.songjian.domain.Book;

import java.util.List;

/**
 * BookDao
 * @description
 * @author SongJian
 * @date 2022/11/25 16:34
 * @version
 */

public interface BookDao {

    /**
     * 插入图书
     * @param book
     */
    @Insert("insert into books values (null, #{type}, #{bookName}, #{description})")
    public void save(Book book);

    /**
     * 修改图书信息
     * @param book
     */
    @Update("update books set type=#{type}, bookname=#{bookName}, description=#{description} where id=#{id}")
    public void update(Book book);

    /**
     * 删除图书
     * @param id
     */
    @Delete("delete from books where id=#{id}")
    public void delete(Integer id);

    /**
     * 根据 id 删除
     * @param id
     * @return
     */
    @Select("select * from books where id=#{id}")
    public Book getById(Integer id);

    /**
     * 查找全部图书
     * @return
     */
    @Select("select * from books")
    public List<Book> getAll();
}
```

### 5 编写Service

Service 要写接口和对应的实现类，实现类写在 `/service/impl` 中。

```java
/**
 * @projectName Demo
 * @package tech.songjian.service
 * @className tech.songjian.service.BookService
 */
package tech.songjian.service;

import org.springframework.transaction.annotation.Transactional;
import tech.songjian.domain.Book;

import java.util.List;

/**
 * BookService
 * @description
 * @author SongJian
 * @date 2022/11/25 16:40
 * @version
 */
@Transactional
public interface BookService {

    /**
     * 保存图书
     * @param book
     * @return
     */
    public boolean save(Book book);

    /**
     * 修改图书信息
     * @param book
     * @return
     */
    public boolean update(Book book);

    /**
     * 根据 id 删除
     * @param id
     * @return
     */
    public boolean delete(Integer id);

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    public Book getById(Integer id);

    /**
     * 查询全部
     * @return
     */
    public List<Book> getAll();
}
```

Service 实现类需要使用 Dao 对象，这里记得使用自动装配 `@AutoWired` 。

```java
/**
 * @projectName Demo
 * @package tech.songjian.service.impl
 * @className tech.songjian.service.impl.BookServiceImpl
 */
package tech.songjian.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.songjian.dao.BookDao;
import tech.songjian.domain.Book;
import tech.songjian.service.BookService;

import java.util.List;

/**
 * BookServiceImpl
 * @description
 * @author SongJian
 * @date 2022/11/25 16:42
 * @version
 */

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public boolean save(Book book) {
        bookDao.save(book);
        return true;
    }

    @Override
    public boolean update(Book book) {
        bookDao.update(book);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        bookDao.delete(id);
        return true;
    }

    @Override
    public Book getById(Integer id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
```

### 6 编写Controller

这里要记得写 `@RestController` 注解（Rest风格开发）以及 `@RequestMapping()` 请求地址映射路径。也是使用自动装配获取 Service 对象。

```java
/**
 * @projectName Demo
 * @package tech.songjian.controller
 * @className tech.songjian.controller.BookController
 */
package tech.songjian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.songjian.dao.BookDao;
import tech.songjian.domain.Book;
import tech.songjian.service.BookService;

import java.util.List;

/**
 * BookController
 *
 * @author SongJian
 * @description
 * @date 2022/11/25 16:44
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public boolean save(@RequestBody  Book book) {
        return bookService.save(book);
    }

    @PutMapping
    public boolean update(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return bookService.delete(id);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Integer id) {
        return bookService.getById(id);
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }
}
```

### 7 编写测试类进行业务层测试

![image-20221125204424898](https://tva1.sinaimg.cn/large/008vxvgGgy1h8hnrvwnqbj30ag02vq2s.jpg)

记得加上注解 `@RunWith(SpringJUnit4ClassRunner.class)` 

> 该注解让类运行在 Spring 的测试环境，以便测试开始时自动创建Spring应用上下文，并使用JUnit4测试工具运行测试。

还有注解 `@ContextConfiguration(classes=SpringConfig.class)`

> 当我们想要在某个测试类使用 `@Autowired` 注解来引入这些收集起来的bean时，只需要给这个测试类添加 `@ContextConfiguration` 注解来标注我们想要导入这个测试类的某些bean。

```java
/**
 * @projectName Demo
 * @package tech.songjian
 * @className tech.songjian.BookServiceTest
 */
package tech.songjian;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.songjian.config.SpringConfig;
import tech.songjian.domain.Book;
import tech.songjian.service.BookService;

import java.util.List;

/**
 * BookServiceTest
 * @description
 * @author SongJian
 * @date 2022/11/25 19:29
 * @version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testGetById() {
        Book book = bookService.getById(1);
        System.out.println(book);
    }

    @Test
    public void testGetAll() {
        List<Book> books = bookService.getAll();
        System.out.println(books);
    }

    @Test
    public void testSave() {
        Book book = new Book("计算机","人工智能通识","好书");
        System.out.println(bookService.save(book));

    }
}
```

我们再打开 Tomcat，使用 Postman 测测，发现没啥问题。

![image-20221125211933288](https://tva1.sinaimg.cn/large/008vxvgGgy1h8hosf2n6uj310r0pa76a.jpg)

那到目前为止，基本功能（CRUD）都实现完全了。接下来封装一下表现层与前端的传输协议。

### 8 封装Result类传输数据

> 将数据封装成统一格式

在 `controller` 包下，创建 `Result` 类来对给前端返回的数据进行封装。

```java
/**
 * @projectName Demo
 * @package tech.songjian.controller
 * @className tech.songjian.controller.Result
 */
package tech.songjian.controller;

/**
 * Result
 * @description
 * @author SongJian
 * @date 2022/11/25 21:25
 * @version
 */
public class Result {
    /**
     * 要封装的数据
     */
    private Object data;

    /**
     * 自定义的状态码，给前端返回
     */
    private Integer code;

    /**
     * 自定义的消息
     */
    private String msg;

    public Result(Integer code, Object data, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, Object data) {
        this.data = data;
        this.code = code;
    }

    public Result() {}

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
```

然后我们需要对 Result 中的码值进行定义，也是写在 controller 包下。

```java
/**
 * @projectName Demo
 * @package tech.songjian.controller
 * @className tech.songjian.controller.Code
 */
package tech.songjian.controller;

/**
 * Code
 * @description
 * @author SongJian
 * @date 2022/11/25 21:28
 * @version
 */
public class Code {
    public static final Integer SAVE_OK = 20011;
    public static final Integer DELETE_OK = 20021;
    public static final Integer UPDATE_OK = 20031;
    public static final Integer GET_OK = 20041;

    public static final Integer SAVE_ERR = 20010;
    public static final Integer DELETE_ERR = 20020;
    public static final Integer UPDATE_ERR = 20030;
    public static final Integer GET_ERR = 20040;
}
```

那么现在，我们需要重新调整 controller 中的返回值，设定为 Result 类型。

```java
/**
 * @projectName Demo
 * @package tech.songjian.controller
 * @className tech.songjian.controller.BookController
 */
package tech.songjian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.songjian.dao.BookDao;
import tech.songjian.domain.Book;
import tech.songjian.service.BookService;

import java.util.List;

/**
 * BookController
 *
 * @author SongJian
 * @description
 * @date 2022/11/25 16:44
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Result save(@RequestBody  Book book) {
        boolean flag = bookService.save(book);
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
    }

    @PutMapping
    public Result update(@RequestBody Book book) {
        boolean flag = bookService.update(book);
        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = bookService.delete(id);
        return new Result(flag ? Code.DELETE_OK : Code.DELETE_ERR, flag);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        Integer code = book != null ? Code.GET_OK : Code.GET_ERR;
        String msg = book != null ? "" : "数据查询失败，请重试";
        return new Result(code, book, msg);
    }

    @GetMapping
    public Result getAll() {
        List<Book> books = bookService.getAll();
        Integer code = books != null ? Code.GET_OK : Code.GET_ERR;
        String msg = books != null ? "" : "数据查询失败，请重试";
        return new Result(code, books, msg);
    }
}
```

于是：

![image-20221125215143848](https://tva1.sinaimg.cn/large/008vxvgGgy1h8hppyox4ij31140hnt9y.jpg)

![image-20221125215157352](https://tva1.sinaimg.cn/large/008vxvgGgy1h8hpq4i7ohj311j0jl3zw.jpg)

### 9 添加异常处理

异常处理类放在表现层统一处理，使用 AOP 的思想。在 controller 包下创建 Handler

```java
/**
 * @projectName Demo
 * @package tech.songjian.controller
 * @className tech.songjian.controller.ProjectExceptionAdvice
 */
package tech.songjian.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.songjian.exception.BizzException;
import tech.songjian.exception.SystemException;

/**
 * ProjectExceptionAdvice
 * @description 异常处理
 * @author SongJian
 * @date 2022/11/25 22:02
 * @version
 */
//@RestControllerAdvice用于标识当前类为REST风格对应的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {
  
    //@ExceptionHandler用于设置当前处理器类对应的异常类型
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        return new Result(ex.getCode(),null,ex.getMessage());
    }

    @ExceptionHandler(BizzException.class)
    public Result doBusinessException(BizzException ex){
        return new Result(ex.getCode(),null,ex.getMessage());
    }

    //除了自定义的异常处理器，保留对Exception类型的异常处理，用于处理非预期的异常
    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        return new Result(Code.SYSTEM_UNKNOW_ERR,null,"系统繁忙，请稍后再试！");
    }
}
```

### 10 自定义业务异常与系统异常

异常分类，即 `bizzErr` 和 `systemErr` 。新建包 `exception` 创建这两个类：

```java
/**
 * @projectName Demo
 * @package tech.songjian.exception
 * @className tech.songjian.exception.SystemException
 */
package tech.songjian.exception;

/**
 * SystemException
 * @description 系统异常
 * @author SongJian
 * @date 2022/11/25 22:28
 * @version
 */
public class SystemException extends RuntimeException{

    /**
     * 异常号
      */
    private Integer code;

    public SystemException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public SystemException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
```

以及，

```java
/**
 * @projectName Demo
 * @package tech.songjian.exception
 * @className tech.songjian.exception.BizzException
 */
package tech.songjian.exception;

/**
 * BizzException
 * @description 业务异常
 * @author SongJian
 * @date 2022/11/25 22:31
 * @version
 */
public class BizzException extends RuntimeException{
    /**
     * 异常号
     */
    private Integer code;

    public BizzException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public BizzException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
```

## 前端页面与后端整合

### 1 添加配置放行静态资源

```java
/**
 * @projectName Demo
 * @package tech.songjian.config
 * @className tech.songjian.config.SpringMvcSupport
 */
package tech.songjian.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * SpringMvcSupport
 * @description
 * @author SongJian
 * @date 2022/11/25 22:17
 * @version
 */
@Configuration
public class SpringMvcSupport extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/");
    }
}
```

### 2 在前端页面中编写Vue代码

使用 axios 发送异步请求，请求对应后端资源路径。

```javascript
<script>
    var vue = new Vue({

        el: '#app',
        data: {
            pagination: {},
            dataList: [],//当前页要展示的列表数据
            formData: {},//表单数据
            dialogFormVisible: false,//控制表单是否可见
            dialogFormVisible4Edit: false,//编辑表单是否可见
            rules: {//校验规则
                type: [{required: true, message: '图书类别为必填项', trigger: 'blur'}],
                name: [{required: true, message: '图书名称为必填项', trigger: 'blur'}]
            }
        },

        //钩子函数，VUE对象初始化完成后自动执行
        created() {
            this.getAll();
        },

        methods: {
            //列表
            getAll() {
                //发送ajax请求
                axios.get("/books").then((res) => {
                    this.dataList = res.data.data;
                });
            },

            //弹出添加窗口
            handleCreate() {
                this.dialogFormVisible = true;
                this.resetForm();
            },

            //重置表单
            resetForm() {
                this.formData = {};
            },

            //添加
            handleAdd() {
                //发送ajax请求
                axios.post("/books", this.formData).then((res) => {
                    console.log(res.data);
                    //如果操作成功，关闭弹层，显示数据
                    if (res.data.code == 20011) {
                        this.dialogFormVisible = false;
                        this.$message.success("添加成功");
                    } else if (res.data.code == 20010) {
                        this.$message.error("添加失败");
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }).finally(() => {
                    this.getAll();
                });
            },

            //弹出编辑窗口
            handleUpdate(row) {
                // console.log(row);   //row.id 查询条件
                //查询数据，根据id查询
                axios.get("/books/" + row.id).then((res) => {
                    // console.log(res.data.data);
                    if (res.data.code == 20041) {
                        //展示弹层，加载数据
                        this.formData = res.data.data;
                        this.dialogFormVisible4Edit = true;
                    } else {
                        this.$message.error(res.data.msg);
                    }
                });
            },

            //编辑
            handleEdit() {
                //发送ajax请求
                axios.put("/books", this.formData).then((res) => {
                    //如果操作成功，关闭弹层，显示数据
                    if (res.data.code == 20031) {
                        this.dialogFormVisible4Edit = false;
                        this.$message.success("修改成功");
                    } else if (res.data.code == 20030) {
                        this.$message.error("修改失败");
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }).finally(() => {
                    this.getAll();
                });
            },

            // 删除
            handleDelete(row) {
                //1.弹出提示框
                this.$confirm("此操作永久删除当前数据，是否继续？", "提示", {
                    type: 'info'
                }).then(() => {
                    //2.做删除业务
                    axios.delete("/books/" + row.id).then((res) => {
                        if (res.data.code == 20021) {
                            this.$message.success("删除成功");
                        } else {
                            this.$message.error("删除失败");
                        }
                    }).finally(() => {
                        this.getAll();
                    });
                }).catch(() => {
                    //3.取消删除
                    this.$message.info("取消删除操作");
                });
            }
        }
    })
</script>
```

## 效果显示

![image-20221125230726997](https://tva1.sinaimg.cn/large/008vxvgGgy1h8hrwqb85gj31eg0o3goa.jpg)

![image-20221125231031817](https://tva1.sinaimg.cn/large/008vxvgGgy1h8hrzvstraj30r70b374t.jpg)





