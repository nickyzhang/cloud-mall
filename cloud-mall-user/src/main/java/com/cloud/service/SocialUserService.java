package com.cloud.service;

import com.cloud.common.core.bean.QQUserInfo;
import com.cloud.common.core.bean.SocialToken;
import com.cloud.common.core.bean.WechatUserInfo;
import com.cloud.dto.user.SocialUserDTO;
import com.cloud.model.user.SocialUser;

import java.util.List;

public interface SocialUserService {

    public int save(SocialUserDTO socialUserDTO);

    public int saveList(List<SocialUser> socialUserList);

    public int deleteSocialUserById(Long id);

    public int deleteSocialUserByUserId(Long userId);

    public int update(SocialUserDTO socialUserDTO);

    public SocialUserDTO findSocialUserById(Long id);

    public SocialUserDTO findSocialUserByOpenId(String openId);

    public SocialToken getSocialToken(String url);

    public QQUserInfo getQQUserInfo(String userInfoUrl);

    public WechatUserInfo getWechatUserInfo(String userInfoUrl);
}
