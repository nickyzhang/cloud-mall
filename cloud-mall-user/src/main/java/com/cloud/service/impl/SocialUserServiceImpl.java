package com.cloud.service.impl;

import com.cloud.common.core.bean.QQUserInfo;
import com.cloud.common.core.bean.SocialToken;
import com.cloud.common.core.bean.WechatUserInfo;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.httpclient.service.HttpClientService;
import com.cloud.dto.user.SocialUserDTO;
import com.cloud.mapper.SocialUserMapper;
import com.cloud.mapper.UserDetailMapper;
import com.cloud.mapper.UserMapper;
import com.cloud.model.user.SocialUser;
import com.cloud.service.SocialUserService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SocialUserServiceImpl implements SocialUserService{

    @Autowired
    UserMapper userMapper;

    @Autowired
    SocialUserMapper socialUserMapper;

    @Autowired
    UserDetailMapper userDetailMapper;

    @Autowired
    HttpClientService httpClientService;

    @Override
    public int save(SocialUserDTO socialUserDTO) {
        if (socialUserDTO == null) {
            return 0;
        }
        SocialUser socialUser = BeanUtils.copy(socialUserDTO,SocialUser.class);
        return this.socialUserMapper.save(socialUser);
    }

    @Override
    public int saveList(List<SocialUser> socialUserList) {
        List<SocialUser> socialUsers = new ArrayList<>();
        int count = 0;
        int code = 0;
        for (SocialUser socialUser : socialUserList) {
            socialUsers.add(socialUser);
            count++;
            if (count % 20 == 0) {
                code = this.socialUserMapper.saveList(socialUsers);
            }
            socialUsers.clear();
        }
        return code;
    }

    @Override
    public int deleteSocialUserById(Long id) {
        return this.socialUserMapper.deleteSocialUserById(id);
    }

    @Override
    public int deleteSocialUserByUserId(Long userId) {
        return this.socialUserMapper.deleteSocialUserByUserId(userId);
    }

    @Override
    public int update(SocialUserDTO socialUserDTO) {
        if (socialUserDTO == null) {
            return 0;
        }
        SocialUser socialUser = BeanUtils.copy(socialUserDTO,SocialUser.class);
        return this.socialUserMapper.update(socialUser);
    }

    @Override
    public SocialUserDTO findSocialUserById(Long id) {
        SocialUser socialUser = this.socialUserMapper.findSocialUserById(id);
        if (socialUser == null) {
            return null;
        }
        return BeanUtils.copy(socialUser,SocialUserDTO.class);
    }

    @Override
    public SocialUserDTO findSocialUserByOpenId(String openId) {
        SocialUser socialUser = this.socialUserMapper.findSocialUserByOpenId(openId);
        if (socialUser == null) {
            return null;
        }
        return BeanUtils.copy(socialUser,SocialUserDTO.class);
    }

    @Override
    public SocialToken getSocialToken(String accessTokenUrl) {
        if (StringUtils.isBlank(accessTokenUrl)) {
            return null;
        }
        try {
            SocialToken socialToken = new SocialToken();
            Map<String,Object> results = this.httpClientService.doPost(accessTokenUrl);
            if (MapUtils.isEmpty(results)) {
                return null;
            }

            String accessToken = (String)results.get("access_token");
            String refreshToken = (String)results.get("refresh_token");
            String openId = (String)results.get("openid");
            String expireIn = (String)results.get("expires_in");
            socialToken.setAccessToken(accessToken);
            socialToken.setRefreshToken(refreshToken);
            socialToken.setOpenId(openId);
            socialToken.setExpireIn(expireIn);
            return socialToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QQUserInfo getQQUserInfo(String userInfoUrl) {
        if (StringUtils.isBlank(userInfoUrl)) {
            return null;
        }
        try {
            QQUserInfo userInfo = new QQUserInfo();
            Map<String,Object> results = this.httpClientService.doPost(userInfoUrl);
            if (MapUtils.isEmpty(results)) {
                return null;
            }

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
            return userInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public WechatUserInfo getWechatUserInfo(String userInfoUrl) {
        if (StringUtils.isBlank(userInfoUrl)) {
            return null;
        }

        try {
            Map<String,Object> results = this.httpClientService.doPost(userInfoUrl);
            if (MapUtils.isEmpty(results)) {
                return null;
            }
            WechatUserInfo userInfo = new WechatUserInfo();
            userInfo.setNickname((String)results.get("nickname"));
            userInfo.setGender((String)results.get("sex"));
            userInfo.setAvator((String)results.get("headimgurl"));
            userInfo.setAvator((String)results.get("openid"));
            userInfo.setUnionid((String)results.get("unionid"));
            userInfo.setCountry((String)results.get("country"));
            userInfo.setProvince((String)results.get("province"));
            userInfo.setCity((String)results.get("city"));
            userInfo.setPrivileges((String)results.get("privilege"));
            userInfo.setRet(Integer.parseInt((String)results.get("errcode")));
            userInfo.setMsg((String)results.get("errmsg"));
            return userInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
