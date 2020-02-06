import Vue from 'vue'
import App from './App.vue'
import Router from 'vue-router'
import routes from './routes'
import store from './store'
import vuetify from './plugins/vuetify';
import Vuetify from 'vuetify/lib'


Vue.config.productionTip = false

Vue.use(Router)
Vue.use(Vuetify)

const router = new Router({
  routes,
  mode: 'history',
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

export default router