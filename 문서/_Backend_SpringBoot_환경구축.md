# Backend-Setting

 \* 이력사항
 \* 2020. 02. 14. 황란아 최초작성



## 1. SpringBoot 프로젝트 STS 개발 환경 세팅

1. STS(Spring Tool Suite)에서 Spring Starter Project 생성

   <img src="C:\Users\multicampus\Desktop\WRA\칼무리\K-20200114-553187.png" style="zoom:75%;" />

   ​	

   	※ Name & Artifact, Group & Package 맞춰주기!

   <img src="C:\Users\multicampus\Desktop\WRA\칼무리\K-20200114-556436.png" style="zoom:70%;" />

   ​	

   ==※ 다섯가지 Dependencies 넣어주기!==

   <img src="C:\Users\multicampus\Desktop\WRA\칼무리\K-20200114-556639.png" style="zoom:70%;" />

   

2. pom.xml 수정

   unknown error 발생시 아래 maven-jar-plugin.version 태그를 properties에 넣어주자!

   ```
   <properties>
   		<java.version>1.8</java.version>
   		<maven-jar-plugin.version>3.1.1</maven-jar-plugin.version>
   </properties>
   ```

   

   unknown error 발생시 아래 dependency를 dependencies에 넣어주자!

   ```
   <dependency>
       		<groupId>io.springfox</groupId>
       		<artifactId>springfox-swagger-ui</artifactId>
       		<version>2.9.2</version>
   </dependency>
   
   <dependency>
   		    <groupId>io.springfox</groupId>
   		    <artifactId>springfox-swagger2</artifactId>
   		    <version>2.9.2</version>
   </dependency>
   
   <dependency>
   			<groupId>commons-dbcp</groupId>
   			<artifactId>commons-dbcp</artifactId>
   			<version>1.4</version>
   </dependency>
   
   <dependency>
               <groupId>com.fasterxml.jackson.core</groupId>
               <artifactId>jackson-databind</artifactId>
               <version>${jackson.version}</version>
   </dependency>
   ```

   

3. 프로젝트 우클릭 -> Maven -> Update Project

   <img src="C:\Users\multicampus\Desktop\WRA\칼무리\K-20200114-566400.png" style="zoom:75%;" />



## 2. SpringBoot에서 Rest API CRUD

cf) REST API란? https://www.edwith.org/boostcourse-web/lecture/16740/

1. src/main/resources -> application.properties

   ※ spring.datasource.url을 통해 DB 연결 설정

   ※ mybatis.config-location을 통해 SqlMapConfig 경로 설정

   ```
   spring.mvc.view.prefix=/
   spring.mvc.view.suffix=.jsp
   
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://70.12.247.81:3306/ssafysns?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
   spring.datasource.username=ssafy
   spring.datasource.password=ssafy
   spring.datasource.type=org.apache.commons.dbcp.BasicDataSource
   
   mybatis.config-location=classpath:/spring/SqlMapConfig.xml
   
   ```

   

