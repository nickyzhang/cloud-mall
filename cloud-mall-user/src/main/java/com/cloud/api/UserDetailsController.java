package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.ServletUtils;
import com.cloud.dto.user.UserDetailDTO;
import com.cloud.service.UserDetailService;
import com.cloud.vo.user.UserDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/detail")
public class UserDetailsController {
    @Autowired
    UserDetailService userDetailService;
    
    @Autowired
    GenIdService genIdService;

    @PostMapping("/add")
    public ResponseResult save(HttpServletRequest request, @RequestBody UserDetailVO userDetailVO) {
        ResponseResult result = new ResponseResult();
        if (userDetailVO == null) {
            return result.failed("保存的社交用户信息为空");
        }

        UserDetailDTO userDetailDTO = BeanUtils.copy(userDetailVO,UserDetailDTO.class);
        userDetailDTO.setId(this.genIdService.genId());
        String ip = ServletUtils.getIpAddress(request);
        LocalDateTime regTime = LocalDateTime.now();
        userDetailDTO.setRegisterIp(ip);
        userDetailDTO.setRegisterTime(regTime);
        userDetailDTO.setLastLoginIp(ip);
        userDetailDTO.setLastLoginTime(regTime);
        LocalDateTime now = LocalDateTime.now();
        userDetailDTO.setCreateDate(now);
        userDetailDTO.setUpdateDate(now);
        log.info(userDetailDTO.toString());
        int code = this.userDetailService.save(userDetailDTO);
        return code == 0 ? result.failed("添加用户详细信息失败") : result.success("添加用户详细信息成功");
    }

    @PostMapping("/update")
    public ResponseResult update(HttpServletRequest request, @RequestBody UserDetailVO userDetailVO) {
        ResponseResult result = new ResponseResult();
        if (userDetailVO == null) {
            return result.failed("保存的社交用户信息为空");
        }

        UserDetailDTO userDetailDTO = this.userDetailService.findUserDetailsByUserId(userDetailVO.getUserId());
        if (userDetailDTO == null) {
            return result.failed("该用户没有详细信息");
        }
        userDetailDTO.setAge(userDetailVO.getAge());
        userDetailDTO.setGender(userDetailVO.getGender());
        userDetailDTO.setBirthday(userDetailVO.getBirthday());
        String ip = ServletUtils.getIpAddress(request);
        LocalDateTime loginTime = LocalDateTime.now();
        userDetailDTO.setLastLoginIp(ip);
        userDetailDTO.setLastLoginTime(loginTime);

        int code = this.userDetailService.update(userDetailDTO);
        return code == 0 ? result.failed("更新用户详细信息失败") : result.success("更新用户详细信息成功");
    }

    @PostMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        int code = this.userDetailService.deleteUserDetailsById(id);
        return code == 0 ? result.failed("删除用户详细信息失败") : result.success("删除用户详细信息成功");
    }

    @PostMapping("/deleteByUserId")
    public ResponseResult deleteByUserId(@RequestParam("userId") Long userId) {
        ResponseResult result = new ResponseResult();
        int code = this.userDetailService.deleteUserDetailsByUserId(userId);
        return code == 0 ? result.failed("删除用户详细信息失败") : result.success("删除用户详细信息成功");
    }

    @GetMapping("/findByUserId")
    public ResponseResult findByUserId(@RequestParam("userId") Long userId){
        ResponseResult result = new ResponseResult();
        UserDetailDTO userDetailDTO = this.userDetailService.findUserDetailsByUserId(userId);
        if (userDetailDTO == null) {
            return result.failed(String.format("找不到userId=%s的用户详细信息",userId));
        }
        return result.success(userDetailDTO);
    }

    @GetMapping("/list/{id}")
    public ResponseResult find(@PathVariable("id") Long id){
        ResponseResult result = new ResponseResult();
        UserDetailDTO userDetailDTO = this.userDetailService.findUserDetailsById(id);
        if (userDetailDTO == null) {
            return result.failed(String.format("id=%s的用户",id));
        }
        return result.success(userDetailDTO);
    }

    @RequestMapping(value = "/findAllUserId",method = RequestMethod.GET)
    public ResponseResult findAllUserId() {
        ResponseResult result = new ResponseResult();
        List<Long> ids = this.userDetailService.findAllUserId();
        return (CollectionUtils.isEmpty(ids)) ? result.success("没有用户",null):
        result.success("查询成功",ids);
    }

    @RequestMapping(value = "/findUserIdListByGender",method = RequestMethod.GET)
    public ResponseResult findUserIdListByGender(@RequestParam("gender") String gender) {
        ResponseResult result = new ResponseResult();
        List<Long> ids = this.userDetailService.findUserIdListByGender(gender);
        return (CollectionUtils.isEmpty(ids)) ? result.success("没有用户",null):
                result.success("查询成功",ids);
    }
}
