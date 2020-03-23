package com.weixin.schedule;

import com.weixin.bean.AccessToken;
import com.weixin.util.PropertiesUtils;
import com.weixin.util.RedisUtils;
import com.weixin.util.WeiXinUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(cron = " * * 0/2 * * ? ")
    public void reportCurrent() {
        //每隔两小时获取一次access_token
        AccessToken accessToken = WeiXinUtils.getAccessToken();
        logger.info("现在的时间为：" + dataFormat.format(new Date()) + ",获取access_token,access_token：" + accessToken.getToken());
        redisUtils.set(PropertiesUtils.getString("ACCESS_TOKEN"), accessToken.getToken(), accessToken.getExpiresIn());
    }

}