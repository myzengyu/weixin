package com.weixin.weixin.entry;

import lombok.Data;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/19 15:06
 * 4
 */
@Data
public class MusicMessage extends BaseMessage {
    private Music Music;

    @Override
    public String toString() {
        return "MusicMessage [Music=" + Music + "]";
    }
}
