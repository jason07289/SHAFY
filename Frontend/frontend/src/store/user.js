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
   

}

// mutations
const mutations = {


  login(state, {data}){
    //1. state의 JWT갱신
    state.JWT = data.JWT;

    //2. localstoarge에 JWT값 저장해두기
    localStorage.JWT = data.JWT;
    
    //3-1. 모든 HTTP요청 헤더에 인증정보를 추가해준다
    axios.defaults.headers.common['Authorization'] = `Bearer ${data.JWT}`; //베어러는 JWT에서 쓴다는데 잘모르겟다..
    //3-2. 갱신했을때, localstorage에서 토큰값을 가져와서 axios헤더에 붙ㅇ준다
    const accessToken = () => {
      const {JWT} = localStorage
      if (!JWT) return
      axios.defaults.headers.common['Authorization'] = `Bearer ${JWT}`;
    }
    accessToken()
  },
  logout(state){
    //1. HTTP 헤더 디폴트값 제거
    axios.defaults.headers.common['Authorization'] = undefined
    //2. mutation에 커밋
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