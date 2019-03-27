package com.rc.dubbo.two.server.service;

import com.google.common.base.Strings;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

/**
 * http通信服务
 */
@Service
public class HttpService {

    private static final Logger log = LoggerFactory.getLogger(HttpService.class);

    private OkHttpClient client = new OkHttpClient();

    /**
     * 构造通用的get请求
     *
     * @param url
     * @param headerMap
     * @return
     * @throws Exception
     */
    private Request commonGetRequest(String url, Map<String, String> headerMap) throws Exception {
        Request.Builder builder = new Request.Builder();

        Request request;
        if (headerMap != null && headerMap.keySet() != null && headerMap.keySet().size() > 0) {
            Headers headers = Headers.of(headerMap);
            request = builder.get().url(url).headers(headers).build();
        } else {
            request = builder.get().url(url).build();
        }
        return request;
    }


    private Request.Builder commonPostBuilder(String url, Map<String, String> headerMap) throws Exception {
        Request.Builder builder = new Request.Builder();

        Request request;
        if (headerMap != null && headerMap.keySet() != null && headerMap.keySet().size() > 0) {
            Headers headers = Headers.of(headerMap);
            builder = builder.get().url(url).headers(headers);
        } else {
            builder = builder.get().url(url);
        }
        return builder;
    }


    /**
     * get请求
     *
     * @param url
     * @param headerMap
     * @return
     * @throws Exception
     */
    public String get(String url, Map<String, String> headerMap) throws Exception {
        Request request = commonGetRequest(url, headerMap);

        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.error("发送同步-get请求发生异常: url={} ", e.fillInStackTrace());
        }
        return null;
    }

    /**
     * post 请求
     *
     * @param url
     * @param headerMap
     * @param contentType
     * @param data
     * @return
     * @throws Exception
     */
    public String post(String url, Map<String, String> headerMap, String contentType, String data) throws Exception {
        Request.Builder builder = commonPostBuilder(url, headerMap);

        Request request;
        if (!Strings.isNullOrEmpty(data) && !Strings.isNullOrEmpty(contentType)) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), data);
            request = builder.post(requestBody).build();
        } else {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            request = builder.post(formBodyBuilder.build()).build();
        }

        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.error("发送同步-post请求发生异常: url={} ", e.fillInStackTrace());
        }
        return null;
    }

    /**
     * post请求--无请求体
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String post(String url) throws Exception {
        Request.Builder builder = commonPostBuilder(url, null);
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Request request = builder.post(formBodyBuilder.build()).build();

        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.error("发送同步-post请求--无请求体 发生异常: url={} ", e.fillInStackTrace());
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url
     * @param headerMap
     * @param bodyParams 请求体数据-map格式
     * @return
     * @throws Exception
     */
    public String post(String url, Map<String, String> headerMap, Map<String, String> bodyParams) throws Exception {
        Request.Builder builder = commonPostBuilder(url, headerMap);

        RequestBody body = setRequestBody(bodyParams);
        Request request = builder
                .post(body)
                .url(url)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.error("发送同步-post请求--请求体数据-map格式 发生异常: url={} ", e.fillInStackTrace());
        }
        return null;
    }

    private RequestBody setRequestBody(Map<String, String> params) {
        FormBody.Builder formBuilder = new FormBody.Builder();

        if (params != null && params.keySet() != null && params.keySet().size() > 0) {
            String key;
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                key = iterator.next();
                formBuilder.add(key, params.get(key));
            }
        }
        return formBuilder.build();
    }
}
