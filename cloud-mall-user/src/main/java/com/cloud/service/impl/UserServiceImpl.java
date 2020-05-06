package com.cloud.service.impl;

import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.EncryptUtils;
import com.cloud.common.redis.service.RedisService;
import com.cloud.dto.user.UserDTO;
import com.cloud.exception.UserBizException;
import com.cloud.mapper.UserMapper;
import com.cloud.model.user.User;
import com.cloud.mq.rabbitmq.service.RabbitSender;
import com.cloud.service.UserService;
import com.cloud.vo.notification.MailParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitSender sender;

    @Autowired
    RedisService redisService;

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    @Override
    public int save(UserDTO userDTO) {
        if (userDTO == null) {
            return 0;
        }
        User user = BeanUtils.copy(userDTO,User.class);
//        String salt = EncryptUtils.genSalt(16);
//        String password = EncryptUtils.encrypt(user.getPassword(),salt);
//        user.setSalt(salt);
//        user.setPassword(password);
        int code = this.userMapper.save(user);
//        if (code == 1) {
//            try {
//                RegisterMessage message = new RegisterMessage(user.getPhone(),"恭喜你,你在云店上注册成功!");
//                this.sender.send("user.register","user.register",message);
//            } catch (Exception e) {
//                throw new UserBizException(ResultCodeEnum.USER_SEND_CODE_FAILED.getCode(), ResultCodeEnum.USER_SEND_CODE_FAILED.getMessage());
//            }
//        }
        return code;
    }

    @Override
    public int saveList(List<User> userList) {
        List<User> users = new ArrayList<>();
        int count = 0;
        int code = 0;
        for (User user : userList) {
            users.add(user);
            count++;
            if (count % 20 == 0) {
                code = this.userMapper.saveList(users);
            }
            users.clear();
        }
        return code;
    }


    /**
     * 更新用户
     * @param userDTO
     * @return
     */
    @Override
    public int update(UserDTO userDTO) {
        if (userDTO == null) {
            return 0;
        }
        User user = BeanUtils.copy(userDTO,User.class);
        int code = this.userMapper.update(user);
        if (code == 1) {
            MailParam mailParam = new MailParam();
            mailParam.setEmail(user.getEmail());
            StringBuilder content = new StringBuilder();
            content.append("尊敬的 ").append(user.getNickname()).append("您好!\n");
            content.append("您的登录密码重置成功！\r\n");
            content.append("为了保障您的账户安全，请保管好并定期更改登录及支付密码。\r\n");
            mailParam.setContent(content.toString());
            mailParam.setSubject("云店更新密码邮件通知");
            mailParam.setHtml(false);
            try {
                this.sender.delaySend("user.update.password","user.update.password",mailParam,5000L);
            } catch (Exception e) {
                throw new UserBizException(ResultCodeEnum.USER_SEND_MAIL_FAILED.getCode(), "更新密码成功邮件发送失败");
            }
        }
        return code;
    }

    /**
     * 根据用户ID查找用户
     * @param userId
     * @return
     */
    @Override
    public UserDTO find(Long userId) {
        User user = this.userMapper.findByUserId(userId);
        if (user == null) {
            return null;
        }
        return BeanUtils.copy(user, UserDTO.class);
    }

    @Override
    public List<Long> findAll() {
        return this.userMapper.findAll();
    }

    @Override
    public List<Long> findUserListByRange(Long start, Long end) {
        return this.userMapper.findUserListByRange(start, end);
    }

    @Override
    public List<Long> findUserListByLimit(Long start, Long offset) {
        return this.userMapper.findUserListByLimit(start, offset);
    }

    /**
     * 根据条件查找查找用户
     * @param map
     * @return
     */
    @Override
    public List<UserDTO> find(Map<String, Object> map) {
        List<User> userList = this.userMapper.findByCondition(map);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return BeanUtils.copy(userList,UserDTO.class);
    }

    /**
     * 根据用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public UserDTO findByUserName(String username, String password) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("username",username);
        conditions.put("status",1);
        List<UserDTO> userDTOList = find(conditions);
        if (CollectionUtils.isEmpty(userDTOList)) {
            return null;
        }
        String dbpassword = userDTOList.get(0).getPassword();
        String salt = userDTOList.get(0).getSalt();
        String newpassword = EncryptUtils.encrypt(password,salt);
        return newpassword.equals(dbpassword) ? userDTOList.get(0) : null;
    }

    /**
     * 根据邮件地址和密码查找用户
     * @param email
     * @param password
     * @return
     */
    @Override
    public UserDTO findByEmail(String email, String password) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("email",email);
        conditions.put("status",1);
        List<UserDTO> userDTOList = find(conditions);
        if (CollectionUtils.isEmpty(userDTOList)) {
            return null;
        }
        String dbpassword = userDTOList.get(0).getPassword();
        String salt = userDTOList.get(0).getSalt();
        String newpassword = EncryptUtils.encrypt(password,salt);
        return newpassword.equals(dbpassword) ? userDTOList.get(0) : null;
    }

    @Override
    public UserDTO findByEmail(String email) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("email",email);
        conditions.put("status",1);
        List<UserDTO> userDTOList = find(conditions);
        if (CollectionUtils.isEmpty(userDTOList)) {
            return null;
        }
        return userDTOList.get(0);
    }

    /**
     * 根据电话和密码查找用户
     * @param phone
     * @param password
     * @return
     */
    @Override
    public UserDTO findByPhone(String phone, String password) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("phone",phone);
        conditions.put("status",1);
        List<UserDTO> userDTOList = find(conditions);
        if (CollectionUtils.isEmpty(userDTOList)) {
            return null;
        }
        String dbpassword = userDTOList.get(0).getPassword();
        String salt = userDTOList.get(0).getSalt();
        String newpassword = EncryptUtils.encrypt(password,salt);
        return newpassword.equals(dbpassword) ? userDTOList.get(0) : null;
    }

    /**
     * 根据电话查找用户
     * @param phone
     * @return
     */
    @Override
    public UserDTO findByPhone(String phone) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("phone",phone);
        conditions.put("status",1);
        List<UserDTO> userList = this.find(conditions);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }


    @Override
    public int  setUserToken(String token, Long userId) {
        try {
            redisService.set(token.toString(), userId, 1000 * 60 * 5);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public Long getUserByToken(String token) {

        return (Long)this.redisService.get(token);
    }

    @Override
    public int removeToken(String token) {

        try {
            this.redisService.del(token);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }
}
