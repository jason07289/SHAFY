# REST API 

Representational State Transfer API



#### 배경

- 과거 웹페이지가 브라우저가 웹 서버에 페이지를 요청하여 클라이언트를 제공
- 클라이언트에 SPA(Single-Page_Application)를 사용하면서 클라이언트와 서버의 분리되기 시작.



#### 구성

REST란 ? 

소프트웨어 프로그램 아키텍처의 한 형식

URI를 통해 **자원을 표시**하고, HTTP method를 이용하여 해당 **자원의 행위**를 정해주며 그 결과를 받는것

- 자원(Resource) : URI
- 행위(Verb) : Http method
- 표현(Representations)

Rest 기반의 규칙들을 지켜서 설계된 API를 Rest API 혹은 Rest API 라고 한다.

#### 특징

1. Uniform Interface (유니폼 인터페이스)

   http 표준만 따른다면 특정 플랫폼에 종속되지 않고 사용 가능 

2. Stateless (무상태성)

   서버는 각각의 요청을 완전히 다른 것으로 인식하고 처리를 한다.

3. Cacheable (캐시가능)

4. Self-descriptiveness (자체 표현 구조)

5. Client - Server 구조

   클라이언트와 서버에서 개발해야 할 내용이 명확해지고 서로간의 의존성이 줄어든다.

6. 계층형 구조

#### 리소스 원형

- 도큐먼트 : 객체 인스턴스나 데이터베이스 레코드와 유사한 개념 

- 컬렉션 : 서버에서 관리하는 디렉터리라는 리소스 

- 스토어 : 클라이언트에서 관리하는 리소스 저장소

  

#### 설계 규칙

- URI 형태

1. 슬래시 구분자는 계층 관계를 나타낸다.
2. URI 마지막 문자로 슬래시를 포함하지 않는다.
3. 하이픈(-)은 URI 가독성을 높이는데 사용한다.
4. 밑줄(_)은 URI에 사용하지 않는다.
5. URI 경로는 소문자가 적합하다.
6. 파일 확장자는 URI에 포함 시키지 않는다.



- URI 경로 디자인
  1. 도큐먼트 이름으로는 단수 명사를 사용
  2. 컬랙션 이름으로는 복수명사를 사용
  3. 스토어 이름으로 복수 명사를 사용
  4. 경로 부분 중 변하는 부분은 유일한 값으로 대체
  5. CRUD 기능을 나타내는 것은 URI에 사용하지 않음



- 요청 메소드

  ```
  GET : 리소스 상태의 표현
  POST : 새로운 리소스를 만들거나, 컨트롤러를 실행
  PUT : 추가, 갱신
  DELETE : 리소스 제거 
  ```



- Function or method 표현 

  ```
  # 직원 1이 메일을 보낸다
  POST /employees/1/send-mail
  ```

  



#### 응답상태코드

- 1xx : 전송 프로토콜 수준의 정보 교환

- 2xx : 클라어인트 요청이 성공적으로 수행됨

- 3xx : 클라이언트는 요청을 완료하기 위해 추가적인 행동을 취해야 함

- 4xx : 클라이언트의 잘못된 요청

- 5xx : 서버쪽 오류로 인한 상태코드

  

---

참고 

[HTTP-REST-API](https://velog.io/@wlsdud2194/HTTP-REST-API-란)

[REST의-탄생-배경](http://recordingbetter.com/drf/2017/07/11/REST의-탄생-배경)

