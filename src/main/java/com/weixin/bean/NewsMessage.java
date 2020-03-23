package com.weixin.bean;

import lombok.Data;

import java.util.List;

/**
 * @author 付
 * @date 2018年1月27日 上午10:12:08
 * @TODO TODO图文消息外层实体
 */
@Data
public class NewsMessage extends BaseMessage {

    private int ArticleCount;//    图文消息个数，限制为8条以内
    private List<News> Articles;//多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应

    @Override
    public String toString() {
        return "NewsMessage{" +
                "ArticleCount=" + ArticleCount +
                ", Articles=" + Articles +
                '}';
    }
}