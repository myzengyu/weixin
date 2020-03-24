package com.weixin.module.weixin.entry;

import lombok.Data;

/**
 * view类型的菜单
 */
@Data
public class ViewButton extends Button {
    private String type;
    private String url;
}