2. src/main/resources -> spring 폴더 생성 -> SqlMapConfig.xml 생성

   - typeAlias에 DTO 경로와 alias 설정
   - mapper에 query.xml 경로 설정

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   
   <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" 
   	"http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
   	<typeAliases>
   		<typeAlias type="com.ssafy.model.dto.User" alias="user" />
   	</typeAliases>
   	<mappers>
   		<mapper resource="spring/user_query.xml" />
   	</mappers>
   </configuration>
   ```

   

3. query.xml 생성

   - mapper 태그에서 namespace를 통해 DAO 경로 설정
   - mybatis로 query 작성

   ```
   <?xml version="1.0" encoding="UTF-8" ?>
   
   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
   	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   
   <mapper namespace="com.ssafy.model.dao.UserDAO">
   
   	<select id="searchAll" resultType="user">
   		select * from user
   	</select>
   	
   	<insert id="insert" parameterType="user">
   		insert into user (id)
   		values(#{id:VARCHAR})
   	</insert>
   
   </mapper>
   ```

   

4. 패키지 맞춰서 DTO 생성

   - 속성값 정의

   - 기본 생성자 & using field 생성자 만들기

   - getter & setter 필수

     

5. 패키지 맞춰서 DAO 생성

   ※ @Mapper 태그!!!

   ```
   package com.ssafy.model.dao;import java.util.List;import org.apache.ibatis.annotations.Mapper;import com.ssafy.model.dto.User;@Mapperpublic interface UserDAO {    public List<User> searchAll();    public void insert(User user);}
   ```

   

6. 패키지 맞춰서 Service Interface와 ServiceImpl 생성

   ※ @Service 태그!!!

   ※ @Autowired 태그!!!

   ```
   package com.ssafy.service;
   
   import java.util.List;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   import com.ssafy.model.dao.UserDAO;
   import com.ssafy.model.dto.User;
   import com.ssafy.model.dto.UserException;
   
   @Service
   public class UserServiceImpl implements UserService {
   	@Autowired
   	private UserDAO dao;
   
   	public List<User> searchAll() {
   		return dao.searchAll();
   
   	}
   
   	public void insert(User user) {
   		dao.insert(user);
   	}
   
   }
   
   ```

   

7. 패키지 맞춰서 RestController 생성

   ```
   package com.ssafy.controller;
   
   import java.util.HashMap;
   import java.util.Map;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.http.HttpStatus;
   import org.springframework.http.ResponseEntity;
   import org.springframework.web.bind.annotation.CrossOrigin;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.PostMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   import com.ssafy.model.dto.User;
   import com.ssafy.service.UserService;
   
   import io.swagger.annotations.ApiOperation;
   
   @CrossOrigin(origins = { "*" }, maxAge = 6000)
   @RestController
   public class UserController {
   
   	@Autowired
   	private UserService userService;
   
   	// 모든 User 조회
   	@ApiOperation(value = "모든 User 목록 조회")
   	@GetMapping("/searchAllUser")
   	public ResponseEntity<Map<String, Object>> SearchAllQnA() throws Exception {
   		return handleSuccess(userService.searchAll());
   	}
   
   	@ApiOperation(value = "User 정보 등록")
   	@PostMapping("/insertUser/{user}")
   	public ResponseEntity<Map<String, Object>> insertIntake(User user) {
   		userService.insert(user);
   		return handleSuccess("User 등록 완료");
   	}
   
   	public ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus state) {
   		Map<String, Object> resultMap = new HashMap<String, Object>();
   		resultMap.put("state", "fail");
   		resultMap.put("data", data);
   		return new ResponseEntity<Map<String, Object>>(resultMap, state);
   	}
   
   	public ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
   		Map<String, Object> resultMap = new HashMap<String, Object>();
   		resultMap.put("state", "ok");
   		resultMap.put("data", data);
   		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
   	}
   
   }
   
   ```



## 3. Swagger 환경 설정

1. 패키지 맞춰서 com.ssafy.config 패키지에 SwaggerConfig.java 생성

   - apis(RequestHandlerSelectors.basePackage("com.ssafy.controller"))를 통해 컨트롤러 소속 패키지 경로 지정!

   ```
   package com.ssafy.config;
   
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   import com.google.common.base.Predicates;
   
   import springfox.documentation.builders.ApiInfoBuilder;
   import springfox.documentation.builders.PathSelectors;
   import springfox.documentation.builders.RequestHandlerSelectors;
   import springfox.documentation.service.ApiInfo;
   import springfox.documentation.spi.DocumentationType;
   import springfox.documentation.spring.web.plugins.Docket;
   import springfox.documentation.swagger2.annotations.EnableSwagger2;
   
   @Configuration
   @EnableSwagger2
   public class SwaggerConfig {
       @Bean
       public Docket api(){
           return new Docket(DocumentationType.SWAGGER_2)
           		.apiInfo(info())
           		.select()
                   .apis(RequestHandlerSelectors.basePackage("com.ssafy.controller"))
                   .paths(PathSelectors.any())
                   .build();
       }
       
       private ApiInfo info() {
   		return new ApiInfoBuilder().title("Web Curation API")
   				.description("Web Curation Service를 위한 <b>CRUD</b>")
   				.license("Team6")
   				.version("3.0")
   				.build();
   	}
   }
   ```



