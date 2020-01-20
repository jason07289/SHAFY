import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import FindPassword from './views/user/FindPassword.vue'
import BookMark from './views/home/BookMark.vue'
import Home from './views/home/Home.vue'
import MyPage from './views/home/MyPage.vue'
import Post from './views/home/Post.vue'
import SetTags from './views/home/SetTags.vue'

export default [

    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
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
