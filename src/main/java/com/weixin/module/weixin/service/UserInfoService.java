package com.weixin.module.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.weixin.common.util.HttpClient;
import com.weixin.module.weixin.dao.UserInfoMapper;
import com.weixin.module.weixin.entry.User;
import com.weixin.common.util.InitMsgContentUtils;
import com.weixin.common.util.PropertiesUtils;
import com.weixin.common.util.WeiXinUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/3/23 14:03
 * 4
 */
@Service
public class UserInfoService {
    @Autowired
    private InitMsgContentUtils initMsgContentUtils;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 当用户关注公众号，获取用户openid
     */
    public String getUserInfo(String openid) {
        String token = initMsgContentUtils.getToken();
        JSONObject jsonObject = HttpClient.doGetStr(PropertiesUtils.getString("GET_USER_INFO").replace("ACCESS_TOKEN", token).replace("OPENID", openid));
        User user = JSONObject.parseObject(jsonObject.toJSONString(), User.class);
        if (userInfoMapper.selectByOpenid(user.getOpenid()) == null)
            userInfoMapper.insert(user);
        return "欢迎关注微信公众号";
    }
}
