<template>
    <div>
        <div id="login" v-on:mousedown.left="createLoves($event)" @click="removeSmallHert">
            <background ref="background"></background>
        </div>
        <el-container>

        <el-header>
            <img class="mlogo" src="../../img/logo.png">
        </el-header>
        <el-main>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="ruleForm.password"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                </el-form-item>
            </el-form>
        </el-main>

        </el-container>


    </div>
</template>

<script>

    import background from "../components/LoveH"
    export default {

        name: "Login",
        components:{background},
        data() {
            return {
                ruleForm: {
                    username: 'xiaoli',
                    password: '111111',
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
                    ],

                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //保存全局this
                        const _this = this
                        // alert('submit!');
                        this.$axios.post('/login',_this.ruleForm).then(res=>{
                            console.log(res.headers)
                            // console.log(res)
                            const jwt = res.headers['authorization'];
                            const userInfo = res.data.data;

                            console.log(userInfo);
//在页面关闭后保存用户信息
                                //把数据共享
                            _this.$store.commit("SET_TOKEN",jwt)
                            _this.$store.commit("SET_USERINFO",userInfo)
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
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            createLoves(){
                this.$refs.background.createLoves(event)
            },
            removeSmallHert(){
                this.$refs.background.removeSmallHert()
            }
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
    .mlogo{
        margin-top: 10px;
        height: 70%;
    }
    .demo-ruleForm{
        max-width: 420px;
        margin: 0 auto;
    }
    #login{
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }
</style>