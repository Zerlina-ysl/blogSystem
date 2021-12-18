package com.company.vueblog.common.lang;

import lombok.Data;

@Data
public class Result {
    //200是正常
    private int code;
    private String msg;
    private Object data;


    public static Result status(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;

    }
    public static Result succ(Object data){
      return status(200,"操作成功",data);

    }
    public static Result fail(String msg){
        return status(404,msg,null);
    }//?
    public static Result fail(int code,String msg,Object data){
        return status(404,msg,null);
    }//?


}
