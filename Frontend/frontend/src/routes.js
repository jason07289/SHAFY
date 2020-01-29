import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import Join_educationStudent from './views/user/Join_educationStudent'
import Join_general from './views/user/Join_general'
import FindPassword from './views/user/FindPassword.vue'
import BookMark from './views/home/BookMark.vue'
import Home from './views/home/Home.vue'
import MyPage from './views/home/MyPage.vue'
import Post from './views/home/Post.vue'
import SetTags from './views/home/SetTags.vue'

 /* eslint-disable no-unused-vars */
export default [

    {
        path : '/',
        name : 'Login',
        component : Login,
        
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
    },
    {
        path : '/user/join/Join_educationStudent',
        name : 'Join_educationStudent',
        component : Join_educationStudent
    },
    {
        path : '/user/join/Join_general',
        name : 'Join_general',
        component : Join_general
    },
    {
        path : '/user/findpw',
        name : 'FindPassword',
        component : FindPassword
    },
    {
        path : '/home',
        name : 'Home',
        component : Home
    },
    {
        path : '/home/bookmark',
        name : 'BookMark',
        component : BookMark
    },
    {
        path : '/home/mypage',
        name : 'MyPage',
        component : MyPage
    },
    {
        path : '/home/post',
        name : 'Post',
        component : Post
    },
    {
        path : '/home/settags',
        name : 'SetTags',
        component : SetTags
    },
]

//라우터가드(인증정보 체크해서 로그인페이지로 보내기)
const checkAuth = () => (from, to, next) => {
    
    if (localStorage.JWT==null) next('/')
    return next()
    
  }
