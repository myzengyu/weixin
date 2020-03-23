package com.weixin.util;

import com.alibaba.fastjson.JSONObject;
import com.weixin.bean.AccessToken;
import com.weixin.schedule.ScheduledTasks;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class WeiXinUtils {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    /**
     * Get请求
     *
     * @param url
     * @return 注意事项 添加json object的jar包及依赖jar包
     * 添加httpclient，http core的 jar包
     */
    public static JSONObject doGetStr(String url) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * post请求
     *
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url, String outStr) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            jsonObject = JSONObject.parseObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 获取access_token
     *
     * @return
     */
    public static AccessToken getAccessToken() {
        AccessToken token = new AccessToken();
        String url = PropertiesUtils.getString("ACCESS_TOKEN_URL").replace("APP_ID", PropertiesUtils.getString("APP_ID")).replace("APP_SECRET", PropertiesUtils.getString("APP_SECRET"));
        JSONObject jsonObject = doGetStr(url);
        if (jsonObject != null) {
            try {
                token.setToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (Exception e) {
                logger.error("获取access_token失败，请联系管理员");
            }
        }
        return token;
    }

    /**
     * 文件上传
     *
     * @param filePath    路径
     * @param accessToken
     * @param type        类型
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String filePath, String accessToken, String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        String url = PropertiesUtils.getString("UPLOAD_URL").replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);
        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        JSONObject jsonObj = JSONObject.parseObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if (!"image".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        System.err.println("----" + mediaId);
        return mediaId;
    }
}


