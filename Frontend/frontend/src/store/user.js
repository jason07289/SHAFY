// import UserApi from '@/apis/UserApi'
/**~~체크리스트~~
 * 네브바 클릭할때마다 로그인되어있는지 토큰 확인하기(세션에가서 만료되었는지)
 * 그리고 state.JWT가 ''인지만 확인하면되는 경우도 있음
 * 또한,
 * 로그인 함수를 쪼개서 기능별로 나누기( 코드 재사용 )
 * 
 */
// initial state
const state = {
  JWT : '', // '' : 비로그인, '[value]' : 로그인 된 상태
  userInfo : {}, // user 프로필 사진, 이름, 닉네임 등 
}

// getters
const getters = {}

// actions
const actions = {
   
  login({ commit }){
  /**
   * 프론트에서 로그인 버튼을 누름
   * userAPI의 requestLogin을 호출( id와 pw로 확인 )
   * -> response를 확인 ( 로그인 실패하면 다시 로그인 페이지로 )
   * 성공한 경우
   * response의 헤더에서 token값을 가져와서
   * session에 저장
   * mutation의 setJWT에 commit
   */  
  },
  
}

// mutations
const mutations = {

  setJWT (state) {
    /**
     * Session에서 token값을 가져와 state.JWT에 값을 저장  
     */  
  },
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}