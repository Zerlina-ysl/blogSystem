import axios from 'axios'
import Element from 'element-ui'
import * as router from "vue-router";

//设置默认网址前缀
axios.defaults.baseURL="http://localhost:8081"

//前置过滤器拦截
axios.interceptors.request.use(config=>{

    return config
})

//后置拦截
axios.interceptors.response.use(response=>{
    let res= response.data

    console.log("======后置拦截======")
    console.log(res)
    console.log("==============")

    if(res.code===200){

        return response
    }
    else{
        // const msg = res.msg
        Element.Message.error("操作出现异常")
        return Promise.reject(response.data.msg)
    }



},
    error=>{
        console.log(error)
        //Error: Request failed with status code 400
        //     at createError (createError.js?e648:16)
        //     at settle (settle.js?5faf:17)
        //     at XMLHttpRequest.onloadend (xhr.js?b9e2:66)
        if(error.response.data){
            error.message = error.response.data.msg
            console.log("======后置拦截======")
            console.log(error.message)
            console.log("==============")
        }
        if(error.response.status === 401){

            store.commit("REMOVE_INFO")
            router.push("/login")
        }
        Element.Message.error(error.message)
        return Promise.reject(error)
    },



)