import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//在入口文件引入element-ui的依赖
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//引入axios
import axios from 'axios'
//引入拦截器
import "./axios.js"

import  mavonEditor from 'mavon-editor'

import 'element-ui/lib/theme-chalk/index.css'
import 'mavon-editor/dist/css/index.css'





//全局使用element组件
Vue.use(Element)
Vue.use(mavonEditor)

Vue.config.productionTip = false
Vue.prototype.$axios = axios

//项目入口
new Vue({
  //将路由规则对象注册到vm实例，在实例中提供this.$route(访问当前路由 this.$router(访问路由器
  router,
  store,
  //利用render函数渲染APP.vue组件
  //render: function (createElement) {
  //      return createElement(App);
  // }
  render: h => h(App)
}).$mount('#app')
