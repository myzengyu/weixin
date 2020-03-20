package com.weixin.bean;

import lombok.Data;

/**
 * 复杂按钮（父按钮）
 * 
 */
@Data

public class ComplexButton extends Button {
	private Button[] sub_button;


}