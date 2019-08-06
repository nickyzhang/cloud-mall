package com.cloud.service.impl;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.EncryptUtils;
import com.cloud.common.core.utils.SmsUtils;
import com.cloud.common.mq.service.Producer;
import com.cloud.common.notification.service.MailService;
import com.cloud.common.notification.service.SmsService;
import com.cloud.config.RabbitDelayConfig;
import com.cloud.config.UserRegisterRabbitConfig;
import com.cloud.exception.UserBizException;
import com.cloud.mapper.UserMapper;
import com.cloud.model.user.User;
import com.cloud.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Producer sender;

    @Autowired
    @Qualifier("aliyunSmsService")
    private SmsService smsService;

    @Autowired
    private MailService mailService;

    @Autowired
    UserRegisterRabbitConfig userRegisterRabbitConfig;

    @Autowired
    RabbitDelayConfig rabbitDelayConfig;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public int save(User user) {
        String salt = EncryptUtils.genSalt(16);
        String password = EncryptUtils.encrypt(user.getPassword(),salt);
        user.setSalt(salt);
        user.setPassword(password);
        try {
            this.sender.send(
                    this.userRegisterRabbitConfig.getExchangeName(),
                    this.userRegisterRabbitConfig.getRouteKey(),
                    "恭喜你,你在云店上注册成功!");
        } catch (Exception e) {
            throw new UserBizException(ResultCodeEnum.USER_SEND_CODE_FAILED.getCode(), ResultCodeEnum.USER_SEND_CODE_FAILED.getMessage());
        }
        return this.userMapper.save(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Override
    public int update(User user) {

        return this.userMapper.update(user);
    }

    /**
     * 根据用户ID查找用户
     * @param userId
     * @return
     */
    @Override
    public User find(Long userId) {
        return this.userMapper.findByUserId(userId);
    }

    /**
     * 根据条件查找查找用户
     * @param conditions
     * @return
     */
    @Override
    public List<User> find(Map<String, Object> conditions) {

        return this.userMapper.findByCondition(conditions);
    }

    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUserName(String username, String password) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("username",username);
        conditions.put("status",1);
        List<User> userList = find(conditions);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        String dbpassword = userList.get(0).getPassword();
        String salt = userList.get(0).getSalt();
        String newpassword = EncryptUtils.encrypt(password,salt);
        return newpassword.equals(dbpassword) ? userList.get(0) : null;
    }

    /**
     * 根据邮件地址和密码查找用户
     * @param email
     * @param password
     * @return
     */
    @Override
    public User findByEmail(String email, String password) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("email",email);
        conditions.put("status",1);
        List<User> userList = find(conditions);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        String dbpassword = userList.get(0).getPassword();
        String salt = userList.get(0).getSalt();
        String newpassword = EncryptUtils.encrypt(password,salt);
        return newpassword.equals(dbpassword) ? userList.get(0) : null;
    }

    /**
     * 根据电话和密码查找用户
     * @param phone
     * @param password
     * @return
     */
    @Override
    public User findByPhone(String phone, String password) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("phone",phone);
        conditions.put("status",1);
        List<User> userList = find(conditions);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        String dbpassword = userList.get(0).getPassword();
        String salt = userList.get(0).getSalt();
        String newpassword = EncryptUtils.encrypt(password,salt);
        return newpassword.equals(dbpassword) ? userList.get(0) : null;
    }

    /**
     * 根据电话查找用户
     * @param phone
     * @return
     */
    @Override
    public User findByPhone(String phone) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("phone",phone);
        conditions.put("status",1);
        List<User> userList = this.find(conditions);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }

    /**
     * 统计用户数量
     * @param conditions
     * @return
     */
    @Override
    public int count(Map<String, Object> conditions) {

        return this.userMapper.count(conditions);
    }

    /**
     * 发送短信验证码
     * @param phone
     * @param code
     */
    @Override
    public void sendSmsCode(HttpServletRequest request, String phone, String code) throws Exception {
        String smsCode = SmsUtils.genSmsCode(6);
        request.getSession().setAttribute("smsCode",smsCode);
        this.smsService.sendVerifyCode(phone,code);
    }

    /**
     * 发送邮件验证码
     * @param to
     * @param subejct
     * @param code
     */
    @Override
    public void sendMailCode(HttpServletRequest request, String to, String subejct, String code) throws Exception {
        String mailCode = SmsUtils.genSmsCode(6);
        request.getSession().setAttribute("mailCode",mailCode);
        String[] toList = {to};
        this.mailService.send(toList, subejct, code, Boolean.FALSE);
    }

    /**
     * 校验短信验证码
     * @param request
     * @param code
     * @return
     */
    @Override
    public boolean checkSmsCode(HttpServletRequest request, String code) {
        String smsCode = (String)request.getSession().getAttribute("smsCode");
        return code.equals(smsCode);
    }

    /**
     * 校验邮箱验证码
     * @param request
     * @param code
     * @return
     */
    @Override
    public boolean checkMailCode(HttpServletRequest request, String code) {
        String mailCode = (String)request.getSession().getAttribute("mailCode");
        return code.equals(mailCode);
    }

    /**
     * 更新密码
     * @param request
     * @param newPasswd
     * @param authCode
     * @return
     */
    @Override
    public boolean updatePassword(HttpServletRequest request, String newPasswd, String authCode) {
        String verifyCode = (String)request.getSession().getAttribute("authCode");
        if (!verifyCode.equals(authCode)) {
            throw new UserBizException(ResultCodeEnum.USER_AUTH_CODE_ERROR.getCode(), ResultCodeEnum.USER_AUTH_CODE_ERROR.getMessage());
        }

        User user = (User)request.getSession().getAttribute("user");
        user.setPassword(newPasswd);
        int result = this.update(user);
        if (result == 0) {
            request.getSession().removeAttribute("authCode");
            return Boolean.FALSE;
        }
        StringBuilder content = new StringBuilder();
        content.append("尊敬的 ").append(user.getNickname()).append("您好!\n");
        content.append("您的登录密码重置成功！\r\n");
        content.append("为了保障您的账户安全，请保管好并定期更改登录及支付密码。\r\n");
        try {
            this.sender.delaySend2(
                    this.rabbitDelayConfig.getExchange(),
                    this.rabbitDelayConfig.getRouteKey(),
                    content.toString(),3000);
        } catch (Exception e) {
            throw new UserBizException(ResultCodeEnum.USER_SEND_MAIL_FAILED.getCode(), "更新密码成功邮件发送失败");
        } finally {
            request.getSession().setAttribute("user",user);
            request.getSession().removeAttribute("authCode");
        }
        return Boolean.TRUE;
    }
}
