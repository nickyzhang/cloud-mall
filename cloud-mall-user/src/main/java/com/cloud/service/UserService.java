package com.cloud.service;

import com.cloud.model.user.User;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {

    public int save(User user);

    public int update(User user);

    public User find(Long userId);

    public List<User> find(Map<String, Object> conditions);

    public User findByUserName(String username, String password);

    public User findByEmail(String email, String password);

    public User findByPhone(String phone, String password);

    public User findByPhone(String phone);

    public int count(Map<String, Object> conditions);

    public void sendSmsCode(HttpServletRequest request, String phone, String code) throws Exception;

    public void sendMailCode(HttpServletRequest request, String to, String subejct, String code) throws Exception;

    public boolean checkSmsCode(HttpServletRequest request, String code);

    public boolean checkMailCode(HttpServletRequest request, String code);

    public boolean updatePassword(HttpServletRequest request, String newPasswd, String authCode);
}
