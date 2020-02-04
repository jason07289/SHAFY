import UserApi from '@/apis/UserApi'
const axios = require('axios').default

// initial state
const state = {
  JWT : localStorage.getItem('JWT'), // 새로고침해도 토큰값유지하기위함
  userInfo : {}, // user 프로필 사진, 이름, 닉네임 등 
  config : {},
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
    commit('loginSuccess', data.JWT)
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
        commit('setUserInfo', res.data.message)
      }else{
        alert(res.data)
      }
    },error=>{
      console.log(error)
    })
  }
}
// mutations
const mutations = {
  loginSuccess(state, JWT){
    state.JWT = JWT
    localStorage.JWT = JWT
    const accessToken= function(){
      const {JWT} = localStorage.JWT
      if (!JWT) return
      axios.defaults.headers.common['Authorization'] = `Bearer ${JWT}`;
    }
    accessToken()
  },
  logoutSuccess(state){
    state.JWT = null
    delete localStorage.JWT;
  },
  setUserInfo(state, data){
    state.userInfo = data
    console.log('성공', state.userInfo)
  }
}


export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}