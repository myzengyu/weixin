package com.weixin.weixin.util;

import com.alibaba.fastjson.JSONObject;
import com.weixin.weixin.entry.AccessToken;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/19 15:13
 * 4
 */
@Data
public class WeiXinUtil {
    private static final String APP_ID = "wxfc73577ca8460519";
    private static final String APP_SECRET = "b0b75d65ccb882f8b5526538b48121cb";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APP_ID&secret=APP_SECRET";

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static JSONObject sendGet(String url) throws IOException {
        JSONObject fromObject = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        //创建get请求
        HttpGet httpGet = new HttpGet(url);
        //执行请求
        response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200) {
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            fromObject = JSONObject.parseObject(result);
        }
        httpClient.close();
        if (response != null) {
            response.close();
        }
        return fromObject;
    }


    /**
     * 获取access_token
     *
     * @return
     */
    public static AccessToken getAccessToken() throws IOException {
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APP_ID", APP_ID).replace("APP_SECRET", APP_SECRET);
        JSONObject jsonObject = sendGet(url);
        if (jsonObject != null) {
            if (jsonObject.containsKey("access_token")) {
                token.setToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInteger("expires_in"));
            } else {
                System.out.println("获取access_token失败");
            }
        }
        return token;
    }
}

