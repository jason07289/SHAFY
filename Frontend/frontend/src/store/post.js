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
  clearAll({ commit }){
    commit('setClearAll')
  },
  getTabposts({ commit }, tabName){
    state.busy = true
    PostApi.getTabPostlist({hashtag:tabName, page:state.page},res=>{
      console.log('응답오고있는지',res)
      if (res.data.state === 'ok'){
        commit('setPostlist', res.data.message.post)
        if(res.data.message.next === false){
          state.busy =true
        }
      }else{
        state.busy = true
      }
    },err=>{
      state.busy = true
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
    state.posts = state.posts.concat(posts)
    state.page++
    state.busy = false
    console.log('store에서 확인',state.posts)
  },
  setClearAll(state){
    state.posts = [],
    state.busy = false,
    state.page = 0
    console.log('스테이트 초기화 함')
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}