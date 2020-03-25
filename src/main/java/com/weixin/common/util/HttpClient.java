package com.weixin.common.util;

import com.alibaba.fastjson.JSONObject;
import com.weixin.common.schedule.ScheduledTasks;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    /**
     * Get请求
     *
     * @param url
     * @return 注意事项 添加json object的jar包及依赖jar包
     * 添加httpclient，http core的 jar包
     */
    public static JSONObject doGetStr(String url) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * post请求
     *
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url, String outStr) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            jsonObject = JSONObject.parseObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
