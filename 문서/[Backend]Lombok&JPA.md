# Lombok

cf) https://niceman.tistory.com/99

1. lombok: Java 기반에서 기계적으로 작성하는 VO, DTO, Entity 관련 작업을 보다 쉽게 하게 해주는 도구이다.

2. 장점

   - Getter, Setter, ToString , hashCode 관련 메소드 작업 관련 Class 코드를 깔끔하게 작성할 수 있다.

   - Spring(SpringSTS)프로젝트에서 사용할 경우 JPA 환경과 함께 일관성있고 가독성 좋은 애플리케이션을 작성할 수 있다.

3. 단점

   - 협업 모든 인원이 lombok을 설치해야한다.
   - 추가 annotation을 사용할 경우 소스코드의 분석이 난해해지는 것 등이 있다.

4. cf) VO, DTO, Entity

   - VO(Value Object)

   - DTO(Data Transfer Object)
     - DTO는 데이터 전송 객체라는 의미를 가진다. 주로 비동기 처리를 할 때 사용한다.

   - Entity
     - Entity 클래스는 DB 테이블 내에 존재하는 컬럼만을 필드로 가지는 클래스이다. Entity 클래스는 상속을 받거나 구현체여서는 안 되며, 테이블 내에 존재하지 않는 컬럼을 가져서도 안 된다.
     - JPA에서는 @Entity annotation을 명시해야 한다. @Entity는 엔티티 클래스 임을 지정하며, 테이블과 1:1로 매핑된다.

# JPA

cf) https://gmlwjd9405.github.io/2019/08/04/what-is-jpa.html

## 영속성

- 영속성이란 생송한 프로그램이 종료되더라도 사라지지 않는 데이터의 특성
- 파일 시스템, 관계형 DB, 객체 DB 등을 활용하여 데이터를 영구하게 저장하여 영속성을 부여한다.
- Persistence Layer
  - 프로그램의 아키텍처에서, 데이터에 영속성을 부여해주는 계층
  - JDBC를 이용하여 직접 구현할 수 있지만, Persistence framework를 이용한 개발이 많이 이루어진다.



## Persistence Framework

1. SQL Mapper
   - SQL과 Object 필드를 매핑
   - SQL 문장으로 직접 데이터베이스 데이터를 다룬다.
   - ex) Mybatis
2. ORM
   - 데이터베이스 데이터와 Object 필드를 매핑
   - SQL Query가 아닌 직관적인 코드(메서드)로 데이터를 조작할 수 있다.
   - 객체 간의 관계를 바탕으로 SQL을 자동으로 생성한다.
   - Persistant API라고도 할 수 있다.
   - ex) JPA, Hibernate



## JPA

1. JPA란

   - Hibernate
     - ORM Framework, Open Source SW
   - JPA(Java Persistence API)
     - Java의 ORM 기술 표준으로, 인터페이스의 모음
     - JPA 인터페이스를 구현한 대표적인 오픈소스가 Hibernate

2. JPA 사용 목적

   - SQL 중심적인 개발 -> 객체 중심 개발

   - 생산성

     - 간단한 CRUD

     ```
     저장: jpa.persist(member)
     
     조회: Member member = jpa.find(memeberId)
     
     수정: member.setName("변경할 이름")
     
     삭제: jpa.remove(member)
     ```

     - 특히, 수정의 경우, 객체를 변경하면 알아서 DB에 UPDATE Query가 나간다.

   - 유지보수

     - JPA 사용 전: 필드 변경시 모든 SQL을 수정해야 한다.
     - JPA: 필드만 추가하면 된다. SQL은 JPA가 처리하기 때문에 손댈 것이 없다.

   - Object와 RDB 간의 패러다임 불일치 해결

   - JPA의 성능 최적화 기능

   - 데이터 접근 추상화와 벤더 독립성

   - 표준



## Mybatis

- 개발자가 지정한 SQL, 저장 프로시저, 고급 매핑을 지원하는 SQL Mapper이다
- JDBC로 처리하는 상당 부분의 코드와 파라미터 설정 및 결과 매핑을 대신해준다.
- 장점
  - SQL에 대한 모든 컨트롤을 하고자 할때 매우 적합하다.
  - SQL 쿼리들이 매우 잘 최적화되어있을 때 유용하다.
- 단점
  - 애플리케이션과 데이터베이스 간의 설계에 대한 모든 조작을 하고자 할 때는 적합하지 않다.애플리케이션과 데이터베이스 간에 서로 잘 구조화되도록 많은 설정이 바뀌어야 하기 때문이다.