package com.github.springcloud.teccom.controller;

import com.github.springcloud.teccom.service.TecComService;
import com.github.springcloud.teccom.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ganzhen on 26/03/2018.
 */
@RestController
@RequestMapping("teccom")
public class TecComController {
    Logger logger = LoggerFactory.getLogger(TecComController.class);

    @Autowired
    private TecComService tecComServiceImpl;

    @RequestMapping(value="installRequestApply",method= RequestMethod.GET)
    public ResponseEntity<?> installRequestApply(
            @RequestParam(value="customerName") String customerName,
            @RequestParam(value="customerPhone") String customerPhone,
            @RequestParam(value="customerAddress") String customerAddress){
        logger.info("in TecComController.installRequestApply: customerName="+customerName+",customerPhone="+customerPhone+",customerAddress="+customerAddress);
        ResultVo vo = tecComServiceImpl.saveCustomerInstallBroadBandRequest(customerName,customerPhone,customerAddress);
        return ResponseEntity.ok(vo);
    }
}
