import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/register.vue'
import Blogs from '../views/Blogs.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogDetail from '../views/BlogDetail.vue'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		name: 'Index',
		redirect: {
			name: 'Blogs'
		}
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/register',
		name: 'Register',
		component: Register
	},
	{
		path: '/blogs',
		name: 'Blogs',
		component: Blogs
	},
	{
		path: '/blog/add', // 注意放在 path:'/blog/:blogId'之前,否则认为add是:blogId
		name: 'BlogAdd',
		component: BlogEdit,
		meta: {
			requireAuth: true
		},
	},
	{
		path: '/blog/:blogId',
		name: 'BlogDetail',
		component: BlogDetail
	},
	{
		path: '/blog/:blogId/edit',
		name: 'BlogEdit',
		component: BlogEdit,
		meta: {
			requireAuth: true
		},
	}
]

const router = new VueRouter({
	mode: 'history', //改为history模式
	base: process.env.BASE_URL, //项目根路径
	routes
})

export default router
