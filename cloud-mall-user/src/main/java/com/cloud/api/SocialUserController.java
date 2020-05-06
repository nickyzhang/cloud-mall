package com.cloud.api;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.bean.QQUserInfo;
import com.cloud.common.core.bean.WechatUserInfo;
import com.cloud.common.core.service.GenIdService;
import com.cloud.dto.user.SocialUserDTO;
import com.cloud.dto.user.UserDTO;
import com.cloud.dto.user.UserDetailDTO;
import com.cloud.enums.UserStatus;
import com.cloud.mapper.SocialUserMapper;
import com.cloud.model.user.ShippingAddress;
import com.cloud.model.user.SocialUser;
import com.cloud.service.SocialUserService;
import com.cloud.service.UserDetailService;
import com.cloud.service.UserService;
import com.cloud.vo.user.SocialBindParam;
import com.cloud.vo.user.SocialUserVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/user/social")
public class SocialUserController {

    @Autowired
    SocialUserService socialUserService;

    @Autowired
    UserService userService;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    GenIdService genIdService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    public static int getNum(int start, int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    static List<Long> userIdList = new ArrayList<>();

    static String[] providers = {"QQ","微信","支付宝","百度","微博","小米"};

    @PostMapping("/add")
    @ApiOperation(value = "添加社交用户")
    public ResponseResult save(@RequestBody SocialUserVO socialUserVO) {
        ResponseResult result = new ResponseResult();
        if (socialUserVO == null) {
            return result.failed("保存的社交用户信息为空");
        }

        if (StringUtils.isBlank(socialUserVO.getOpenId())) {
            return result.failed("保存的社交用户的openId不能为空");
        }

        if (StringUtils.isBlank(socialUserVO.getProviderId())) {
            return result.failed("提供商不能为空");
        }

        SocialUserDTO socialUserDTO = new SocialUserDTO();
        socialUserDTO.setId(this.genIdService.genId());
        socialUserDTO.setOpenId(socialUserVO.getOpenId());
        socialUserDTO.setProviderId(socialUserVO.getProviderId());
        LocalDateTime now = LocalDateTime.now();
        socialUserDTO.setCreateDate(now);
        socialUserDTO.setUpdateDate(now);
        int code = this.socialUserService.save(socialUserDTO);
        return code == 0 ? result.failed("添加社交用户失败") : result.success("添加社交用户成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新社交用户")
    public ResponseResult update(@RequestBody SocialUserVO socialUserVO) {
        ResponseResult result = new ResponseResult();
        if (socialUserVO == null) {
            return result.failed("保存的社交用户信息为空");
        }

        if (StringUtils.isBlank(socialUserVO.getOpenId())) {
            return result.failed("保存的社交用户的openId不能为空");
        }

        if (StringUtils.isBlank(socialUserVO.getProviderId())) {
            return result.failed("提供商不能为空");
        }
        SocialUserDTO socialUserDTO = this.socialUserService.findSocialUserByOpenId(socialUserVO.getOpenId());
        if (socialUserDTO == null) {
            return result.failed("不能获取这个社交用户");
        }
        int code = this.socialUserService.update(socialUserDTO);
        return code == 0 ? result.failed("更新社交用户失败") : result.success("更新社交用户成功");
    }

    @RequestMapping(value = "/batchAdd",method = RequestMethod.POST)
    public ResponseResult saveList() {
        if (CollectionUtils.isEmpty(userIdList)) {
            System.out.println("我进来了.......");
            userIdList = this.userService.findAll();
            System.out.println("用户数量=> "+userIdList.size());
        }

        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        SocialUserMapper socialUserMapper = session.getMapper(SocialUserMapper.class);
        int count = 0;
        try {
            for (int i = 0; i < userIdList.size(); i++) {
                SocialUser user = new SocialUser();
                user.setId(this.genIdService.genId());
                user.setUserId(userIdList.get(i));
                user.setOpenId(UUID.randomUUID().toString());
                user.setProviderId(providers[getNum(0,providers.length-1)]);
                user.setCreateDate(LocalDateTime.now());
                user.setUpdateDate(LocalDateTime.now());
                socialUserMapper.save(user);
                if (count % 1000 == 0 || count == userIdList.size()-1) {
                    session.flushStatements();
                    session.clearCache();
                }
                count++;
            }
            session.commit();
        } catch (Exception e) {
            log.error("[SocialUserController]批量添加数据发生异常: "+e.getMessage());
        } finally{
            session.close();
        }
        return new ResponseResult().success("成功",count);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "根据社交用户ID删除用户")
    public ResponseResult delete(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        int code = this.socialUserService.deleteSocialUserById(id);
        return code == 0 ? result.failed("删除社交用户失败") : result.success("删除社交用户成功");
    }

    @PostMapping("/deleteByUserId")
    @ApiOperation(value = "根据用户ID删除用户")
    public ResponseResult deleteByUserId(@RequestParam("userId") Long userId) {
        ResponseResult result = new ResponseResult();
        int code = this.socialUserService.deleteSocialUserByUserId(userId);
        return code == 0 ? result.failed("删除社交用户失败") : result.success("删除社交用户成功");
    }

    @GetMapping("/findByUserId")
    @ApiOperation(value = "根据用户ID查找用户")
    public ResponseResult findByUserId(@RequestParam("userId") Long userId){
        ResponseResult result = new ResponseResult();
        SocialUserDTO socialUserDTO = this.socialUserService.findSocialUserById(userId);
        if (socialUserDTO == null) {
            return result.failed(String.format("找不到userId=%s的用户"));
        }
        return result.success(socialUserDTO);
    }

    @GetMapping("/findByOpenId")
    @ApiOperation(value = "根据openid查找用户")
    public ResponseResult findByOpenId(@RequestParam("openId") String openId){
        ResponseResult result = new ResponseResult();
        SocialUserDTO socialUserDTO = this.socialUserService.findSocialUserByOpenId(openId);
        if (socialUserDTO == null) {
            return result.failed(String.format("找不到userId=%s的用户"));
        }
        return result.success(socialUserDTO);
    }

    @PostMapping("/bind/qq")
    @ApiOperation(value = "将QQ登录的用户进行手机号绑定")
    public ResponseResult qqBind(@RequestBody SocialBindParam socialBindParam) {
        ResponseResult result = new ResponseResult();
        if(socialBindParam == null) {
            return result.failed("社交绑定参数为空");
        }

        String openId = socialBindParam.getOpenId();
        if (StringUtils.isBlank(openId)) {
            return result.failed("OpenId不能为空");
        }

        String mobile = socialBindParam.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return result.failed("手机号码不能为空");
        }

        String userInfoUrl = socialBindParam.getUserInfoUrl();
        if (StringUtils.isBlank(userInfoUrl)) {
            return result.failed("获取用户详细信息URL不能为空");
        }

        QQUserInfo userInfo = this.socialUserService.getQQUserInfo(userInfoUrl);
        if (userInfo == null || userInfo.getRet() != 0) {
            return result.failed("获取QQ信息失败");
        }

        // 如果这个手机号还没有绑定过任何用户，则重新创建一个用户绑定
        UserDTO userDTO = this.userService.findByPhone(mobile);
        if (userDTO == null) {
            userDTO = new UserDTO();
            userDTO.setUserId(this.genIdService.genId());
            userDTO.setNickname(userInfo.getNickname()+ RandomUtils.nextInt(10000,100000));
            userDTO.setStatus(UserStatus.ENABLED.getCode());
            userDTO.setPhone(mobile);
            LocalDateTime now = LocalDateTime.now();
            userDTO.setCreateDate(now);
            userDTO.setUpdateDate(now);
            int code = this.userService.save(userDTO);
            if (code == 0) {
                return result.failed("绑定QQ用户失败");
            }
        }

        // 根据用户ID获取用户先息信息，如果没有则根据QQ用户信息创建
        UserDetailDTO userDetailDTO = this.userDetailService.findUserDetailsByUserId(userDTO.getUserId());
        if (userDetailDTO == null) {
            userDetailDTO = new UserDetailDTO();
            userDetailDTO.setId(this.genIdService.genId());
            userDetailDTO.setUserId(userDTO.getUserId());
            userDetailDTO.setGender(userInfo.getGender());
            LocalDateTime now = LocalDateTime.now();
            userDTO.setCreateDate(now);
            userDTO.setUpdateDate(now);
            int code = this.userDetailService.save(userDetailDTO);
            if (code == 0) {
                return result.failed("绑定QQ用户失败");
            }
        }

        SocialUserDTO socialUserDTO = this.socialUserService.findSocialUserByOpenId(openId);
        if (socialUserDTO == null) {
            return result.failed(String.format("不存在OpenId=%s这个社交用户",openId));
        }
        socialUserDTO.setUserId(userDTO.getUserId());
        int code = this.socialUserService.update(socialUserDTO);
        return code == 0 ? result.failed("绑定QQ用户失败") : result.success("绑定QQ用户成功");
    }

    @PostMapping("/bind/wechat")
    @ApiOperation(value = "将微信登录的用户进行手机号绑定")
    public ResponseResult wechatBind(@RequestBody SocialBindParam socialBindParam) {
        ResponseResult result = new ResponseResult();
        if(socialBindParam == null) {
            return result.failed("社交绑定参数为空");
        }

        String openId = socialBindParam.getOpenId();
        if (StringUtils.isBlank(openId)) {
            return result.failed("OpenId不能为空");
        }

        String mobile = socialBindParam.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return result.failed("手机号码不能为空");
        }

        String userInfoUrl = socialBindParam.getUserInfoUrl();
        if (StringUtils.isBlank(userInfoUrl)) {
            return result.failed("获取用户详细信息URL不能为空");
        }

        WechatUserInfo userInfo = this.socialUserService.getWechatUserInfo(userInfoUrl);
        if (userInfo == null || userInfo.getRet() != 0) {
            return result.failed("获取QQ信息失败");
        }

        // 如果这个手机号还没有绑定过任何用户，则重新创建一个用户绑定
        UserDTO userDTO = this.userService.findByPhone(mobile);
        if (userDTO == null) {
            userDTO = new UserDTO();
            userDTO.setUserId(this.genIdService.genId());
            userDTO.setNickname(userInfo.getNickname()+ RandomUtils.nextInt(10000,100000));
            userDTO.setStatus(UserStatus.ENABLED.getCode());
            userDTO.setPhone(mobile);
            LocalDateTime now = LocalDateTime.now();
            userDTO.setCreateDate(now);
            userDTO.setUpdateDate(now);
            int code = this.userService.save(userDTO);
            if (code == 0) {
                return result.failed("绑定微信用户失败");
            }
        }

        // 根据用户ID获取用户详细信息，如果没有则根据微信用户信息创建
        UserDetailDTO userDetailDTO = this.userDetailService.findUserDetailsByUserId(userDTO.getUserId());
        if (userDetailDTO == null) {
            userDetailDTO = new UserDetailDTO();
            userDetailDTO.setId(this.genIdService.genId());
            userDetailDTO.setUserId(userDTO.getUserId());
            userDetailDTO.setGender(userInfo.getGender());
            LocalDateTime now = LocalDateTime.now();
            userDTO.setCreateDate(now);
            userDTO.setUpdateDate(now);
            int code = this.userDetailService.save(userDetailDTO);
            if (code == 0) {
                return result.failed("绑定微信用户详细信息失败");
            }
        }

        SocialUserDTO socialUserDTO = this.socialUserService.findSocialUserByOpenId(openId);
        if (socialUserDTO == null) {
            return result.failed(String.format("不存在OpenId=%s这个微信用户",openId));
        }
        socialUserDTO.setUserId(userDTO.getUserId());
        int code = this.socialUserService.update(socialUserDTO);
        return code == 0 ? result.failed("绑定微信用户失败") : result.success("绑定微信用户成功");
    }
}
