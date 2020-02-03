import PostApi from '../apis/PostApi'

// initial state
const state = {
    posts : [],
}

// getters
const getters = {
  
}

// actions
const actions = {
  getAllPosts ({ commit }, params){
    PostApi.getPostlist(params,res=>{
      if (res.data.state === 'ok'){
        console.log(res)
        commit('setPostlist', res.data.message)
      }else{
        // error 메시지를 브라우저 알림으로
        alert(res.message)
      }
    },err =>{
      console.log(err)
    })
  }
}

// mutations
const mutations = {
// postslist 업데이트 
  setPostlist(state, posts){
    state.posts = posts
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}