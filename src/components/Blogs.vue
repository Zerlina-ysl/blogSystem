<template>
    <div>
        <div id="login" v-on:mousedown.left="createLoves($event)" @click="removeSmallHert">
            <background ref="background"></background>
        </div>
        <Header>
        </Header>

        <div class="block">
            <el-timeline>
                <el-timeline-item :timestamp="blog.created" placement="top" v-for="(blog,key) in blogs" :key=key
                                  v-show="flag">

                    <el-card>
                        <h4>
                            <!--                                                                key:value 路由跳转携带参数-->
                            <router-link :to="{name:'BlogDetail',params:{blogId:blog.id}}">
                                {{blog.title}}
                            </router-link>
                        </h4>
                        <p>{{blog.description}}</p>
                    </el-card>
                </el-timeline-item>


            </el-timeline>
            <el-pagination class="mpage" background layout="prev, pager, next" :current-page="currentPage"
                           :page-size="pageSize" :total="total" @current-change=page>
            </el-pagination>

        </div>


        <!--        blogs-->
    </div>
</template>

<script>
    import Header from "./Header"
    import background from "./LoveH"

    export default {
        name: "Blogs",
        components: {Header, background},
        data() {
            return {
                //博客的数据 根据后端数据库写
                blogs: {},
                currentPage: 1,
                total: 0,
                pageSize: 2,
                flag: false
            }
        },
        methods: {
            page(currentPage) {
                const _this = this
                _this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {
                    //初始化
                    // console.log(res)
                    var data = res.data.data
                    _this.blogs = data.records
                    _this.currentPage = data.current
                    _this.total = data.total
                    _this.pageSize = data.size
                })
            },
            createLoves() {
                this.$refs.background.createLoves(event)
            },
            removeSmallHert() {
                this.$refs.background.removeSmallHert()
            }

        },
        created() {
            if (this.$store.getters.getUser.username != null) {
                this.flag = true
                this.page(1)

            }
        }
    }
</script>

<style scoped>
    .block {
        margin: 20px;
    }


    .mpage {
        margin: 0 auto;
        text-align: center;
    }

    /*#login{*/
    /*    position: absolute;*/
    /*    top: 0;*/
    /*    left: 0;*/
    /*    width: 100%;*/
    /*    height: 100%;*/
    /*}*/
</style>