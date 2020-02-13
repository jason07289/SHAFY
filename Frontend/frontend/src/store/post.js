import PostApi from '../apis/PostApi'
// import router from '../main'

/* eslint-disable no-unused-vars */
// initial state
const state = {
    posts : [],
}

// getters
const getters = {
  
}

// actions
const actions = {
  getAllPosts ({ commit }, data){
    console.log('여기서 찍힘',data)
    PostApi.getHomePost(data,res=>{
      console.log(res)
      if (res.data.state === 'ok'){
        console.log(res)
        commit('setPostlist', res.data.message)
      }else{
        // error 메시지를 브라우저 알림으로
        console.log(res.data)
      }
    },err =>{
      console.log(err)
    })
  },
  doposting({ commit }, data){
    console.log('게시글 작성', data)
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