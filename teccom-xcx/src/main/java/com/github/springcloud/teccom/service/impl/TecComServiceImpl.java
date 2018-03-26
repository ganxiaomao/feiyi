package com.github.springcloud.teccom.service.impl;

import com.eaio.uuid.UUID;
import com.github.springcloud.teccom.dbdao.InstallBroadBandRequestDao;
import com.github.springcloud.teccom.dbentity.InstallBroadBandRequestEntity;
import com.github.springcloud.teccom.service.TecComService;
import com.github.springcloud.teccom.vo.ResultVo;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by ganzhen on 26/03/2018.
 */
@Service("tecComServiceImpl")
public class TecComServiceImpl implements TecComService {
    Logger logger = LoggerFactory.getLogger(TecComServiceImpl.class);

    @Autowired
    private InstallBroadBandRequestDao installBroadBandRequestDao;

    /**
     * 保存用户安装申请
     * @param customerName
     * @param customerPhone
     * @param customerAddress
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResultVo saveCustomerInstallBroadBandRequest(String customerName, String customerPhone, String customerAddress) {
        ResultVo rv = new ResultVo();
        if(Strings.isNullOrEmpty(customerName))
            return new ResultVo(false,null,"请填写姓名！");
        if(Strings.isNullOrEmpty(customerPhone))
            return new ResultVo(false,null,"请填写电话号码！");
        if(Strings.isNullOrEmpty(customerAddress))
            return new ResultVo(false,null,"请填写地址！");

        Date now = new Date();
        String id = new UUID().toString();
        InstallBroadBandRequestEntity entity = new InstallBroadBandRequestEntity();
        entity.setId(id);
        entity.setCreateTime(now);
        entity.setCustomerAddress(customerAddress);
        entity.setCustomerName(customerName);
        entity.setCustomerPhone(customerPhone);
        entity.setStatus(0);//初始未处理
        try{
            installBroadBandRequestDao.insert(entity);
            rv.setSuccess(true);
            rv.setObj(null);
            rv.setMsg("保存成功");
        }
        catch(Exception e){
            logger.info("error in TecComServiceImpl.saveCustomerInstallBroadBandRequest",e.getCause());
            rv.setMsg("网络异常，请稍后再试");
            rv.setObj(null);
            rv.setSuccess(false);
        }

        return rv;
    }
}
