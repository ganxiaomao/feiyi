package com.github.springcloud.stockcrawler.vo;

import java.io.Serializable;

/**
 * Created by ganzhen on 11/02/2018.
 */
public class ResultVo implements Serializable{
    private boolean success = false;
    private Object obj;
    private String msg;

    public ResultVo(){}

    public ResultVo(boolean success, Object obj, String msg){
        this.success = success;
        this.obj = obj;
        this.msg = msg;
    }

    public void fail(Object obj, String msg){
        this.success = false;
        this.obj = obj;
        this.msg = msg;
    }

    public void success(Object obj, String msg){
        this.success = true;
        this.obj = obj;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getObj() {
        return obj;
    }

    public String getMsg() {
        return msg;
    }
}
