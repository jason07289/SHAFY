<h1>SSAFY SNS</h1>



<h2> JPA </h2>
 &nbsp;Java Persistence API. 자바의 ORM(Object-Relational Mapping) 표준 기술이다. 즉 자바의 객체와 관계형 DB를 매핑하는 기술이다.
<br/><br/>
<b> Hibernate </b><br/>
 &nbsp;ORM Framework 중 하나로 MyBatis에 비해 코드가 훨씬 더 간결하며 객체지향적이다.<br/><br/>

    장점 - 생산성, 유지보수가 높다.
    단점 - 어렵다, 성능상 문제가 있을 수 있다.

 
<br/><br/><br/>
<b>환경설정</b>

1. pom.xml
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    ```

2. application.properties
    ```properties
    # JPA
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.database=mysql
    # 아래와 같이 mysql InnoDB 설정을 해줘야 foreign key가 작동한다. (default는 MyISAM)
    spring.jpa.database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    ```

<br/><br/><br/>
<b>폴더 구조</b>

각 기능별로 Controller, DTO, Service, Repository가 필요하며 Repository는 <b>Interface</b>이다.<br/>

<center><img src="https://user-images.githubusercontent.com/33229855/74412697-5b6f7400-4e81-11ea-9d94-7db0fe06a82f.png" width="200px"/></center>

<br/><br/><br/>
<b>사용법</b>

  &nbsp;기본적으로 MyBatis나 기존의 테이블 데이터를 출력하기 위해 'select * from user'와 같은 쿼리문을 직접 사용했던 방식과 달리 JPA에서는 user.findAll()이라는 간단한 자바 명령어로 대신할 수 있다.
  
  &nbsp;DTO에서 객체와 DB 테이블을 매핑해주고, Repository를 만들고 나면 Controller나 Service에서 JPA에서 제공하는 몇 가지 기본적인 메소드들을 사용할 수 있다. 아래 세가지를 포함한 몇 가지 함수들을 제공한다. (update 메소드는 존재하지 않는다.)

    save(), findAll(), deleteAll(), ...

 <br/> &nbsp;만일 커스텀 기능을 사용하고 싶을 경우 Repository(인터페이스)에 직접 선언 후 사용할 수 있다. 예를 들어 FAQRepository에 faq번호(no)로 검색하는 쿼리문을 동작시키고 싶을 경우 아래와 같이 함수 이름을 <mark>findByNo(int no)</mark>로 선언해주기만 하면 아래 쿼리문과 똑같이 동작하는 기능을 사용할 수 있다.

```sql
   select * from faq where no=#{no}
```
<br/>
        <b>※주의!</b> JPA에서 기본적으로 제공되는 함수 중에 findById()라는 메소드가 있는데 Id가 컬럼명이 아닌 Primary key로 찾겠다는 뜻이다. 즉, FAQ에서 no컬럼이 Primary key일 경우 <mark>findById()</mark> 로도 사용할 수 있다.
<br/><br/><br/>

1. <b>DTO</b><br/><br/>
    생성자 접근 권한은 PUBLIC, PROTECTED 외 다른 것들을 지정할 수 있다. 단, <mark>access = AccessLevel.PROTECTED</mark>를 설정할 경우 외부에서 해당 생성자에게 접근하는 것을 막을 수 있다.
    ```java
    @NoArgsConstructor(access = AccessLevel.PROTECTED) // 인자 없는 생성자
    @AllArgsConstructor // 모든 인자있는 생성자
    @ToString           // toString() 메소드
    @Getter             // Getter,Setter를 합쳐서 @Data로 사용할 수 있지만 권장X
    @Setter
    @Entity             // A. DB 테이블과 일대일로 매칭되는 객체 단위
    @Table(name = "faq")// B. FAQ 객체와 매핑될 DB 테이블 이름 지정
    public class FAQ {  // A, B를 합쳐서 @Entity(name="faq")로도 가능.
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(length = 30, nullable = false, unique = true)
        private int no;
        
        @Column(length = 50, nullable = false)
        private String title;
        
        @Column(length = 1000, nullable = false)
        private String content;
        
        @Column(length = 30, nullable = false)
        private String id;
        
    //	@CreationTimestamp
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @Column(nullable = false)
        private Date datetime; // 주의!!! import.java.util.Date!!!

        // 외래키 설정
        @ManyToOne
        @JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_faq_id"))
        private User user;
    }
    ```
    <br/>그 외 Annotation<br/>
    ```java
    /**
    * 객체 위에 사용하는 Annotation
    */
    @RequiredArgsConstructor //not null인 애들 빼고 모두 파라미터로 가지는 생성자

    /**
    * 속성 위에 사용하는 Annotation
    */
    @NotNull // not null 지정. 이 태그를 이용할 경우 자바상에서도 에러를 잡아줌.
    
    @ToString(exclude="컬럼명") // 특정 컬럼을 뺄 수 있음.

    @Column(columnDefinition="TIMYINT", length=1)
    private int deleted;        //Boolean Type을 사용하고 싶을 경우
    ```
<br/><br/>
    
2. <b>Update</b><br/><br/>
  &nbsp;JPA에서는 EntityManager가 엔티티를 조회/저장/삭제/수정한다. 하지만 수정 API는 찾아볼 수 없는데 엔티티 매니저가 엔티티 변경이 일어나면 자동으로 감지하여 DB에 반영하기 때문이다. 이를 변경 감지라고 한다.<br/><br/>
  2-1. 변경 감지<br/>
  dfefdf<br/><br/>
  2-2. Custom Update<br/>
    ```java
    //Repository
    @Modifying
    @Query("update comments c set c.deleted = true where c.no=:no")
    void updateDeleted(@Param("no") int no);
        
    //ServiceImpl
    @Override
    @Transactional  //Transactional을 걸어주지 않을 경우 Error
    public void delete(int no) {
        //deleted = true로 활성화
        try {
            commentsRepository.updateDeleted(no);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ```
<br/><br/><br/>
<h2>JPQL</h2>
<b>&nbsp;[Java Persistence Query Language]</b> <br/>
&nbsp;JPA의 일부로 정의된 플랫폼 독립적인 객체지향 쿼리 언어이다. SQL과 비슷한 문법을 가지지만 DB 테이블에 직접 연결되는 것이 아니라 <b>JPA 엔티티에 대해 동작</b>한다. 따라서 쿼리문에 테이블이 아닌 <b>엔티티의 컬럼명</b>을 사용해주어야 한다.<br/><br/>

<b>사용법</b>
JPQL에서는 객체(Entity)의 별칭을 필수적으로 명시해야 한다.
 ***select \**** 을 사용할 수 없다. ***select 별칭*** 으로 사용해야 한다.


 ```java
 @Query(value="select e from Entity e where e.name=:name")
 public Entity searchByName(@Param("name") String name);
 ```
 
 &nbsp;<b><i>select *</i></b> 과 같은 sql문법을 사용하고 싶을 경우는 다음과 같이 선언한다.
 
 ```java
 @Query(value="select * from Entity where name=:name", nativeQuery=true)
 public Entity searchByName(@Param("name") String name);
 ```

  <br/>
 ※ 주의!! Param안의 이름과 함수의 매개변수 명이 같아야 한다.<br/><br/>

 ```java
 //가능
 @Query(value="select e from Entity e where e.name=:name")
 public Entity searchByName(@Param("name") String name);

 //불가능(Error 발생)
 @Query(value="select e from Entity e where e.name=:name")
 public Entity searchByName(@Param("entityNamme") String name);
 ```



