import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
Vue.use(Element)

import axios from 'axios'
Vue.prototype.$axios = axios
import "./axios"
// axios.defaults.withCredentials=true;//让ajax携带cookie

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

import "./permission"

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
