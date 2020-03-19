package com.weixin.entry;

import lombok.Data;

@Data
public class TextMessage {
    private String ToUserName;//开发者微信号
    private String FromUserName;//发送方帐号（一个OpenID）
    private String CreateTime;//消息创建时间 （整型）
    private String MsgType;//消息类型
    private String Content;//内容
    private String MsgId;//消息id，64位整型

    @Override
    public String toString() {
        return "TextMessage [ToUserName=" + ToUserName + ", FromUserName="
                + FromUserName + ", CreateTime=" + CreateTime + ", MsgType="
                + MsgType + ", Content=" + Content + ", MsgId=" + MsgId + "]";
    }

}