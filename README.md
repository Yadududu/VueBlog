介绍
这是一个基于SpringBoot + Vue开发的前后端分离博客项目。

技术栈：  
前端：  
vue  
route  
element-ui  
axios  
后端：  
Springboot  
mybatis plus  
shiro  
redis  
Hibernate validatior  
jwt  

vueblog-vue运行需要安装:  
npm i element-ui  
npm i axios  
npm i mavon-editor(编辑器)  
npm i markdown-it(解释md文档)  
npm i github-markdown-css(md样式)  

部署到服务器使用nginx代理,nginx配置:  
```
server {
	listen      80;
	server_name  testvue.mylmj.top www.testvue.mylmj.top;

	location / {
		root /usr/local/www/springbootvuebefore;
		index index.html index.htm;
		#不写这句,history模式请求路由会变成直接请求后台的路由导致404
		try_files $uri $uri/ /index.html;
	}
	
	#反向代理，解决跨域问题，不配置的话也容易出现405的问题
	location ~/springbootvue/{
		proxy_pass http://localhost:8080;
	}

}
```