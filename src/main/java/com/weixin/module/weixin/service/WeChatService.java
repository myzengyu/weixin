package com.weixin.module.weixin.service;

import com.weixin.common.util.InitMsgContentUtils;
import com.weixin.common.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class WeChatService {

    @Autowired
    private InitMsgContentUtils initMsgContentUtils;
    @Autowired
    private UserInfoService userInfoService;

    public String processRequest(HttpServletRequest request) throws Exception {
        Map<String, String> requestMap = MessageUtils.paresXml(request);
        //公众号ID
        String toUserName = requestMap.get("ToUserName");
        //对方的openID
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
                message = initMsgContentUtils.initText(toUserName, fromUserName,text);
            } else if ("图文".equals(content)) {
                message = initMsgContentUtils.initNewsMessage(toUserName, fromUserName);
            } else if ("音乐".equals(content)) {
                //调用初始化图片消息的方法
                message = initMsgContentUtils.initMusicMessage(toUserName, fromUserName);
            } else if ("创建菜单".equals(content)) {
                //创建菜单
                message = initMsgContentUtils.initMenuMessage(toUserName, fromUserName);
            }
        } else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_EVENT)) {
            // 事件类型
            String eventType = requestMap.get("Event");
            // 订阅
            if (eventType.equals(MessageUtils.EVENT_MESSAGE_TYPE_SUBSCRIBE)) {
                //获取用户信息，并存入数据库
                String test = userInfoService.getUserInfo(fromUserName);
                initMsgContentUtils.initText(toUserName,fromUserName,test);
            }
            // 取消订阅
            else if (eventType.equals(MessageUtils.EVENT_MESSAGE_TYPE_UNSUBSCRIBE)) {
                // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
            }
        }
        return message;
    }
}