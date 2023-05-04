import Vue from 'vue'
import App from './App'
import store from './store' // store
import plugins from './plugins' // plugins
import './permission' // permission
Vue.use(plugins)

Vue.config.productionTip = false
Vue.prototype.$store = store

// #ifdef H5
// 提交前需要注释  本地调试使用
// const vconsole = require('vconsole')
// Vue.prototype.$vconsole = new vconsole() // 使用vconsole
// #endif


App.mpType = 'app'

const app = new Vue({
  ...App
})

app.$mount()
