package com.wjs.common.base.request;

import com.wjs.common.base.annotation.StatusAndClassNum;
import com.wjs.common.base.util.CloseableUtil;
import com.wjs.common.base.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by panqingqing on 16/6/1.
 */
@StatusAndClassNum(superClass = Request.class, number = "1", describe = "http请求调用", parasitClass = Request.class)
public class HttpRequest extends Request {

    private Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    @Override
    public String exec(String url, String method, int overtime) {
        CloseableHttpClient closeableHttpClient = HttpUtil.getCloseableHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        String result = null;
        try {
            closeableHttpResponse = HttpUtil.getCloseableHttpResponse(closeableHttpClient, url, method, overtime);
            StatusLine statusLine = HttpUtil.getStatusLine(closeableHttpResponse);
            this.statusCode = statusLine.getStatusCode();
            HttpEntity httpEntity = HttpUtil.getHttpEntity(closeableHttpResponse);
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            logger.error("调用url:" + url + "异常,异常信息:" + e.getMessage());
            return e.getMessage();
            //这个地方不往外抛异常,把异常入库!
        } finally {
            CloseableUtil.close(closeableHttpResponse, closeableHttpClient);
        }
        return result;
    }
}
