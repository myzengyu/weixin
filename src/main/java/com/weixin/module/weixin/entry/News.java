package com.weixin.module.weixin.entry;

import lombok.Data;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/19 14:13
 * 4
 */
@Data
public class News {

    //文章标题
    private String Title;
    //文章描述
    private String Description;
    //图片地址
    private String PicUrl;
    //文章地址
    private String Url;
}
