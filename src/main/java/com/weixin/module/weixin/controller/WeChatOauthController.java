package com.weixin.module.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.weixin.common.util.HttpClient;
import com.weixin.common.util.PropertiesUtils;
import com.weixin.common.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Controller
@RequestMapping("weChat")
public class WeChatOauthController {

    @Autowired
    private RedisUtils redisUtils;

    private final static String WECHAT_KEY = "weChat";

    /**
     * 微信授权获取code方法
     *
     * @throws Exception
     */

    @RequestMapping(value = "oauth", method = RequestMethod.GET)
    public void weChatOauth(HttpServletResponse response) throws Exception {
        //获取微信重定向地址
        String wechat_redirect = PropertiesUtils.getString("WECHAT_REDIRECT");
        //请求微信获取code的地址https://open.weixin.qq.com/connect/oauth2/authorize?appid=APP_ID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
        String url = PropertiesUtils.getString("GRT_CODE");
        //对回调地址进行编码
        String encode_url = URLEncoder.encode(wechat_redirect + "weChat/invoke", "utf-8");
        String replace_url = url.replace("APP_ID", PropertiesUtils.getString("APP_ID"))
                .replace("REDIRECT_URI", encode_url);
        System.out.println(replace_url);
        response.sendRedirect(replace_url);
    }

    @RequestMapping("invoke")
    public void OauthInvoke(HttpServletRequest request) {
        String code = request.getParameter("code");

        System.out.println(code);
        //获取oauth_access_token
        String replace = PropertiesUtils.getString("OAUTH_ACCESS_TOKEN");
        String oauth_access_token = replace.replace("APP_ID", PropertiesUtils.getString("APP_ID"))
                .replace("SECRET", PropertiesUtils.getString("APP_SECRET"))
                .replace("CODE", code);
        JSONObject jsonObject = HttpClient.doGetStr(oauth_access_token);
        if (jsonObject.get("errcode") != null) {
        }
        String openid = jsonObject.getString("openid");
        redisUtils.hset(WECHAT_KEY, openid, oauth_access_token);
    }

    @RequestMapping("refresh")
    public void refreshToken() {
        String refresh_token = PropertiesUtils.getString("REFRESH_TOKEN");
        String oauth_access_token = refresh_token.replace("APP_ID", PropertiesUtils.getString("APP_ID"))
                .replace("REFRESH_TOKEN", PropertiesUtils.getString("APP_SECRET"));
        JSONObject jsonObject = HttpClient.doGetStr(oauth_access_token);
        String openid = jsonObject.getString("openid");
        redisUtils.hset(WECHAT_KEY, openid, oauth_access_token);

    }

}
