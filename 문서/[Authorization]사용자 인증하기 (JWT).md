# 사용자 인증하기 (JWT)



인증 방식에는 크게 Session based 와 Token based 가 있다.

두 가지 인증 방식의 차이는 [링크](https://velopert.com/2350)를 참고.



## Token based Authorizaion 

 로그인시 서버 세션에 인증 키를 저장하는 것이 아니라, 클라이언트 상에서 값을 가지고 있는것.



### 동작 방식

| Client                                                       | Server                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [req1]Login (email/pw) 을 전달                               |                                                              |
|                                                              | [res1]사용자 확인 후 토큰 발급<br /> : [JWT](https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world), [DB와 연동](https://www.javainuse.com/spring/boot-jwt-mysql) |
| 발급 받은 token을 local storage에 저장<br /> : [Vue-Session이용](https://sjwiq200.tistory.com/37) |                                                              |
| [req] 요청 시 token을 실어서 보냄 <br />로그아웃시 Session 삭제 | [res] 요청 헤더에 token을 확인한 후 DB에 접근                |



