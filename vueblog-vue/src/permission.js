import router from "./router";

// 路由判断登录 根据路由配置文件的参数
router.beforeEach((to, from, next) => {

	if (to.matched.some(record => record.meta.requireAuth)) { // 判断该路由是否需要登录权限

		const token = localStorage.getItem("token")
		console.log("------------" + token)

		if (token) { // 判断当前的token是否存在 ； 登录存入的token
			next()
		} else {
			next({ // 不存在跳到登录页面
				path: '/login'
			})
		}
	} else {
		next()
	}
})
