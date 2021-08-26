const webpack = require('webpack')

module.exports = {
	
	publicPath: '/',
	outputDir:"vueblog", //打包后的项目目录名称

	devServer: {
		host: 'localhost',
		port: '8083',
		https: false,
		open: true, //配置自动启动浏览器
		
		proxy: {
			'/vueblog': { //匹配所有以 '/api' 开头的请求路径
				target: 'http://localhost:8081', //代理目标基础路径（后端接口地址）
				// target: 'http://localhost:8080/vueblog', //代理目标基础路径（后端接口地址）
				changeOrigin: true, //用于控制请求头中的host值
				ws: true, //用于支持websocket
				pathRewrite: {
					'^/vueblog': '',//重写交给服务器的地址，如果没有这句，服务器收到的地址会带/api
				},
			}
		},
	}

}
