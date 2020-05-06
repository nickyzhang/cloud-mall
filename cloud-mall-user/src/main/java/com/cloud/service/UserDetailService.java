package com.cloud.service;

import com.cloud.dto.user.UserDetailDTO;

import java.util.List;

public interface UserDetailService {

    public int save(UserDetailDTO userDetailDTO);

    public int deleteUserDetailsByUserId(Long userId);

    public int deleteUserDetailsById(Long id);

    public int update(UserDetailDTO userDetailDTO);

    public UserDetailDTO findUserDetailsById(Long id);

    public UserDetailDTO findUserDetailsByUserId(Long userId);

    public List<Long> findAllUserId();

    public List<Long> findUserIdListByGender(String gender);

    public int count();
}
