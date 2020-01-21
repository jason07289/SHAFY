*시작하기 앞서...*

[`Vue.js`시작 하기](./docs/환경구축.md)를 따라 개발 환경을 구축





#### vue 프로젝트 시작하기

In `gitbash`

```cmd
Vue creat <project-name>
```

- 설정 세팅은 default를 선택
- 기본적으로 제공하는 Babel, Eslint를 사용
- project-name은 대문자가 들어가면 안된다.



#### vue-router를 이용 한 URL 매핑

In `gitbash`

 ```cmd
npm install vue-router
yarn add vue-router
 ```

 

In `main.js`

```javascript
import Router from 'vue-router'
import routes from './routes'


// `Vue.use()`를 통해 명시적으로 라우터를 추가.
Vue.use(Router)

// 전역에서 사용할 인스턴스 생성
// `routes.js`  에 경로를 지정
const router = new Router({
    routes,
})

// `vue`앱에 추가
new Vue({
    ...
    router,
	...
}).$mount('#app');
```



In `App.vue`

```html
<template>
    <div id="app">
            <!-- router할 위치를 잡아준다. -->
        <router-view></router-view>
    </div>
</template>
```



**라우팅 될 페이지의 폴더 구조**는 다음과 같다. 

```
views
	|- exception
    	|- Error.vue
    	|- NotFound.vue
	|- home
		|- BookMark.vue
		|- Home.vue
		|- MyPage.vue
		|- Post.vue
		|- SetTags.vue
	|- user
		|- FindPassword.vue
		|- Join.vue
		|- Login.vue
```



### 설치

#### email-validator 과 password-validator 

Login 페이지 구현 시 email 과 password 유효성 검사를 위해 `validator`를 설치.

```cmd
npm install password-validator 
npm install email-validator
```

#### axios

```cmd
npm install axios
```

#### mdi

```cmd
npm install material-design-icons-iconfont
```

```javascript
// main.js
import 'material-design-icons-iconfont/dist/material-design-icons.css'
```

---

## Error

#### (no-unused-vars)Eslint error

js 파일에서 변수 선언 후 사용하지 않을 경우 에러가 발생

문제가 되는 파일의 최 상단에 아래 내용을 추가. (임시적으로 사용?)

```javascript
 /* eslint-disable no-unused-vars */
```

 





----

참고 링크 

[Vue-CLI](https://cli.vuejs.org/guide/creating-a-project.html#vue-create)

[Vue-Router](https://router.vuejs.org/kr/)