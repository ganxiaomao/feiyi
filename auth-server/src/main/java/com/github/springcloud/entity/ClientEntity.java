package com.github.springcloud.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ganzhen on 17/01/2018.
 */
@Table(name = "auth_feiyi_client")
@Data
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "expire_interval")
    private Long expireInterval;

    @Column(name = "expire_time")
    private Date expireTime;

    @Column(name = "user_code")
    private String userCode;
}
