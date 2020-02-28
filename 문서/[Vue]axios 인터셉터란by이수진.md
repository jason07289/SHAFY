# axios 인터셉터란 ? 

요청을 보낼때 매번 인증 정보를 헤더에 설정해줘야 하는 경우 

axios interceptor 를 전역에 두고 사용.



- 생성

```javascript
// Add a request interceptor
axios.interceptors.request.use(function (config) {
  	config.headers.common['Authorization'] = `Bearer ${data.JWT}`;
    return config
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  });
```



- 제거 (eject)

```javascript
const myInterceptor = axios.interceptors.request.use(function () { /*...*/ });
axios.interceptors.request.eject(myInterceptor);
```



---

참고 

[새로고침 없이 로그인 하기](https://fkkmemi.github.io/nemv/nemv-053-axios-interceptor/)

[axois 러닝 가이드 인터셉터](https://yamoo9.github.io/axios/guide/interceptors.html)



