package com.weixin.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConfigProperties {

    @Value("${APP_ID}")
    public String APP_ID;
    @Value("${APP_SECRET}")
    public String APP_SECRET;
    //获取acces_token 接口地址
    @Value("${ACCESS_TOKEN_URL}")
    public String ACCESS_TOKEN_URL;
    //上传文件url接口地址
    @Value("${UPLOAD_URL}")
    private String UPLOAD_URL;
}
