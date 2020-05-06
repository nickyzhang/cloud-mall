package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.user.UserDetailDTO;
import com.cloud.mapper.UserDetailMapper;
import com.cloud.model.user.UserDetail;
import com.cloud.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailMapper userDetailMapper;


    @Override
    public int save(UserDetailDTO userDetailDTO) {
        if (userDetailDTO == null) {
            return 0;
        }

        UserDetail userDetail = BeanUtils.copy(userDetailDTO,UserDetail.class);
        return this.userDetailMapper.save(userDetail);
    }

    @Override
    public int deleteUserDetailsByUserId(Long userId) {

        return this.userDetailMapper.deleteUserDetailsByUserId(userId);
    }

    @Override
    public int deleteUserDetailsById(Long id) {
        return this.userDetailMapper.deleteUserDetailsById(id);
    }

    @Override
    public int update(UserDetailDTO userDetailDTO) {
        if (userDetailDTO == null) {
            return 0;
        }

        UserDetail userDetail = BeanUtils.copy(userDetailDTO,UserDetail.class);
        return this.userDetailMapper.update(userDetail);
    }

    @Override
    public UserDetailDTO findUserDetailsById(Long id) {
        UserDetail userDetail = this.userDetailMapper.findUserDetailsById(id);
        if (userDetail == null) {
            return null;
        }
        return BeanUtils.copy(userDetail,UserDetailDTO.class);
    }

    @Override
    public UserDetailDTO findUserDetailsByUserId(Long userId) {
        UserDetail userDetail = this.userDetailMapper.findUserDetailsByUserId(userId);
        if (userDetail == null) {
            return null;
        }
        return BeanUtils.copy(userDetail,UserDetailDTO.class);
    }

    @Override
    public List<Long> findAllUserId() {
        return this.userDetailMapper.findAllUserId();
    }

    @Override
    public List<Long> findUserIdListByGender(String gender) {
        return this.userDetailMapper.findUserIdListByGender(gender);
    }

    @Override
    public int count() {
        return this.userDetailMapper.count();
    }
}
