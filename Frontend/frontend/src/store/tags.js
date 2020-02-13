 /* eslint-disable no-unused-vars */
import HashTagApi from '@/apis/HashTagApi'
import router from '../main'

const state = {
  tabtags : [],
  followtags : [],
}


// actions
const actions = {
 getAllTab({ commit }){
  HashTagApi.getTabtag(res=>{
    if (res.data.state === 'ok'){
      console.log(res.data.message)
      commit('setAllTab', res.data.message.hashtag.split('#'))
    }else{
      console.log(res.data)
    }
    },err=>{
      console.log(err)
    }
  )
 }
}
// mutations
const mutations = {
  setAllTab(state, data){
    const end = data.length
    state.tabtags = data.splice(1, end)
  }
  // setSNSseq(state, data){
  //   state.seq = data.seq
  //   state.togosite = data.togosite
  //   console.log('시퀀스와 togosite 저장', data)
  //   router.push({name:'Join'})
  // }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}