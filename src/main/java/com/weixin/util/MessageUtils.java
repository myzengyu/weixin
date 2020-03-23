package com.weixin.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.weixin.bean.MusicMessage;
import com.weixin.bean.News;
import com.weixin.bean.NewsMessage;
import com.weixin.bean.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/19 10:21
 * 4
 */
public class MessageUtils {

    //返回消息类型:文本信息
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    //返回消息类型:音乐信息
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    //返回消息类型:新闻
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    //------------------------------------------------------------

    //请求消息类型：文本
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    //请求消息类型：图片信息
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    //请求消息类型：视频信息
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    //请求消息类型：音频
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    //请求消息类型：短视频信息
    public static final String REQ_MESSAGE_TYPE_SHORT_VIDEO = "short_video";
    //请求消息类型：地理信息信息
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    //请求消息类型：链接信息
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    //请求消息类型：事件
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    //------------------------------------------------------------

    //事件类型：关注
    public static final String EVENT_MESSAGE_TYPE_SUBSCRIBE = "subscribe";
    //事件类型：取消关注
    public static final String EVENT_MESSAGE_TYPE_UNSUBSCRIBE = "unsubscribe";
    //事件类型：点击
    public static final String EVENT_MESSAGE_TYPE_CLICK = "click";

    //------------------------------------------------------------

    //点击菜单跳转链接时的事件推送
    public static final String REQ_MESSAGE_TYPE_VIEW = "view";
    //扫码
    public static final String REQ_MESSAGE_TYPE_SCAN_CODE = "scan_code_push";

    public static Map<String, String> paresXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        //获取流信息
        ServletInputStream inputStream = request.getInputStream();
        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(inputStream);
        //获取xml的根元素
        Element root = document.getRootElement();
        //获取根元素的所有节点
        List<Element> elements = root.elements();
        for (Element element : elements) {
            map.put(element.getName(), element.getText());
        }
        inputStream.close();
        return map;
    }

    /**
     * 将文本消息对象转换成xml
     *
     * @param textMessage
     * @return 注意事项：添加xstream.jar
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }


    /**
     * 图文消息转换为xml
     *
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xStream.alias("xml", newsMessage.getClass());
        xStream.alias("item", new News().getClass());
        return xStream.toXML(newsMessage);
    }

    /**
     * 将音乐信息转换为xml
     *
     * @param musicMessage
     * @return
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xStream.alias("xml", musicMessage.getClass());
        return xStream.toXML(musicMessage);
    }

    /**
     * xStream本身不支持生成cdata块生成，对xstream扩展，让其自动生成cdata块
     */
    private static XStream xStream = new XStream(new StaxDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                boolean cdata = true;

                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

}

