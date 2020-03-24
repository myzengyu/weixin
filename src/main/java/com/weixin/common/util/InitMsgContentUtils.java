package com.weixin.common.util;

import com.alibaba.fastjson.JSONObject;
import com.weixin.module.weixin.entry.*;
import com.weixin.module.weixin.service.MenuManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 初始化消息内容工具类
 *
 * @author 付先生
 * @date 2018年1月26日 下午4:49:25
 * @TODO TODO
 */
@Component
public class InitMsgContentUtils {

    private final Logger log = LoggerFactory.getLogger(InitMsgContentUtils.class);

    private final RedisUtils redisUtils;

    public InitMsgContentUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    //初始化文本消息
    public String initText(String toUserName, String fromUserName, String content) {

        String message = "";
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);
        text.setCreateTime(new Date().getTime() + "");
        text.setContent(content);
        message = MessageUtils.textMessageToXml(text);
        return message;
    }

    //初始化图文信息
    public String initNewsMessage(String toUserName, String fromUserName) {
        String message = "";
        List<News> newsList = new ArrayList<>();
        //图文消息实体
        NewsMessage newsMessage = new NewsMessage();
        //图文消息的内容实体
        News news = new News();
        news.setTitle("曾宇正在测试图文信息！");
        news.setDescription("曾宇正在测试图文信息");
        news.setPicUrl("https://bucket-youyou.oss-cn-beijing.aliyuncs.com/yoyo/78d4be3301fe453aa59f4c7856362e30.png");//需要替换本地服务器图片文件
        news.setUrl("https://mp.weixin.qq.com/s/8DPuPZAmDj0dad94EsrNfA");
        newsList.add(news);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setCreateTime(new Date().getTime() + "");
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        message = MessageUtils.newsMessageToXml(newsMessage);
        System.err.println(message);
        return message;
    }

    /**
     * 初始化音乐信息
     *
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public String initMusicMessage(String toUserName, String fromUserName) throws IOException {
        String message = "";
        String path = "src/main/resources/static/111.jpg";
        String token = getToken();
        try {
            Music music = new Music();
            String mediaId = WeiXinUtils.upload(path, token, "thumb");
            music.setTitle("好听的Music");
            music.setDescription("感觉非常哇塞的歌曲");
            music.setMusicUrl("https://bucket-youyou.oss-cn-beijing.aliyuncs.com/yoyo_project/teacherResource/008b7c28ac824a539166412284f318d6.mp3");
            music.setHQMusicUrl("https://bucket-youyou.oss-cn-beijing.aliyuncs.com/yoyo_project/teacherResource/008b7c28ac824a539166412284f318d6.mp3");
            music.setThumbMediaId(mediaId);
            //消息发送
            MusicMessage musicMessage = new MusicMessage();
            musicMessage.setToUserName(fromUserName);
            musicMessage.setFromUserName(toUserName);
            musicMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_MUSIC);
            musicMessage.setCreateTime(new Date().getTime() + "");
            musicMessage.setMusic(music);
            message = MessageUtils.musicMessageToXml(musicMessage);
            System.err.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    public String initMenuMessage(String toUserName, String fromUserName) {
        String message = "";
        int result = 0;
        Menu menu = MenuManage.getMenu();
        String token = getToken();
        JSONObject jsonObject = WeiXinUtils.doPostStr(PropertiesUtils.getString("CREATE_MENU").replace("ACCESS_TOKEN", token), JSONObject.toJSONString(menu));
        if (jsonObject != null) {
            TextMessage text = new TextMessage();
            text.setFromUserName(toUserName);
            text.setToUserName(fromUserName);
            text.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);
            text.setCreateTime(new Date().getTime() + "");
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
                text.setContent("创建失败");
            } else if (result == 0) {

                text.setContent("创建成功");
            }
            message = MessageUtils.textMessageToXml(text);
        }
        return message;
    }

    /**
     * 获取微信的access_token
     *
     * @return
     */
    public String getToken() {
        String access_token = null;
        int i = 0;
        while (access_token == null || "".equals(access_token)) {
            access_token = (String) redisUtils.get(PropertiesUtils.getString("ACCESS_TOKEN"));
            i++;
            if (i > 10) {
                AccessToken accessToken = WeiXinUtils.getAccessToken();
                redisUtils.set(PropertiesUtils.getString("ACCESS_TOKEN"), accessToken.getToken(), accessToken.getExpiresIn());
                i = 0;
            }
        }
        return access_token;
    }
}