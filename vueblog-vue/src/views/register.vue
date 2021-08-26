<template>
	  <div>
		    <el-container>
			      <el-header>
				        <router-link to="/blogs">
					        <img src="../assets/VueBlog-logo.png" style="height: 60%; margin-top: 10px;">
					        </router-link>
			      </el-header>
			      <el-main>
				        <el-form :model="ruleForm" status-icon :rules="rules" ref="loginForm" label-width="100px" class="demo-ruleForm">
					         <h1>注册</h1>
					          <el-form-item label="用户名" prop="username">
						            <el-input type="text" maxlength="12" v-model="ruleForm.username"></el-input>
					          </el-form-item>
					          <el-form-item label="密码" prop="password">
						            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
					          </el-form-item>
					          <el-form-item label="邮箱" prop="email">
						            <el-input type="email" v-model="ruleForm.email"></el-input>
					          </el-form-item>
					          <el-form-item>
						            <el-button type="primary" @click="submitForm('loginForm')">注册</el-button>
						            <el-button @click="resetForm('loginForm')">重置</el-button>
									<el-button type="success" @click="login">登录</el-button>
					          </el-form-item>
				        </el-form>
			      </el-main>
			</el-container>
	  </div>
</template>
<script>
	export default {
		name: 'Login',
		data() {
			var validatePass = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入密码'));
				} else {
					callback();
				}
			};
			var validatePass2 = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入邮箱地址'));
				} else {
					callback();
				}
			};
			return {
				ruleForm: {
					password: '',
					username: '',
					email:'',
				},
				rules: {
					password: [{
						required: true,
						validator: validatePass,
						trigger: 'blur'
					}],
					username: [{
							required: true, //是否必填
							message: '请输入用户名',
							trigger: 'blur'
						},
						{
							min: 3,
							max: 12,
							message: '长度在 3 到 12 个字符',
							trigger: 'blur'
						}
					],
					email: [{
							required: true,
							validator: validatePass2,
							trigger: 'blur'
						},
						{
							type: 'email',
							message: '请输入正确的邮箱地址',
							trigger: ['blur', 'change']
						}
					],
				}
			};
		},
		methods: {
			submitForm(formName) {
				const _this = this;
				this.$refs[formName].validate((valid) => {
					if (valid) {
						// 提交逻辑
						this.$axios.post('/register', this.ruleForm).then((res) => {
							// console.log("res.data.data")
							this.$message({
							  message: '恭喜你，注册成功',
							  type: 'success'
							});
							_this.$router.push("/Login")
						})
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			},
			login(){
				this.$router.push('/login');
			}
		},
		mounted() {

		}
	}
</script>

<style scoped>
	.el-header,
	.el-footer {
		background-color: #B3C0D1;
		color: #333;
		text-align: center;
		line-height: 60px;
	}

	/* .el-aside {
		background-color: #D3DCE6;
		color: #333;
		text-align: center;
		line-height: 200px;
	} */

	.el-main {
		/*background-color: #E9EEF3;*/
		color: #333;
		text-align: center;
		line-height: 20px;
	}

	body>.el-container {
		margin-bottom: 40px;
	}

	/* .el-container:nth-child(5) .el-aside,
	.el-container:nth-child(6) .el-aside {
		line-height: 260px;
	}

	.el-container:nth-child(7) .el-aside {
		line-height: 320px;
	} */

	.mlogo {
		height: 60%;
		margin-top: 10px;
	}

	.demo-ruleForm {
		max-width: 500px;
		margin: 0 auto;
	}
</style>
