package com.weixin.controller;
/**
 * @description 微信公众号服务器配置校验token
 * @date 2019-05-09 9:38
 * @return
 */

import com.weixin.service.WeChatService;
import com.weixin.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class TestController {

    @Autowired
    private WeChatService weChatService;

    @RequestMapping(value = "test/checkToken", method = RequestMethod.GET)
    public void checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //token验证代码段
        System.out.println("请求已到达，开始校验token");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter writer = response.getWriter();
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            System.out.println(echostr);
            writer.println(echostr);
        }

    }

    @RequestMapping(value = "test/checkToken", method = RequestMethod.POST)
    public void acceptThing(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String message = weChatService.processRequest(request);
        out.print(message);
        out.close();
    }
}





