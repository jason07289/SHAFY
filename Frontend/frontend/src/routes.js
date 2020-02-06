import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import Join_detail from './views/user/Join_detail'
import FindPassword from './views/user/FindPassword.vue'
import BookMark from './views/home/BookMark.vue'
import Home from './views/home/Home.vue'
import MyPage from './views/home/MyPage.vue'
import Posting from './views/home/Posting.vue'
import SetTags from './views/home/SetTags.vue'

 /* eslint-disable no-unused-vars */
 //라우터가드(인증정보 체크해서 로그인페이지로 보내기)
 const checkToken = function(token){
     return token===null || token===undefined || token==='';
 }
const checkAuth = () => (to, from, next) => {
    var token = localStorage.JWT
    console.log("token:"+token)
    if (checkToken(token)) {
        //alert("로그인 해주시기 바랍니다.")
        // alert("로그인 후 이용해주세요")
        return next({name:'Login'})
    }
    return next()
  }

  const blockLogin = ()=> (to, from, next) =>{
      var token = localStorage.JWT
    // 로그인 했을 때는 이전 페이지로 
      if(!checkToken(token)){
          console.log(from.path)
          return next(from.path)
      }
      return next()
  } 


export default [

    {
        path : '/login',
        name : 'Login',
        component : Login,
        beforeEnter: blockLogin()
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
    },
    {
        path : '/user/join/:type',
        name : 'Join_detail',
        component : Join_detail,
    },
    {
        path : '/user/findpw',
        name : 'FindPassword',
        component : FindPassword
    },
    {
        path : '/',
        name : 'Home',
        component : Home,
        beforeEnter: checkAuth()
    },
    {
        path : '/home/bookmark',
        name : 'BookMark',
        component : BookMark,
        beforeEnter: checkAuth()
    },
    {
        path : '/home/mypage',
        name : 'MyPage',
        component : MyPage,
        beforeEnter: checkAuth()
    },
    {
        path : '/home/posting',
        name : 'Posting',
        component : Posting,
        beforeEnter: checkAuth()
    },
    {
        path : '/home/settags',
        name : 'SetTags',
        component : SetTags,
        beforeEnter: checkAuth()
    },
]


