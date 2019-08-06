package com.cloud.service.impl;

import com.cloud.mapper.UserDetailMapper;
import com.cloud.model.user.UserDetail;
import com.cloud.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailMapper userDetailMapper;


    @Override
    public void save(UserDetail userDetail) {

        this.userDetailMapper.save(userDetail);
    }

    @Override
    public void deleteUserDetails(UserDetail userDetail) {

        this.userDetailMapper.deleteUserDetails(userDetail);
    }

    @Override
    public void deleteUserDetailsById(Long id) {

        this.userDetailMapper.deleteUserDetailsById(id);
    }

    @Override
    public void update(UserDetail userDetails) {

        this.userDetailMapper.update(userDetails);
    }

    @Override
    public UserDetail findUserDetailsById(Long id) {

        return this.userDetailMapper.findUserDetailsById(id);
    }

    @Override
    public UserDetail findUserDetailsByUserId(Long userId) {

        return this.userDetailMapper.findUserDetailsByUserId(userId);
    }
}
