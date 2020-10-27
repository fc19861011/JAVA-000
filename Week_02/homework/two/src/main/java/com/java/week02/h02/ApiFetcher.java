package com.java.week02.h02;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author fc
 * @date 2020-10-27
 * 题目：
 * 写一段代码，使用 HttpClient 或 OKHttp 访问 http://localhost:8801
 */
public class ApiFetcher {
    /**
     * API访问地址
     */
    private final String API_URI = "http://localhost:8801";

    /**
     * 通过HttpClient获取api返回结果
     *
     * @param uri 请求地址
     * @return api返回结果
     * 依赖包：httpclient-4.5.12.jar
     * httpclient编译依赖包如下：
     * |- commons-codec-1.11.jar
     * |- commons-codec-1.11.jar
     * |- commons-logging-1.2.jar
     */
    private String fetchByHttpClient(String uri) {
        HttpResponse response;
        String result = "";
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过OKHttp获取api返回结果
     *
     * @param uri 请求地址
     * @return api返回结果
     * 依赖包：okhttp-4.0.0.jar
     * okhttp编译依赖包如下：
     * |- okio-2.2.2.jar
     * |- kotlin-stdlib-1.3.40.jar
     */
    private String fetchByOkHttp(String uri) {
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(uri).build();
        try(Response response = client.newCall(request).execute()) {
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        ApiFetcher fetcher = new ApiFetcher();
        String apiResult;
        apiResult = fetcher.fetchByHttpClient(fetcher.API_URI);
        System.out.println("HttpClient：" + apiResult);
        apiResult = fetcher.fetchByOkHttp(fetcher.API_URI);
        System.out.println("OkHttp：" + apiResult);
    }
}
