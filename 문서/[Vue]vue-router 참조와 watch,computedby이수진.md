### 로그인 페이지 

회원인 사용자가 이메일, 비밀번호를 올바르게 입력하고 로그인 버튼을 누르면 홈 화면으로 이동. 

[Vue-router](https://router.vuejs.org/kr)를 이용해 페이지를 전환.



`main.js` 에서 이미 라우터 인스턴스를 만들고, `Vue` (app) 인스턴스에 선언



`login.vue` 에서는 `this` 로 참조가 가능

```javascript
...
this.$router.push({ path:'home' })
...
```




### checkform()에 watch() 속성 이용 

- checkform() : 이메일 형식과 비밀번호의 유효성을 검사 

-  watch에 data를 정의 ->  data가 변화할 때 마다 값이 갱신.

  

computed(선언형) vs watch(명령형) 

선언형 프로그래밍이 코드반복이 적다 



computed : 일반적으로 store(Vuex)의 값이 바뀔 때 사용한다.

watch : 상태가 바뀐 후 콜백 함수를 호출해야 할 때 사용한다.



*선언형 프로그래밍과 명령형 프로그래밍 ?*

- 명령형 : 알고리즘을 명시하고 목표는 명시하지 않는다. (HOW)
- 선언형 : 목표를 명시하고 알고리즘을 명시하지 않는다. (WHAT)





---

참고

Vue-router [프로그래밍 방식 네비게이션](https://router.vuejs.org/kr/guide/essentials/navigation.html)

JavaScript [함수 표현식 vs 함수 선언식](https://joshua1988.github.io/web-development/javascript/function-expressions-vs-declarations/) ,[화살표 함수]([https://velog.io/@ki_blank/JavaScript-%ED%99%94%EC%82%B4%ED%91%9C-%ED%95%A8%EC%88%98Arrow-function](https://velog.io/@ki_blank/JavaScript-화살표-함수Arrow-function)), [선언형 프로그래밍과 명령형 프로그래밍](https://dwenn.tistory.com/105)

