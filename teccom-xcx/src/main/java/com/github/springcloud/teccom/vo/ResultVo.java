package com.github.springcloud.teccom.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ganzhen on 26/03/2018.
 */
@Data
public class ResultVo implements Serializable {
    private boolean success = false;
    private Object obj;
    private String msg;

    public ResultVo(){}

    public ResultVo(boolean success,Object obj, String msg){
        this.success = success;
        this.obj = obj;
        this.msg = msg;
    }
}
