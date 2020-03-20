package com.weixin.service;

import com.weixin.util.InitMsgContentUtils;
import com.weixin.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class WeChatService {

    @Autowired
    private InitMsgContentUtils initMsgContentUtils;

    public String processRequest(HttpServletRequest request) throws Exception {
        Map<String, String> requestMap = MessageUtils.paresXml(request);
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
        if (msgType.equals((MessageUtils.REQ_MESSAGE_TYPE_TEXT))) {
            if ("文本".equals(content)) {
                String text = "曾宇微信公众号测试";
                //调用初始化文本消息方法
                message = initMsgContentUtils.initText(toUserName, fromUserName, text);
            }
            if ("图文".equals(content)) {
                message = initMsgContentUtils.initNewsMessage(toUserName, fromUserName);
            }
            if ("音乐".equals(content)) {
                //调用初始化图片消息的方法
                message = initMsgContentUtils.initMusicMessage(toUserName, fromUserName);
            }
        }
        return message;
    }
}