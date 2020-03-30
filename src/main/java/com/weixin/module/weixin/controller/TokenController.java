package com.weixin.module.weixin.controller;

import com.weixin.common.util.CommonUtil;
import com.weixin.common.util.PropertiesUtils;
import com.weixin.common.util.RedisUtils;
import com.weixin.common.util.WeiXinUtils;
import com.weixin.module.weixin.entry.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class TokenController {

    private final Logger logger = LoggerFactory.getLogger(TokenController.class);
    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");


    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("refresh/token")
    public void refreshToken() {
        AccessToken accessToken = WeiXinUtils.getAccessToken();
        logger.info("现在的时间为：" + dataFormat.format(new Date()) + ",获取access_token,access_token：" + accessToken.getToken());
        redisUtils.set(PropertiesUtils.getString("ACCESS_TOKEN"), accessToken.getToken(), accessToken.getExpiresIn());
    }

    @RequestMapping("getAccessToken")
    @ResponseBody
    public String getAccessToken() {
        String token = (String) redisUtils.get(PropertiesUtils.getString("ACCESS_TOKEN"));
        if (CommonUtil.isEmpty(token)) {
            AccessToken accessToken = WeiXinUtils.getAccessToken();
            logger.info("现在的时间为：" + dataFormat.format(new Date()) + ",获取access_token,access_token：" + accessToken.getToken());
            redisUtils.set(PropertiesUtils.getString("ACCESS_TOKEN"), accessToken.getToken(), accessToken.getExpiresIn());
            token = accessToken.getToken();
        }
        return token;
    }
}
