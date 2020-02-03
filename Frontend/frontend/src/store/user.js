// import UserApi from '@/apis/UserApi'
const axios = require('axios').default

// initial state
const state = {
  JWT : localStorage.getItem('JWT'), // 새로고침해도 토큰값유지하기위함
  userInfo : {}, // user 프로필 사진, 이름, 닉네임 등 
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
    //2. 모든 HTTP요청 헤더에 인증정보를 추가해준다
    axios.defaults.headers.common['Authorization'] = `Bearer ${data.JWT}`; //베어러는 JWT에서 쓴다는데 잘모르겟다..
  },
  logout({commit}){
    //1. HTTP 헤더 디폴트값 제거
    axios.defaults.headers.common['Authorization'] = undefined
    //2. mutation에 커밋
    commit('logoutSuccess')
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