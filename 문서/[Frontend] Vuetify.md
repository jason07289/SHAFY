[참고자료 : 스팀잇 기반 앱 만들기 깃허브][https://github.com/anpigon/steemit-app]

(2020 02 03 새로하는것)

#### 설치

```shell
$ vue add vuetify
$ npm install vuetify --save
$ npm install sass sass-loader fibers deepmerge -D
$ npm install material-design-icons-iconfont --save
$ npm install --save vuedraggable
```

#### 적용



---

(예전에한것들)

#### 설치

```shell
$ npm install vuetify --save
$ npm install sass sass-loader fibers deepmerge -D
or
$ yarn add vuetify
$ yarn add sass sass-loader fibers deepmerge -D

$ npm install material-design-icons-iconfont --save

```



#### `main.js` Vuetify 사용 지시

```javascript
import Vuetify from 'vuetify' // vuetify 임포트(import)
import 'vuetify/dist/vuetify.min.css' // Vuetify.css 임포트(import)

...

Vue.use(Vuetify) // Vuetify를 사용하도록 지시
```



#### `index.html`에 머티리얼 디자인 아이콘 사용태그 추가

```html
<head>
    ...
  <link href='https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons' rel="stylesheet">
	...
</head>
```

참고 
[아마존 공식문서](https://docs.aws.amazon.com/ko_kr/sdk-for-javascript/v2/developer-guide/setting-up-node-on-ec2-instance.html)

