package com.rc.dubbo.two.server.controller;

import com.rc.dubbo.one.api.enums.StatusCode;
import com.rc.dubbo.one.api.response.BaseResponse;
import com.rc.dubbo.two.server.request.RecordPushRequest;
import com.rc.dubbo.two.server.service.OrderRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

@RestController
public class OrderRecordController  {

    private static final Logger log = LoggerFactory.getLogger(OrderRecordController.class);

    private static final String prefix = "order";

    @Autowired
    OrderRecordService orderRecordService;

    /**
     * 面向客户，用户下单
     */
    @RequestMapping(value=prefix+"/record/push", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    public BaseResponse pushRecord(@RequestBody RecordPushRequest pushRequest) {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            log.info("接收到请求数据: {} " + pushRequest );

            //TODO:处理用户下单数据-发起用户下单接口的调用
            orderRecordService.pushOrderV2(pushRequest);
        } catch (Exception e) {
            baseResponse = new BaseResponse(StatusCode.Fail);
            log.error("面向客户，用户下单-发生异常: {} " + e.fillInStackTrace());
        }
        return baseResponse;
    }
}
