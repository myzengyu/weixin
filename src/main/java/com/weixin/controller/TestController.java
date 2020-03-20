package com.weixin.controller;
/**
 * @description 微信公众号服务器配置校验token
 * @date 2019-05-09 9:38
 * @return
 */

import com.weixin.util.InitMsgContentUtil;
import com.weixin.util.MessageUtil;
import com.weixin.util.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class TestController {


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
        Map<String, String> requestMap = MessageUtil.paresXml(request);
        String toUserName = requestMap.get("ToUserName");
        String fromUserName = requestMap.get("FromUserName");
//        String createTime = requestMap.get("CreateTime");
        //用户发过来的内容的类型
        String msgType = requestMap.get("MsgType");
        //用户发过来的内容
        String content = requestMap.get("Content");
        System.out.println("接收到了消息：" + content);
//        String msgId = requestMap.get("MsgId");
        String message = null;
        if (msgType.equals((MessageUtil.REQ_MESSAGE_TYPE_TEXT))) {
            if ("文本".equals(content)) {
                String text = "曾宇微信公众号测试~~~~";
                //调用初始化文本消息方法
                message = InitMsgContentUtil.initText(toUserName, fromUserName, text);
            }
            if ("图文".equals(content)) {
                message = InitMsgContentUtil.initNewsMessage(toUserName, fromUserName);
            }
            if ("音乐".equals(content)) {
                //调用初始化图片消息的方法
                message = InitMsgContentUtil.initMusicMessage(toUserName, fromUserName);
            }
            out.print(message);
            out.close();
        }
    }
}




