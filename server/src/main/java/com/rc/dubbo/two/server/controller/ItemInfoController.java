package com.rc.dubbo.two.server.controller;

import com.rc.dubbo.one.api.enums.StatusCode;
import com.rc.dubbo.one.api.response.BaseResponse;
import com.rc.dubbo.one.api.service.IDubboItemInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemInfoController {

    private static final Logger log = LoggerFactory.getLogger(ItemInfoController.class);

    private static final String prefix = "item";


    @Autowired
    private IDubboItemInfoService dubboItemInfoService;

    @RequestMapping(value = prefix + "/list", method = RequestMethod.GET)
    public BaseResponse list() {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            BaseResponse dubboOneResp = dubboItemInfoService.listItemInfos();
            if (dubboOneResp != null && dubboOneResp.getCode() == 0) {
                baseResponse.setData(dubboOneResp.getData());
            }
        } catch (Exception e) {
            log.error("列表查询服务-调用业务实现逻辑-发生异常：", e.fillInStackTrace());
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }

    @RequestMapping(value = prefix + "/page/list", method = RequestMethod.GET)
    public BaseResponse pageList(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo <= 0 || pageSize == null || pageSize <= 0) {
            pageNo = 1;
            pageSize = 2;
        }
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            BaseResponse dubboOneResp = dubboItemInfoService.listPageItems(pageNo, pageSize);
            if (dubboOneResp != null && dubboOneResp.getCode() == 0) {
                baseResponse.setData(dubboOneResp.getData());
            }
        } catch (Exception e) {
            log.error("列表查询服务-分页查询-调用业务实现逻辑-发生异常：", e.fillInStackTrace());
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }

    @RequestMapping(value = prefix + "/page/list/params", method = RequestMethod.GET)
    public BaseResponse pageListParams(Integer pageNo,Integer pageSize,String search) {
        if (pageNo == null || pageNo <= 0 || pageSize == null || pageSize <= 0) {
            pageNo = 1;
            pageSize = 2;
        }
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            BaseResponse dubboOneResp = dubboItemInfoService.listPageItemsParams(pageNo, pageSize, search);
            if (dubboOneResp != null && dubboOneResp.getCode() == 0) {
                baseResponse.setData(dubboOneResp.getData());
            }
        } catch (Exception e) {
            log.error("列表查询服务-分页查询-模糊查询-调用业务实现逻辑-发生异常：", e.fillInStackTrace());
            baseResponse = new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }
}
