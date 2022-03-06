<template>
    <div>
        <div id="login" v-on:mousedown.left="createLoves($event)" @click="removeSmallHert">
            <background ref="background"></background>
        </div>

        <span v-show="!isRegister">
        <el-container>
        <el-header>
            <img class="mlogo" src="../assets/img/OIP-C.jpg">
        </el-header>
        <el-main>

            <el-form :model="loginForm" :rules="rules1" ref="loginForm" label-width="120px" class="demo-ruleForm">

                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="loginForm.password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
                    <el-button @click="isRegister=true">注册账号</el-button>
                </el-form-item>
            </el-form>
        </el-main>
        </el-container>
    </span>





        <span v-show="isRegister">
    <el-form :model="registerForm" :rules="rules2" ref="registerForm" label-width="120px" class="demo-ruleForm">


                <el-form-item label="用户名" prop="username">
                    <el-input v-model="registerForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="registerForm.password" ></el-input>
                </el-form-item>

                   <el-form-item prop="email"label="邮箱">
                       <el-input v-model="registerForm.email"></el-input>
                   </el-form-item>

<br>

                <el-form-item>
                    <el-button @click="isRegister=false">转向登录</el-button>
                    <el-button type="primary" @click="register('registerForm')">注册</el-button>
                </el-form-item>

            </el-form>


        </span>


    </div>
</template>

<script>

    import background from "./LoveH"

    export default {

        name: "Login",
        components: {background},
        data() {

            return {
                isRegister: false,
                loginForm: {
                    username: '',
                    password: '',

                },
                registerForm:{
                    password: '',
                    email: '',
                    username: '',
                },
                rules1: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
                    ],
                },
                rules2: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
                    ],


                    email: [
                        { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                    ],
                },
            };
        },

        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //保存全局this
                        const _this = this
                        // alert('submit!');
                        this.$axios.post('/login', _this.loginForm).then(res => {
                            console.log(res.headers)
                            // console.log(res)
                            //存储到localStorage 方便全局使用
                            const jwt = res.headers['authorization'];
                            const userInfo = res.data.data;

                            console.log(userInfo);
//在页面关闭后保存用户信息
                            //把数据共享
                            _this.$store.commit("SET_TOKEN", jwt)
                            _this.$store.commit("SET_USERINFO", userInfo)
                            //获取userInfo
                            // console.log(this.$store.getters.getUserInfo)
                            _this.$router.push("/blogs")
                        })

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            register(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //保存全局this
                        const _this = this
                        // alert('submit!');
                        this.$axios.post('/register',_this.registerForm).then(res=>{
                            console.log(res.headers)
                            // console.log(res)
                            //存储到localStorage 方便全局使用
                            const jwt = res.headers['authorization'];
                            const userInfo = res.data.data;

                            console.log(userInfo);
                            //把数据共享
                            _this.$store.commit("SET_TOKEN",jwt)
                            _this.$store.commit("SET_USERINFO",userInfo)
                            //获取userInfo
                            // console.log(this.$store.getters.getUserInfo)
                            _this.$router.push("/blogs")
                        })

                    } else {
                        console.log('error register!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },


            createLoves() {
                this.$refs.background.createLoves(event)
            },
            removeSmallHert() {
                this.$refs.background.removeSmallHert()
            },
        }
    }


</script>

<style scoped>

    .el-header,
    .el-footer {
        background-color: #b3c0d1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-main {
        /* background-color: #e9eef3; */
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    .mlogo {
        margin-top: 10px;
        height: 70%;
    }

    .demo-ruleForm {
        max-width: 420px;
        margin: 0 auto;
    }


    #login {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }

</style>