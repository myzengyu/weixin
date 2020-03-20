package com.weixin.entry;

import lombok.Data;

@Data
public class TextMessage extends BaseMessage {

    private String Content;//内容

    @Override
    public String toString() {
        return "TextMessage{" +
                "Content='" + Content + '\'' +
                '}';
    }
}