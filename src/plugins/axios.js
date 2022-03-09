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
    console.log(res.msg)
    console.log("==============")

    if(res.code===200){

        return response
    }
    else{
        // const msg = res.msg
        Element.Message.error(res.msg)
        return Promise.reject(response.data.msg)
    }



},
    error=>{
        console.log(error)
        if(error.response.data){
            error.message = error.response.data.msg
            console.log("======后置拦截======")
            console.log(error.message)
            console.log("==============")
        }

        Element.Message.error(error.message)
        return Promise.reject(error)
    },



)