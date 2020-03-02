 /* eslint-disable no-unused-vars */
import HashTagApi from '@/apis/HashTagApi'
// import router from '../main'

const state = {
  tabtags : [],
  followtags : {},
}

const getters = {
} 
// actions
const actions = {
 getAllTab({ commit }){
  HashTagApi.getTabtag(res=>{
    if (res.data.state === 'ok'){
      var temp = res.data.message.hashtag.split('#')
      const end = temp.length
      temp = temp.splice(1,end)
      commit('setAllTab', temp)
    }else{
      console.log(`${res.data.message} : 탭 가져오기실패`)
    }
    },err=>{
      console.log(`${err} : 탭 가져오기실패`)
    }
  )
 },
 getMyfollowing({ commit }){
  HashTagApi.getFollowtag(res=>{
    if (res.data.state === 'ok'){
      if (res.data.message === null){
        console.log('팔로잉 태그 : 0')
      }else{
        var temp = res.data.message.hashtag.split('#')
        const end = temp.length
        temp = temp.splice(1,end)
        commit('setMyfollowing', temp)
      }
    }
  },err=>{
    console.log(err) 
  })
 },
 updateTab({ commit },data){
   HashTagApi.putTabtag(data,res=>{
     if (res.data.state === 'ok'){
      console.log(`DB에 반영 : ${res.data.message}`)
      var temp = res.data.message.hashtag.split('#')
      const end = temp.length
      temp = temp.splice(1,end)
      commit('setAllTab', temp)
     }else{
      console.log(`탭 태그 커밋 실패:${res.data.message}`)
     }
   },err=>{
    //  console.log(`탭 태그 오류 발생: ${err}`)
   })
 },
}
// mutations
const mutations = {
  setAllTab(state, data){
    state.tabtags = data
    // console.log('tab 설정 완료')
  },
  setMyfollowing(state, data){
    for (var tag in data){
      state.followtags[data[tag]] = 1
    }
  },
  setOnefollowing(state, data){
    var temp = {...state.followtags}
    temp[data] = 1
    state.followtags = temp
  },
  deleteOnefollowing(state, data){
    var temp = {...state.followtags}
    delete temp[data]
    state.followtags = temp
    // console.log(this.followtags)
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
  getters,
  state,
  actions,
  mutations
}