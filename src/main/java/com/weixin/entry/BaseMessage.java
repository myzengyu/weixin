package com.weixin.entry;

import lombok.Data;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/19 14:19
 * 4
 */
@Data
public class BaseMessage {
    //来信人
    private String FromUserName;
    //收件人
    private String ToUserName;
    //内容类型
    private String MsgType;
    private String CreateTime;
}
