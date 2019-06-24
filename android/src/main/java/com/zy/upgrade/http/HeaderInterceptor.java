package com.zy.upgrade.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    Map<String, String> headerParamsMap = new HashMap<>(); // 公共 Headers 添加

    public HeaderInterceptor(Map<String, String> headerParamsMap) {
        this.headerParamsMap = headerParamsMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        // process header params inject
        Headers.Builder headerBuilder = request.headers().newBuilder();
        // 以 Entry 添加消息头
        if (headerParamsMap.size() > 0) {
            Iterator iterator = headerParamsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
            requestBuilder.headers(headerBuilder.build());
        }
        // process header params end
        request = requestBuilder.build();
        return chain.proceed(request);
    }
}
