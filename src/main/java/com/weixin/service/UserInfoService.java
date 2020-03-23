package com.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.weixin.dao.UserInfoMapper;
import com.weixin.entry.User;
import com.weixin.util.InitMsgContentUtils;
import com.weixin.util.PropertiesUtils;
import com.weixin.util.WeiXinUtils;
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
        JSONObject jsonObject = WeiXinUtils.doGetStr(PropertiesUtils.getString("GET_USER_INFO").replace("ACCESS_TOKEN", token).replace("OPENID", openid));
        User user = JSONObject.parseObject(jsonObject.toJSONString(), User.class);
        userInfoMapper.insert(user);
        return "欢迎关注微信公众号";
    }
}
