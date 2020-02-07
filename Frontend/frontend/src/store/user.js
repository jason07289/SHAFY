import UserApi from '@/apis/UserApi'
import router from '../main'
const axios = require('axios').default

// initial state
const state = {
  JWT : localStorage.getItem('JWT'), // 새로고침해도 토큰값유지하기위함
  userInfo : {}, // user 프로필 사진, 이름, 닉네임 등 
  config : {},
  seq: 0,
  myurl:'http://70.12.246.84:8080',
  hosturl:'http://70.12.246.122:8080',
}

// getters
const getters = {
  isLogin: function(state){
    var JWT = state.JWT
    if(JWT==null){
      return false
    }else{
      return true
    }
  }
}

// actions
const actions = {
  login({ commit },data){
    console.log('before go to server')
    return UserApi.requestLogin(data,res=>{
      if(res.data.state==="ok"){
        console.log('before commit')
        commit('loginSuccess', res.data.JWT)
        console.log('after commit', state.JWT)
      
      }else{
        console.log(res)
        // alert(res.data)
      }
    },error=>{
      console.log('로그인에러',error)
    })
  },

  logout({ commit }){
    axios.defaults.headers.common['Authorization'] = undefined
    axios.interceptors.request.eject();
    commit('logoutSuccess')
  },
  getUserInfo({ commit }){
    UserApi.requestUserInFo(res=>{
      // console.log('userInfoAPI',res)
      if (res.data.state === 'ok'){
        console.log(router)
        commit('setUserInfo', res.data.message)
        
      }else{
        alert(res.data)
      }
    },error=>{
      console.log(error)
    })
  },
  SNSLogin({ commit }, data){
    console.log(data)
    // 데이터가 토큰인 경우 loginSuccess로 
    if (data.token === undefined){
      commit('setSNSseq', data.seq)
    }else{
      commit('loginSuccess',data.JWT)
    } 
    // 데이터가 시퀀스인 경우 시퀀스 값 저장하고 라우팅해주기
  }
}
// mutations
const mutations = {
  loginSuccess(state, JWT){
    state.JWT = JWT
    localStorage.JWT = JWT
    // const accessToken= function(){
    //   const {JWT} = localStorage.JWT
    //   if (!JWT) return
    //   axios.defaults.headers.common['Authorization'] = `Bearer ${JWT}`;
    // }
    // accessToken()
    router.push({name:'Home'})
  },
  logoutSuccess(state){
    state.JWT = null
    delete localStorage.JWT;
  },
  setUserInfo(state, data){
    state.userInfo = data
    console.log('성공', state.userInfo)
  },
  setSNSseq(state, data){
    state.seq = data
    console.log('시퀀스 저장', data)
    router.push({name:'Join'})
  }
}


export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}