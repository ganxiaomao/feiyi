package com.github.springcloud.teccom.service;

import com.github.springcloud.teccom.vo.ResultVo;

/**
 * Created by ganzhen on 26/03/2018.
 */
public interface TecComService {

    public ResultVo saveCustomerInstallBroadBandRequest(String customerName, String customerPhone, String customerAddress);
}
