<template>
    <div>
<!--        引入公用头部-->
        <div id="login" v-on:mousedown.left="createLoves($event)" @click="removeSmallHert">
            <background ref="background"></background>
        </div>
        <Header></Header>

        <div class="mcontent">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="ruleForm.title"></el-input>
                </el-form-item>


                <el-form-item label="摘要" prop="description">
                    <el-input type="textarea" v-model="ruleForm.description"></el-input>
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <mavon-editor v-model="ruleForm.content"></mavon-editor>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">立即发表</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>




        </div>

<!--        edit-->
    </div>
</template>


<script>
    import Header from "./Header";

    import background from "./LoveH";
    export default {
        name: "BlogEdit.vue",
        components: {Header,background},
        data() {
            return {
                ruleForm: {
                    id :'',
                    title:'',
                    description:'',
                    content:'',
                    username: '',
                },
                rules: {
                    title: [
                        { required: true, message: '请输入标题', trigger: 'blur' },
                        { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: '请输入摘要', trigger: 'blur' }
                    ],
                    content: [
                        {  required: true, message: '请输入内容', trigger: 'blur' }
                    ],

                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this
                        this.ruleForm.username=this.$store.getters.getUser.username
                        this.$axios.post('/blog/edit',this.ruleForm,{
                            headers:{
                                "Authorization":localStorage.getItem("token")

                            }
                        }).then( res =>{

                            console.log(localStorage.getItem("token"))
                            console.log(res)
                            this.$alert('发表成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                _this.$router.push("/blogs")

                                }
                            });
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
        },
        created(){
            //页面刚加载时
            const blogId = this.$route.params.blogId
            if(blogId) {

                const _this = this
                this.$axios.get("/blog/" + blogId).then(res => {
//通过v-model 可以在编辑内容时回显数据；
                    const blog = res.data.data
                    _this.id = blog.id
                    _this.ruleForm.id = blog.id
                    _this.ruleForm.title = blog.title
                    _this.ruleForm.description = blog.description
                    _this.ruleForm.content = blog.content

                })
            }
        }
    }
</script>

<style scoped>
    .mcontent {
    text-align: center;
    }
    #login{
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }

</style>