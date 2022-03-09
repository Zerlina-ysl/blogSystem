<template>
    <div class="mcontent">

<!--   定义头部-->

        <h3 v-if="user.username!='请先登录'">欢迎来到{{user.username}}的博客</h3>
<!--        头像-->
        <div class="block">
                <el-avatar :size="100" :src="this.user.avatar" ></el-avatar>
            <el-upload
                    class="upload-demo"
                    action=""
                    :http-request="uploadImage"
                    :show-file-list="false"
                    :limit="1"
                    :before-upload="beforeAvatarUpload">
                <el-button size="small" type="primary">上传头像</el-button>

            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
            <div>{{user.username}}</div>

        </div>

    <div class="maction">
        <span>
            <el-link type="primary" href="/blogs">主页</el-link></span>
        <el-divider direction="vertical"></el-divider>
        <span>
            <el-link type="success" href="/blog/add">发表博客</el-link></span>
        <el-divider direction="vertical"></el-divider>

            <span v-show="!hasLogin">
          <el-link type="primary" @click="login">登录</el-link>
            </span>
            <span v-show="hasLogin">
          <el-link type="danger" @click="logout">退出</el-link>
            </span>

    </div>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data(){
            return {
                user: {
                    username: '请先登录',
                    avatar: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
                },
                hasLogin:false,
            }
        },
        methods:{
            logout(){
                const _this = this
                _this.$axios.get("/logout",{
                    //需要认证权限 所以需要传递相关消息

                    headers:{
                        "Authorization":localStorage.getItem("token")
                    }
                }).then(res => {
                    //response应该相当于一个形参
                    console.log(res)
                    _this.$store.commit("REMOVE_INFO")
                    _this.$router.push("/login")

                })
                },
            login(){
                this.$router.push("/login")
            },

            uploadImage(param) {
                const formData = new FormData()
                formData.append('file', param.file)
                const url="/uploadAvatar?username="+this.user.username;
                this.$axios.post(url, formData).then(data => {

                    alert('头像上传成功')
                    this.user.avatar = this.$store.getters.getUser.avatar
                    console.log(this.user.avatar)
                }).catch(response => {

                    alert('头像上传失败')
                })
            },


            beforeAvatarUpload(file) {
                console.log(file)
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
        },
        created(){
            console.log(this.$store.getters.getUser)
            if(this.$store.getters.getUser.username!=null){
                this.user.username = this.$store.getters.getUser.username

                this.user.avatar = this.$store.getters.getUser.avatar
                this.hasLogin = true
                console.log("===hasLogin===")
                console.log(this.hasLogin)
            }
        }
    }
</script>

<style scoped>

    .mcontent {

        max-width: 960px;
        margin: 0 auto ;
        text-align: center;
    }
    .maction {
        margin: 10px;
    }
</style>