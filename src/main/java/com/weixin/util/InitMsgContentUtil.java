package com.weixin.util;

import com.weixin.entry.*;

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
public class InitMsgContentUtil {

    //初始化文本消息
    public static String initText(String toUserName, String fromUserName, String content) {
        String message = "";
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
        text.setCreateTime(new Date().getTime() + "");
        text.setContent(content);
        message = MessageUtil.textMessageToXml(text);
        return message;
    }

    //初始化图文信息
    public static String initNewsMessage(String toUserName, String fromUserName) {
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
        newsMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_NEWS);
        newsMessage.setCreateTime(new Date().getTime() + "");
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        message = MessageUtil.newsMessageToXml(newsMessage);
        System.err.println(message);
        return message;
    }

    /**
     * 初始化音乐信息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initMusicMessage(String toUserName,String fromUserName) throws IOException {
        String message = "";
        String access_token = WeiXinUtil.getAccessToken().getToken();
        String path = "https://bucket-youyou.oss-cn-beijing.aliyuncs.com/yoyo/78d4be3301fe453aa59f4c7856362e30.png";
        try{
            Music music = new Music();
            String mediaId = WeiXinUtil.upload(path, access_token, "thumb");
            music.setTitle("好听的Music");
            music.setDescription("感觉非常哇塞的歌曲了。。");
            music.setMusicUrl("https://bucket-youyou.oss-cn-beijing.aliyuncs.com/yoyo_project/teacherResource/008b7c28ac824a539166412284f318d6.mp3");
            music.setHQMusicUrl("https://bucket-youyou.oss-cn-beijing.aliyuncs.com/yoyo_project/teacherResource/008b7c28ac824a539166412284f318d6.mp3");
            music.setThumbMediaId(mediaId);

            MusicMessage musicMessage = new MusicMessage();
            musicMessage.setToUserName(fromUserName);
            musicMessage.setFromUserName(toUserName);
            musicMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);
            musicMessage.setCreateTime(new Date().getTime()+"");
            musicMessage.setMusic(music);
            message = MessageUtil.musicMessageToXml(musicMessage);
            System.err.println(message);
        }catch(Exception e){
            e.printStackTrace();
        }
        return message;
    }
}