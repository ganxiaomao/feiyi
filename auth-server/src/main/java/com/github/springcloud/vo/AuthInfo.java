package com.github.springcloud.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AuthInfo {
    private String id;
    private Date expDate;

    public AuthInfo(String id, Date expDate){
        this.id = id;
        this.expDate = expDate;
    }

    public AuthInfo(){}
}
