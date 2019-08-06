package com.cloud.service;


import com.cloud.model.user.UserDetail;

public interface UserDetailService {

    public void save(UserDetail userDetail);

    public void deleteUserDetails(UserDetail userDetails);

    public void deleteUserDetailsById(Long id);

    public void update(UserDetail userDetails);

    public UserDetail findUserDetailsById(Long id);

    public UserDetail findUserDetailsByUserId(Long userId);
}
