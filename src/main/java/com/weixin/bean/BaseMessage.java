package com.weixin.bean;

import lombok.Data;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/19 14:19
 * 4
 */
@Data
public class BaseMessage {
    //来信人 开发者帐号
    private String FromUserName;
    //收件人,一个openid
    private String ToUserName;
    //内容类型
    private String MsgType;
    //消息创建时间
    private String CreateTime;
    //是否是星标
    private Boolean funcFlag;
    // 消息id，64位整型
    private long MsgId;
}
