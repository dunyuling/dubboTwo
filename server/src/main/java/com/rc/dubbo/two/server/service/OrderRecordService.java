package com.rc.dubbo.two.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.dubbo.two.server.request.RecordPushRequest;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderRecordService {


    private static final Logger log = LoggerFactory.getLogger(OrderRecordService.class);

    private OkHttpClient okHttpClient = new OkHttpClient();

    private static final String url = "http://localhost:9013/v1/record/push";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    HttpService httpService;

    /**
     * 处理Controller层传过来的用户下单数据
     *
     * @param recordPushRequest
     */
    public void pushOrder(RecordPushRequest recordPushRequest) throws Exception {

        try {
            //TODO: 构造builder
            Request.Builder builder = new Request.Builder().url(url);

            //TODO: 构造请求头，请求体
            builder.header("Content-Type", "application/json");

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(recordPushRequest));

            //TODO: 构造请求
            Request request = builder.post(requestBody).build();

            //TODO: 发起请求
            Response response = okHttpClient.newCall(request).execute();
            log.info(response.body().toString());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 处理Controller层传过来的用户下单数据
     *
     * @param recordPushRequest
     */
    public void pushOrderV2(RecordPushRequest recordPushRequest) throws Exception {

        try {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type","application/json");
            String result = httpService.post(url, headerMap, "application/json", objectMapper.writeValueAsString(recordPushRequest));

            log.info("pushOrderV2: {} ", result);
        } catch (Exception e) {
            throw e;
        }
    }
}
