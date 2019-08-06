package com.cloud.service;

import com.cloud.common.core.bean.SocialToken;
import com.cloud.common.core.bean.SocialUserInfo;
import com.cloud.model.user.SocialUser;
import com.cloud.model.user.User;
import com.cloud.model.user.UserDetail;
import java.util.Map;

public interface SocialUserService {

    public void save(SocialUser user);

    public void deleteSocialUser(SocialUser user);

    public void deleteSocialUserById(Long id);

    public void deleteSocialUserByUserId(Long userId);

    public void update(SocialUser user);

    public SocialUser findSocialUserById(Long id);

    public SocialUser findSocialUserByOpenId(String openId);

    public Map<String,Object> execute(String url);

    public SocialToken getSocialToken(String url);

    public SocialUserInfo getUserInfo(String userInfoUrl);

    public void bindSocialUser(User user, SocialUser socialUser, UserDetail userDetail);
}
