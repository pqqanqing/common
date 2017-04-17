package com.wjs.common.base.request;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

/**
 * Created by panqingqing on 16/6/1.
 */
public abstract class Request {

    public static final int OVERTIME = 10000;
    public static final String METHOD_POST = HttpPost.METHOD_NAME;
    public static final String METHOD_GET = HttpGet.METHOD_NAME;

    protected int statusCode;

    public String defaultExec(String url) {
        return exec(url, METHOD_POST, OVERTIME);
    }

    public abstract String exec(String url, String method, int overtime);

    public int getStatusCode() {
        return statusCode;
    }

}
