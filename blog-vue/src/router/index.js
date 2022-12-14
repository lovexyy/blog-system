import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login' 
import Blogs from '@/views/Blogs' 
import BlogEdit from '@/views/BlogEdit' 
import BlogDetail from '@/views/BlogDetail' 

Vue.use(VueRouter)

const routes = [ 
  {
    path: '/',
    name: 'Index',
    redirect:{name : "Blogs"}
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },{
  path: '/login',
  name: 'Login',
  component: Login
  },{
    path: '/blog/add',
    name: 'BlogEdit',
    component: BlogEdit
  },{
    path: '/blog/:blogid',
    name: 'BlogDetail',
    component: BlogDetail
  } ,{
    path: '/blog/:blogid/edit',
    name: 'BlogEdit', 
    component: BlogEdit
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
