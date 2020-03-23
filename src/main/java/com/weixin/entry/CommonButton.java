package com.weixin.entry;

import lombok.Data;

/**
 * 普通按钮（子按钮）
 */
@Data
public class CommonButton extends Button {
	private String type;
	private String key;

}
