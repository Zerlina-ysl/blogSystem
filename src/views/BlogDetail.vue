<template>
    <div>

<!--        detail-->
        <div id="login" v-on:mousedown.left="createLoves($event)" @click="removeSmallHert">
            <background ref="background"></background>
        </div>
        <Header>

        </Header>



        <div class="mblog">
            <h2>
                {{blog.title}}
            </h2>

            <el-link icon="el-icon-edit">
                <router-link :to="{name:'BlogEdit',params:{blogId:blog.id}}" v-if="ownBlog">编辑
                </router-link>
            </el-link>
            <el-divider direction="vertical"></el-divider>

                <el-link icon="el-icon-delete" v-if="ownBlog" round @click="delblog" class="del" style="color: red">
                    删除
                </el-link>
            <el-divider></el-divider>
            <div v-html="blog.content" class="markdown-body"></div>

        </div>

    </div>
</template>


<script>
    import Header from "../components/Header"
    import 'github-markdown-css'
    import background from "../components/LoveH"
    export default {

        name: "BlogDetail.vue",
        components:{Header,background},
        data(){
            return {
                blog:{
                    id:"",
                    title: "",
                    content: ""
                },
                ownBlog:false
            }
        },
        methods:{
            createLoves(){
                this.$refs.background.createLoves(event)
            },
            removeSmallHert(){
                this.$refs.background.removeSmallHert()
            },
            delblog(){
                const blogId = this.$route.params.blogId
                console.log(blogId)
                const _this = this
                if(blogId){
                    this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        _this.$axios.post(`/blogdel/${blogId}`, null, {
                            headers: {
                                "Authorization": localStorage.getItem("token")
                            }
                        }).then(res => {
                            this.$message({
                                type: 'success',
                                message: res.data.data
                            });
                            _this.$router.push("/blogs")
                        })

                    }).catch(() => {

                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }

            }
        },
        created(){

            const blogId = this.$route.params.blogId
            if(blogId) {
                console.log(blogId)

                const _this = this
                this.$axios.get("/blog/" + blogId).then(res => {
//通过v-model 可以在编辑内容时回显数据；
                    const blog = res.data.data
                    _this.blog.id = blog.id
                    _this.blog.title = blog.title
                    //对内容进行markdown格式渲染
                    var MarkDownIt = require("markdown-it")
                    var md  = new MarkDownIt()
                    var result = md.render(blog.content)

                    _this.blog.content = result

                    _this.ownBlog = (blog.userId===_this.$store.getters.getUser.id)

                })
            }
        }
    }
</script>


<style scoped>
    .mblog {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 1000%;
        min-height: 700px;
        padding:20px 15px;
    }
    #login{
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }

</style>