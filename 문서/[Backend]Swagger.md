# Swagger

 \* 이력사항
 \* 2020. 02. 14. 황란아 최초작성



## Swagger란?

Swagger은 NodeJs, Java, Python 등 다양한 언어를 지원해준다. 이 포스트는 Spring에 적용한 예제이다.

*어노테이션 기반 REST API 문서 자동화*

- 대화형 API 콘솔 자동 생성
- 소스코드 빌드시 문서 자동 생성

문서 자동 생성을 위한 Swagger 어노테이션

- @Api - Controller 단위로 API 메타데이터 명시
- @ApiOperation - 하나의 REST API 요청 URL에 메핑되며, 문서화 대상을 처리 됨
- @ApiParam, @ApiImplicitParam - REST API 호출 시 전달되는 파라미터에 대한 설명
- @ApiModelProperty - Model Class 필드에 대한 설명

## 단점

- 변경 이력관리가 안됨
- 코드 침투적(어노테이션 도배)



## Swagger 환경 설정

1. pom.xml에 dependency 추가

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
   ```
   
   
   
2. 패키지 맞춰서 com.ssafy.config 패키지에 SwaggerConfig.java 생성

   - apis(RequestHandlerSelectors.basePackage("com.ssafy.controller"))를 통해 컨트롤러 소속 패키지 경로 지정!

   ```java
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



## SWagger를 통한 백엔드와 프론트엔드가 문서를 통해 협업!!!