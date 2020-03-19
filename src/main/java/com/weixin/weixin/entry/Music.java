package com.weixin.weixin.entry;

import lombok.Data;

/**
 * 音乐消息内容实体
 *
 * @author 付
 * @date 2018年1月29日 下午2:15:39
 * @TODO TODO
 */
@Data
public class Music {

    private String Title;//音乐标题
    private String Description;//音乐描述
    private String MusicUrl;//音乐链接
    private String HQMusicUrl;//高质量音乐链接，WIFI环境优先使用该链接播放音乐
    private String ThumbMediaId;//缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id

    @Override
    public String toString() {
        return "Music [Title=" + Title + ", Description=" + Description
                + ", MusicUrl=" + MusicUrl + ", HQMusicUrl=" + HQMusicUrl
                + ", ThumbMediaId=" + ThumbMediaId + "]";
    }

}