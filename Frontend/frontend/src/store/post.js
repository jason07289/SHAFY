import PostApi from '../apis/PostApi'
// import router from '../main'

/* eslint-disable no-unused-vars */
// initial state
const state = {
    posts : [],
    busy : false,
    page : 0,
}

// getters
const getters = {
  
}

// actions
const actions = {
  getTabposts({ commit }, tabName){
    state.busy = true
    PostApi.getTabPostlist({hashtag:tabName, page:state.page},res=>{
      if (res.data.state === 'ok'){
        commit('setPostlist', res.data.message)
      }else{
        console.log(res)
      }
    },err=>{
      console.log(err)
    })
  },
  doposting({ commit }, data){
    PostApi.requestPosting(data,
      res=>{
        if (res.data.state === 'ok'){
          console.log('게시글 작성 성공!')
          //alert('게시글이 작성 되었습니다.')
          // router.push({name:'Home'})
        }else{
          console.log(res)
        }
      },err=>{
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