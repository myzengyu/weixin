package com.weixin.module.weixin.service;

import com.weixin.module.weixin.entry.*;


/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/23 10:50
 * 4
 */
public class MenuManage {
    //视图按钮
    private static final String MENU_TYPE_VIEW = "view";
    //点击事件按钮
    private static final String MENU_TYPE_CLICK = "click";

    //创建menu菜单
    public static Menu getMenu() {
        //第一个视图按钮
        ViewButton btn10 = new ViewButton();
        btn10.setName("View菜单");
        btn10.setType(MENU_TYPE_VIEW);
        btn10.setUrl("https://www.baidu.com");


//-------------------------------------------------------
        CommonButton btn20 = new CommonButton();
        btn20.setName("事件菜单");
        btn20.setType(MENU_TYPE_CLICK);
        btn20.setKey("1");

        ViewButton btn21 = new ViewButton();
        btn21.setName("菜单");
        btn21.setType(MENU_TYPE_VIEW);
        btn21.setUrl("https://www.baidu.com/");

//------------------------------------------------------------
        CommonButton btn31 = new CommonButton(); //返回图文消息
        btn31.setName("");
        btn31.setType(MENU_TYPE_CLICK);
        btn31.setKey("");

        //###############################################一级子菜单
        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("一栏");
        mainBtn1.setSub_button(new Button[]{btn10});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("二栏");  //
        mainBtn2.setSub_button(new Button[]{btn20, btn21});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("三栏");// btn31, btn32, btn33,
        mainBtn3.setSub_button(new Button[]{btn31});

        /**
         * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
         */
        Menu menu = new Menu();
        menu.setButtons(new Button[]{mainBtn1, mainBtn2, mainBtn3});
        return menu;
    }
}
