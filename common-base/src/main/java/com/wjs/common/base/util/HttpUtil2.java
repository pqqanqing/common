package com.wjs.common.base.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

public class HttpUtil2 {

    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
    private static RequestConfig requestConfig;

    private static final int CONNECT_TIME = 5000;

    static {
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(200);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIME)
                .setConnectTimeout(CONNECT_TIME).setSocketTimeout(CONNECT_TIME).build();
    }

    public static CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
        return httpClient;
    }

    public static String doGet(String url) {
        HttpRequestBase httpRequestBase = new HttpGet(url);
        CloseableHttpClient closeableHttpClient = getCloseableHttpClient();
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpRequestBase);
            HttpEntity httpEntity = HttpUtil.getHttpEntity(response);
            String result = EntityUtils.toString(httpEntity);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            CloseableUtil.close(response, closeableHttpClient);
        }
    }

    public static String doGet2(String url) {
        CloseableHttpClient closeableHttpClient = HttpUtil.getCloseableHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        String result = null;
        try {
            closeableHttpResponse = HttpUtil.getCloseableHttpResponse(closeableHttpClient, url, HttpGet.METHOD_NAME, 5000);
            HttpEntity httpEntity = HttpUtil.getHttpEntity(closeableHttpResponse);
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            return e.getMessage();
        } finally {
            CloseableUtil.close(closeableHttpResponse, closeableHttpClient);
        }
        return result;
    }

    public static String doPost(String url, String params) {
        CloseableHttpClient closeableHttpClient = HttpUtil.getCloseableHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            if (isNoneBlank(params)) {
                StringEntity entity = new StringEntity(params, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            httpPost.setConfig(requestConfig);
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = HttpUtil.getHttpEntity(closeableHttpResponse);
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            return e.getMessage();
        } finally {
            CloseableUtil.close(closeableHttpResponse, closeableHttpClient);
        }
        return result;
    }

}
