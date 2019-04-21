package com.example.pc.lifeassistant.util;


import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 网络请求封装类HttpEntity
 * Created by pc on 2019/2/19.
 */

public class HttpEntity {

    private static int connectionTimeout = 10; //网络连接超时
    private static int readTimeout = 30; //内容读取超时

    private String url;
    private OkHttpClient okHttpClient;
    private Request request;
    private Callback callback;

    public HttpEntity(Callback callback) {
        this.url = HttpConstant.URL;
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
        this.request = new Request.Builder().url(HttpConstant.URL).build();
        this.callback = callback;
    }


    public HttpEntity(String json, Callback callback){
        this.url = HttpConstant.URL;
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        this.request = new Request.Builder()
                .url(HttpConstant.URL)
                .build();
        this.callback = callback;
    }


    public HttpEntity(Request request, Callback callback) {
        this.url = request.url().url().toString();
        this.request = request;
        this.callback = callback;
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
    }


    public void setRequestObject(String url, String method, RequestBody body) {
        this.url = url;
        request = new Request.Builder().url(url).method(method, body).build();

    }


    public void request() {
        okHttpClient.newCall(request).enqueue(callback);
    }
}
