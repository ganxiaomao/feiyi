package com.github.springcloud.teccom.dbentity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户安装宽带申请
 * Created by ganzhen on 26/03/2018.
 */
@Table(name = "teccom_install_broadband_request")
@Data
public class InstallBroadBandRequestEntity {
    @Id
    @Column(name = "Id")
    private String id;

    /**
     * 用户姓名
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 用户电话
     */
    @Column(name = "customer_phone")
    private String customerPhone;

    /**
     * 用户地址
     */
    @Column(name = "customer_address")
    private String customerAddress;

    /**
     * 记录创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 记录状态：0，未处理；1，已处理
     */
    @Column(name = "status")
    private int status;
}
