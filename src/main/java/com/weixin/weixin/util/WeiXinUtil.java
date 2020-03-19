package com.weixin.weixin.util;

import com.sun.deploy.net.HttpResponse;
import com.weixin.weixin.entry.AccessToken;
import lombok.Data;
import org.springframework.http.HttpEntity;

import java.io.IOException;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/19 15:13
 * 4
 */
@Data
public class WeiXinUtil {
    private static final String APPID = "wxfc73577ca8460519";
    private static final String APPSECRET = "b0b75d65ccb882f8b5526538b48121cb";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "utf-8");
                jsonObject = JSONObject.fromObject(result);

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
            httpPost.setEntity(new StringEntity(outStr, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            jsonObject = JSONObject.fromObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return jsonObject;
    }


    /**
     * 获取access_token
     *
     * @return
     */
    public static AccessToken getAccessToken() {
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if (jsonObject != null) {
            if (jsonObject.containsKey("access_token")) {
                token.setToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } else {
                System.out.println("获取access_token失败");
            }
        }
        return token;
    }
}

}
