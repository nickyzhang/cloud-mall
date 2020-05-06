package com.cloud.web.constants;

public class SocialConstants {

    public static final String DEFAULT_REDIRECT_URL = "http://www.cloud2shop.com";

    public static final String WECHAT_AUTHORIZATION_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";

    public static final String WECHAT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    public static final String WECHAT_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    public static final String QQ_AUTHORIZATION_URL = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&state=%s&redirect_uri=%s";

    public static final String QQ_ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    public static final String QQ_USERINFO_URL = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s&format=json";
}
