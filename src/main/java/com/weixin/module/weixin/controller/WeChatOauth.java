package com.weixin.module.weixin.controller;

import com.weixin.common.util.PropertiesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URLEncoder;

@Controller
@RequestMapping("weChat")
public class WeChatOauth {
    @RequestMapping(value = "oauth", method = RequestMethod.GET)
    public void weChatOauth() throws Exception {
        String url = PropertiesUtils.getString("GRT_CODE");

        url.replace("APP_ID", PropertiesUtils.getString("APP_ID"))
                .replace("","");

        URLEncoder.encode(url, "utf-8");
    }


}
