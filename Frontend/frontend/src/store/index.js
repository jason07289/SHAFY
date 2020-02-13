import Vue from 'vue'
import Vuex from 'vuex'
import post from './post'
import user from './user' 
import tags from './tags'

Vue.use(Vuex)

export default new Vuex.Store({
  modules:{
    user,
    post,
    tags
  },
})
