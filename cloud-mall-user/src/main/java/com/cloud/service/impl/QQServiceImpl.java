package com.cloud.service.impl;

import com.cloud.common.core.bean.QQUserInfo;
import com.cloud.common.core.bean.SocialToken;
import com.cloud.common.core.bean.SocialUserInfo;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.httpclient.service.HttpClientService;
import com.cloud.mapper.SocialUserMapper;
import com.cloud.mapper.UserDetailMapper;
import com.cloud.mapper.UserMapper;
import com.cloud.model.user.SocialUser;
import com.cloud.model.user.User;
import com.cloud.model.user.UserDetail;
import com.cloud.service.SocialUserService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.Map;

@Service("qqService")
public class QQServiceImpl implements SocialUserService {


    @Autowired
    private SocialUserMapper socialUserMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private GenIdService genIdService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    HttpClientService httpClientService;

    @Override
    public void save(SocialUser user) {
        this.socialUserMapper.save(user);
    }

    @Override
    public void deleteSocialUser(SocialUser user) {
        this.socialUserMapper.deleteSocialUser(user);
    }

    @Override
    public void deleteSocialUserById(Long userId) {
        this.socialUserMapper.deleteSocialUserByUserId(userId);
    }

    @Override
    public void deleteSocialUserByUserId(Long userId) {
        this.socialUserMapper.deleteSocialUserByUserId(userId);
    }

    @Override
    public void update(SocialUser user) {
        this.socialUserMapper.update(user);
    }

    @Override
    public SocialUser findSocialUserById(Long id) {
        return socialUserMapper.findSocialUserById(id);
    }

    @Override
    public SocialUser findSocialUserByOpenId(String openId) {
        return socialUserMapper.findSocialUserByOpenId(openId);
    }

    @Override
    @Transactional
    public void bindSocialUser(User user, SocialUser socialUser, UserDetail userDetail) {
        this.userMapper.save(user);
        this.socialUserMapper.save(socialUser);
        this.userDetailMapper.save(userDetail);
    }


    @Override
    public Map<String,Object> execute(String url){
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url参数不能为空");
        }
        try {
            return this.httpClientService.doPost(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SocialToken getSocialToken(String accessTokenUrl) {
        SocialToken socialToken = new SocialToken();
        Map<String,Object> results = execute(accessTokenUrl);
        if (MapUtils.isEmpty(results)) {
            return socialToken;
        }

        results.forEach( (key,value) -> {
            socialToken.setAccessToken((String)results.get("access_token"));
            socialToken.setRefreshToken((String)results.get("refresh_token"));
            socialToken.setOpenId((String)results.get("openid"));
            socialToken.setExpireIn((String)results.get("expires_in"));
        });
        return socialToken;
    }

    @Override
    public SocialUserInfo getUserInfo(String url) {
        QQUserInfo userInfo = new QQUserInfo();
        Map<String,Object> results = execute(url);
        if (MapUtils.isEmpty(results)) {
            return userInfo;
        }

        results.forEach( (key,value) -> {
            userInfo.setNickname((String)results.get("nickname"));
            userInfo.setGender((String)results.get("gender"));
            userInfo.setAvator((String)results.get("figureurl"));
            userInfo.setFigureurl_1((String)results.get("figureurl_1"));
            userInfo.setFigureurl_2((String)results.get("figureurl_2"));
            userInfo.setLevel(Integer.parseInt((String)results.get("level")));
            userInfo.setYellowYearVip(Boolean.valueOf((String)results.get("is_yellow_year_vip")));
            userInfo.setVip(Integer.parseInt((String)results.get("vip")) == 1);
            userInfo.setRet(Integer.parseInt((String)results.get("ret")));
            userInfo.setMsg((String)results.get("msg"));
        });
        return userInfo;
    }
}
