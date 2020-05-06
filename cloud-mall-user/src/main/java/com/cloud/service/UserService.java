package com.cloud.service;

import com.cloud.dto.user.UserDTO;
import com.cloud.model.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public int save(UserDTO user);

    public int saveList(List<User> userList);

    public int update(UserDTO user);

    public UserDTO find(Long userId);

    public List<Long> findAll();

    public List<Long> findUserListByRange(Long start, Long end);

    public List<Long> findUserListByLimit(Long start, Long offset);

    public List<UserDTO> find(Map<String, Object> map);

    public UserDTO findByUserName(String account, String password);

    public UserDTO findByEmail(String email, String password);

    public UserDTO findByEmail(String email);

    public UserDTO findByPhone(String phone, String password);

    public UserDTO findByPhone(String phone);

    public int setUserToken(String token, Long userId);

    public Long getUserByToken(String token);

    public int removeToken(String token);
}
