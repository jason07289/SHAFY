import UserApi from '@/apis/UserApi'
const axios = require('axios').default

// initial state
const state = {
  JWT : localStorage.getItem('JWT'), // 새로고침해도 토큰값유지하기위함
  userInfo : {
    id:'',
    name:'',
    img:'',
  }, // user 프로필 사진, 이름, 닉네임 등 
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
  /**
   * UserApi에서 response data 넣어서 호출
   */
  login({ commit },data){
    //1. data내용을 바탕으로 mutation커밋 (JWT, userInfo 변이)
    commit('loginSuccess', data.JWT)
    axios.defaults.headers.common['Authorization'] = `Bearer ${data.JWT}`;
  },
  logout({ commit }){
    axios.defaults.headers.common['Authorization'] = undefined
    commit('logoutSuccess')
  },
  getUserInfo(){
    UserApi.requestUserInFo(res=>{
      console.log(res)
    },error=>{
      console.log(error)
    })
  }
}
// mutations
const mutations = {
  loginSuccess(state, JWT){
    state.JWT = JWT;
    localStorage.JWT = JWT;
    //3. 갱신했을때, localstorage에서 토큰값을 가져와서 axios헤더에 붙여줌
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
  }
}


export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}