package com.wjs.common.base.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.http.client.methods.HttpPost.METHOD_NAME;

/**
 * Created by panqingqing on 16/6/1.
 */
public class HttpUtil {
    public static CloseableHttpClient getCloseableHttpClient() {
        return HttpClients.createDefault();
    }

    public static CloseableHttpResponse getCloseableHttpResponse(CloseableHttpClient closeableHttpClient, String url, String method, int connectTime) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTime).setSocketTimeout(connectTime).build();
        HttpRequestBase httpRequestBase = null;
        if (StringUtils.equals(METHOD_NAME, method)) {
            httpRequestBase = new HttpPost(url);
        } else {
            httpRequestBase = new HttpGet(url);
        }
        httpRequestBase.setConfig(requestConfig);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpRequestBase);
        return closeableHttpResponse;
    }

    public static CloseableHttpResponse get(CloseableHttpClient closeableHttpClient, String url, int connectTime) throws IOException {
        return getCloseableHttpResponse(closeableHttpClient, url, HttpGet.METHOD_NAME, connectTime);
    }

    public static CloseableHttpResponse post(CloseableHttpClient closeableHttpClient, String url, int connectTime) throws IOException {
        return getCloseableHttpResponse(closeableHttpClient, url, HttpPost.METHOD_NAME, connectTime);
    }

    public static CloseableHttpResponse post4Params(CloseableHttpClient closeableHttpClient, String url, int connectTime, String params) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTime).setSocketTimeout(connectTime).build();
        HttpPost httpPost = new HttpPost(url);
        if (isNoneBlank(params)) {
            StringEntity entity = new StringEntity(params, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        return closeableHttpResponse;
    }

    public static CloseableHttpResponse getPostCloseableHttpResponseByJson(CloseableHttpClient closeableHttpClient, String url, int connectTime, JSONObject params) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTime).setSocketTimeout(connectTime).build();
        HttpPost httpPost = new HttpPost(url);
        if (nonNull(params)) {
            StringEntity entity = new StringEntity(params.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        return closeableHttpResponse;
    }

    public static StatusLine getStatusLine(CloseableHttpResponse closeableHttpResponse) {
        if (null == closeableHttpResponse) {
            throw new RuntimeException("closeableHttpResponse =" + closeableHttpResponse);
        }
        StatusLine statusLine = closeableHttpResponse.getStatusLine();
        return statusLine;
    }

    public static HttpEntity getHttpEntity(CloseableHttpResponse closeableHttpResponse) {
        if (null == closeableHttpResponse) {
            throw new RuntimeException("closeableHttpResponse =" + closeableHttpResponse);
        }
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        return httpEntity;
    }

}
