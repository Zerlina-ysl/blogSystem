import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Blogs from '../components/Blogs.vue'
import BlogEdit from '../components/BlogEdit.vue'
import BlogDetail from '../components/BlogDetail.vue'

Vue.use(VueRouter)
//路由规则的配置.
const routes = [
  {
    path: '/',
    name: 'Index',
    // component: Blogs
    redirect:{name:"Blogs"}
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },

  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail,

  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit,
    meta:{
      requireAuth:true
    }
  },
  {
    path:'/blogdel/:blogId',
    name:'BlogDel',
    component: BlogDetail,
  },


]

const router = new VueRouter({
  //使用history模式
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
