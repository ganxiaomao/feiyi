package com.github.springcloud.teccom.dbdao;

import com.github.springcloud.teccom.MyMapper;
import com.github.springcloud.teccom.dbentity.InstallBroadBandRequestEntity;

import java.util.List;

/**
 * Created by ganzhen on 26/03/2018.
 */
public interface InstallBroadBandRequestDao extends MyMapper<InstallBroadBandRequestEntity> {

    public List<InstallBroadBandRequestEntity> selectDatasByCustomerName();
}
