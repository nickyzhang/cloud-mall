package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.SmsUtils;
import com.cloud.common.core.utils.ValidateUtils;
import com.cloud.dto.user.UserDTO;
import com.cloud.enums.UserStatus;
import com.cloud.exception.UserBizException;
import com.cloud.mapper.SocialUserMapper;
import com.cloud.service.UserService;
import com.cloud.vo.notification.*;
import com.cloud.vo.user.UserInfo;
import com.cloud.vo.user.UserVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    GenIdService genIdService;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    public static int getNum(int start, int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    private static String[] telFirst=("134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156," +
            "133,153,180,181,182,183,184,185,186,187,188,189,170,171,172,173,174,175,176,177,178,179").split(",");
    private static String phone() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }

    private static String[] xingList = {"zhang","zhao","qian","sun","li","zhou","wu","zheng","wang","liu","fenng",
            "chen","chu","wei","jiang","shen","han","zhang","zhu","qin","you","xu","sima","yuchi","shangguan",
            "he","lv","shi","kong","cao","yan","hua","zhuge","huangfu","ouyang","baili","gongsun","xuanyuan","lihu"};

    private static String[] nameList = {
            "xiujuan","xiang","rong","jing","cong","yong","zhen","zi","zizhu","yuan","liang","xing","qing","you",
            "lan","ting","wei","ling","han","yuan","li","xuan","juan","hao","yang","ying","kai","yong","wan",
            "jiaoyi","haozhang","xiaoyu","liqi","shuying","shihan","shiyun","yongyi","mi","shishi","yangyang",
            "junyi","lanxin","min","minmin","xinhe","yonghe","runye","jing","xinger","jinger","yuzheng","yumin",
            "feizhu","fengshao","youkai","biqing","qing","lingyu","yanran","xue","lingxue","aoxue","yixue","xueer",
            "shaoguang","shiran","yiyun","yiyi","yilan","lianyan","yuxiang","yuanxi","wudong","shaoqing","shaoyun",
            "cihan","xiaoshuang","hanshuang","hanxue","xingwen","haoran","ning","yuner","lingxi","ruoyun","ruochen"};

    private static final String[] email_suffix=("@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aliyun.com," +
            "@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net," +
            "@foxmail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn").split(",");

    public static String[] elements() {
        String first = xingList[getNum(0,xingList.length-1)];
        String second = nameList[getNum(0,nameList.length-1)];
        String[] elements = new String[2];
        elements[0] = first+second+getNum(100,99999);;
        StringBuilder sb = new StringBuilder();
        sb.append(elements[0]).append(email_suffix[getNum(0,email_suffix.length-1)]);
        elements[1] = sb.toString();
        return elements;
    }

    @RequestMapping(value = "/batchAdd",method = RequestMethod.POST)
    public ResponseResult batchAdd(@RequestParam("limit") int limit) {
        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        SocialUserMapper socialUserMapper = session.getMapper(SocialUserMapper.class);
        int count = 0;
        try {
            for (int i = 0; i < limit; i++) {
                UserDTO user = new UserDTO();
                user.setUserId(this.genIdService.genId());
                user.setUsername(elements()[0]);
                user.setPhone(phone());
                user.setEmail(elements()[1]);
                user.setSalt("$2a$16$J10HZ74Ln7coWNvyPuQkMO");
                user.setPassword("$2a$16$J10HZ74Ln7coWNvyPuQkMOpfinC4BuBl3H/4kOYoGFXPi8jUVaKAK");
                user.setAvator("http://images.cloud.com/image/user/"+elements()[0]+".png");
                user.setStatus(UserStatus.ENABLED.getCode());
                user.setNickname(user.getPhone());
                LocalDateTime now = LocalDateTime.now();
                user.setCreateDate(now);
                user.setUpdateDate(now);
                this.userService.save(user);
                count++;
                if (count % 1000 == 0 || count == limit) {
                    session.flushStatements();
                    session.clearCache();
                }
            }
            session.commit();
        } catch (Exception e) {
            log.error("[UserController]批量添加数据发生异常: "+e.getMessage());
        } finally {
            session.close();
        }
        return new ResponseResult().success("成功");
    }

    @PostMapping(value="/add")
    public void add(@RequestParam("username") String username,
                    @RequestParam("password") String password,
                    @RequestParam("phone") String phone,
                    @RequestParam("email") String email,
                    @RequestParam("avator") String avator)  {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setPhone(phone);
        userDTO.setEmail(email);
        userDTO.setAvator(avator);
        userDTO.setUserId(this.genIdService.genId());
        userDTO.setStatus(UserStatus.ENABLED.getCode());
        userDTO.setNickname(userDTO.getPhone());
        LocalDateTime now = LocalDateTime.now();
        userDTO.setCreateDate(now);
        userDTO.setUpdateDate(now);
        this.userService.save(userDTO);
    }

    @PostMapping(value="/auth/register")
    @ApiOperation(value = "用户注册")
    public ResponseResult register(@RequestBody UserVO userVO) {
        if (userVO == null) {
            throw new IllegalArgumentException("要注册的用户为空");
        }

        if (StringUtils.isBlank(userVO.getUsername())) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_NAME.getCode(), ResultCodeEnum.USER_EMPTY_NAME.getMessage());
        }

        if (StringUtils.isBlank(userVO.getPassword())) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_PASSWORD.getCode(), ResultCodeEnum.USER_EMPTY_PASSWORD.getMessage());
        }

        if (!userVO.getPassword().equals(userVO.getConfirmPassword())) {
            throw new UserBizException(ResultCodeEnum.USER_DIFFERENT_PASSWORD.getCode(), ResultCodeEnum.USER_DIFFERENT_PASSWORD.getMessage());
        }

        if (StringUtils.isBlank(userVO.getPhone())) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_PHONE.getCode(), ResultCodeEnum.USER_EMPTY_PHONE.getMessage());
        }

        if (StringUtils.isBlank(userVO.getEmail())) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_EMAIL.getCode(), ResultCodeEnum.USER_EMPTY_EMAIL.getMessage());
        }

        UserDTO userDTO = BeanUtils.copy(userVO, UserDTO.class);
        userDTO.setUserId(this.genIdService.genId());
        userDTO.setStatus(UserStatus.ENABLED.getCode());
        userDTO.setNickname(userDTO.getPhone());
        LocalDateTime now = LocalDateTime.now();
        userDTO.setCreateDate(now);
        userDTO.setUpdateDate(now);
        ResponseResult result = new ResponseResult();
        int code = this.userService.save(userDTO);
        if (code == 0) {
            throw new UserBizException(ResultCodeEnum.USER_REGISTER_FAILED.getCode(),ResultCodeEnum.USER_REGISTER_FAILED.getMessage());
        }

        return new ResponseResult().success("用户注册成功",null);
    }

    @PostMapping(value="/auth/login")
    @ApiOperation(value = "用户登录")
    public ResponseResult login(@RequestParam("account") String account,
            @RequestParam("password") String password) {
        if (StringUtils.isBlank(account)) {
            throw new IllegalArgumentException("账号不能为空");
        }

        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("密码不能为空");
        }

        UserDTO userDTO = this.userService.findByUserName(account, password);
        if (userDTO == null) {
            userDTO = this.userService.findByPhone(account, password);
            if (userDTO == null) {
                userDTO = this.userService.findByEmail(account, password);
            }
        }

        if (userDTO == null) {
            throw new UserBizException(ResultCodeEnum.USER_LOGIN_FAILED);
        }
        UserInfo userInfo = BeanUtils.copy(userDTO, UserInfo.class);
        return new ResponseResult().success(userInfo);
    }

    @GetMapping(value="/findByPhone")
    @ApiOperation(value = "根据手机号码查询用户")
    public ResponseResult findByPhone(@RequestParam("phone") String phone,
                                      @RequestParam("password") String password) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(phone)) {
            return result.failed("手机号码为空");
        }
        if (StringUtils.isBlank(password)) {
            return result.failed("密码为空");
        }
        UserDTO userDTO = this.userService.findByPhone(phone,password);
        if (userDTO == null) {
            return result.failed("不存在该用户");
        }
        UserInfo userInfo = BeanUtils.copy(userDTO, UserInfo.class);
        return result.success(userInfo);
    }

    @GetMapping(value="/findByEmail")
    @ApiOperation(value = "根据邮件地址查询用户")
    public ResponseResult findByEmail(@RequestParam("email") String email,
                                      @RequestParam("password") String password) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(email)) {
            return result.failed("邮件地址为空");
        }
        if (StringUtils.isBlank(password)) {
            return result.failed("密码为空");
        }
        UserDTO userDTO = this.userService.findByEmail(email,password);
        if (userDTO == null) {
            return result.failed("不存在该用户");
        }
        UserInfo userInfo = BeanUtils.copy(userDTO, UserInfo.class);
        return result.success(userInfo);
    }

    @GetMapping(value="/findAll")
    @ApiOperation(value = "查询所有用户")
    public ResponseResult findAll() {
        ResponseResult result = new ResponseResult();
        List<Long> userList = this.userService.findAll();
        return result.success(userList);
    }
    @PostMapping(value="/auth/smsLogin")
    @ApiOperation(value = "使用短信方式进行登录")
    public ResponseResult smsLogin(@RequestParam("phone") String phone, @RequestParam("verifyCode") String verifyCode) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(phone)) {
            return result.failed("手机号码为空");
        }

        CheckSmsCodeParam checkSmsCodeParam = new CheckSmsCodeParam();
        checkSmsCodeParam.setSource("cloud-mall-user");
        checkSmsCodeParam.setMobile(phone);
        checkSmsCodeParam.setCode(verifyCode);
        boolean checked = this.notificationService.checkSmsCode(checkSmsCodeParam).getCode()
                == ResultCodeEnum.OK.getCode();
        if (!checked) {
            return result.failed("验证码不正确");
        }

        UserDTO userDTO = this.userService.findByPhone(phone);
        if (userDTO == null) {
            userDTO = new UserDTO();
            userDTO.setUserId(this.genIdService.genId());
            userDTO.setPhone(phone);
            userDTO.setStatus(UserStatus.ENABLED.getCode());
            LocalDateTime now = LocalDateTime.now();
            userDTO.setCreateDate(now);
            userDTO.setUpdateDate(now);
            int code = this.userService.save(userDTO);
            if (code == 0) {
                return result.failed("登录失败");
            }
        }
        UserInfo userInfo = BeanUtils.copy(userDTO, UserInfo.class);
        return result.success(userInfo);
    }

    @PutMapping("/updatePassword")
    @ApiOperation(value = "更新用户登录密码")
    public ResponseResult updatePassword(@RequestParam("phone") String phone,
                                         @RequestParam("newPasswd") String newPasswd) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(phone)) {
            throw new UserBizException(ResultCodeEnum.USER_UPDATE_PASSWORD_FAILED.getCode(),
                    ResultCodeEnum.USER_UPDATE_PASSWORD_FAILED.getMessage());
        }

        if (StringUtils.isBlank(newPasswd)) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_NEW_PASSWORD.getCode(), ResultCodeEnum.USER_EMPTY_NEW_PASSWORD.getMessage());
        }

        UserDTO userDTO = this.userService.findByPhone(phone);
        userDTO.setPassword(newPasswd);
        int code = this.userService.update(userDTO);
        if (code  == 0) {
            return result.failed("更新登录密码失败");
        }
        return result.success("更新登录密码成功",null);
    }

    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个用户")
    public ResponseResult find(@PathVariable("userId") Long userId) {
        ResponseResult result = new ResponseResult();
        UserDTO userDTO = this.userService.find(userId);
        if (userDTO == null) {
            result.failed("该用户不存在");
        }
        UserInfo userInfo = BeanUtils.copy(userDTO, UserInfo.class);
        return result.success(userInfo);
    }

    @PostMapping("/auth/sendMailCode")
    @ApiOperation(value = "发送邮件验证码")
    public ResponseResult sendMailCode(@RequestParam("email") String email) {

        if (StringUtils.isBlank(email)){
            throw new BizException(ResultCodeEnum.NOTIFY_MAIL_EMPTY_TO.getCode(),ResultCodeEnum.NOTIFY_MAIL_EMPTY_TO.getMessage());
        }

        if (!ValidateUtils.isEmail(email)) {
            throw new BizException(ResultCodeEnum.NOTIFY_MAIL_INVALID_ADDRESS.getCode(),ResultCodeEnum.NOTIFY_MAIL_INVALID_ADDRESS.getMessage());
        }

        try {
            MailCodeParam mailCodeParam = new MailCodeParam();
            mailCodeParam.setSource("cloud-mall-user");
            mailCodeParam.setEmail(email);
            mailCodeParam.setCode(SmsUtils.genVerifyCode(6));
            this.notificationService.sendMailCode(mailCodeParam);
        } catch (Exception e) {
            throw new BizException(ResultCodeEnum.NOTIFY_MAIL_SEND_EXCEPTION.getCode(),ResultCodeEnum.NOTIFY_MAIL_SEND_EXCEPTION.getMessage());
        }
        return new ResponseResult().success("发送邮件验证码成功",null);
    }

    @PostMapping("/auth/sendSmsCode")
    @ApiOperation(value = "发送短信验证码")
    public ResponseResult sendSmsCode(@RequestParam("phone") String phone) {
        if (StringUtils.isBlank(phone)) {
            throw new BizException(ResultCodeEnum.NOTIFY_SMS_EMPTY_PHONE.getCode(), ResultCodeEnum.NOTIFY_SMS_EMPTY_PHONE.getMessage());
        }

        if (!ValidateUtils.isPhone(phone)) {
            throw new BizException(ResultCodeEnum.NOTIFY_SMS_INVALID_PHONE_PATTERN.getCode(), ResultCodeEnum.NOTIFY_SMS_INVALID_PHONE_PATTERN.getMessage());
        }

        try {
            SmsCodeParam smsCodeParam = new SmsCodeParam();
            smsCodeParam.setSource("cloud-mall-user");
            smsCodeParam.setMobile(phone);
            String smsCoce = SmsUtils.genVerifyCode(6);
            log.info("[发送短信验证码]: {}",smsCoce);
            smsCodeParam.setCode(smsCoce);
            this.notificationService.sendSmsCode(smsCodeParam);
        } catch (Exception e) {
            throw new BizException(ResultCodeEnum.NOTIFY_SMS_SEND_EXCEPTION.getCode(), ResultCodeEnum.NOTIFY_SMS_SEND_EXCEPTION.getMessage());
        }
        return new ResponseResult().success("发送短信验证码成功",null);
    }

    @PostMapping("/modifyMobile")
    @ApiOperation(value = "修改手机号码")
    public ResponseResult modifyMobile(@RequestParam("oldMobile") String oldMobile,
                                      @RequestParam("newMobile") String newMobile) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(oldMobile)) {
            return result.failed("更新手机号码失败");
        }

        if (StringUtils.isBlank(newMobile)) {
            return result.failed("新手机号码不能为空");
        }

        UserDTO userDTO = this.userService.findByPhone(oldMobile);
        if (userDTO == null) {
            return result.failed("更新手机号码失败");
        }
        userDTO.setPhone(newMobile);
        int code = this.userService.update(userDTO);
        if (code == 0) {
            return result.failed("更新手机号码失败");
        }
        return result.success("更新手机号码成功",null);
    }

    @PostMapping("/modifyEmail")
    @ApiOperation(value = "修改邮件地址")
    public ResponseResult modifyEmail(@RequestParam("oldEmail") String oldEmail,
                                      @RequestParam("newEmail") String newEmail) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(oldEmail)) {
            return result.failed("更新邮件地址失败");
        }

        if (StringUtils.isBlank(newEmail)) {
            return result.failed("新邮件地址不能为空");
        }

        UserDTO userDTO = this.userService.findByEmail(oldEmail);
        if (userDTO == null) {
            return result.failed("更新邮件地址失败");
        }
        userDTO.setEmail(newEmail);
        int code = this.userService.update(userDTO);
        if (code == 0) {
            return result.failed("更新邮件地址失败");
        }
        return result.success("更新邮件地址成功",null);
    }

    @RequestMapping(value = "/auth/setUserToken",method = RequestMethod.POST)
    public ResponseResult setUserToken(@RequestParam("token") String token, @RequestParam("userId") Long userId) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(token)) {
            return result.failed("token不能为空");
        }

        if (userId == null) {
            return result.failed("用户信息不能为空");
        }
        int code = this.userService.setUserToken(token, userId);
        return code == 0 ? result.failed("设置token失败") : result.success("设置token成功",null);
    }

    @RequestMapping(value = "/auth/getUserToken",method = RequestMethod.GET)
    public ResponseResult<Long> getUserByToken(String token) {
        ResponseResult<Long> result = new ResponseResult<Long>();
        if (StringUtils.isBlank(token)) {
            return result.failed("token不能为空");
        }
        Long userId = this.userService.getUserByToken(token);
        if (userId == null) {
            return result.failed("这个token无效");
        }
        return result.success(userId);
    }

    @RequestMapping(value = "/auth/removeToken",method = RequestMethod.POST)
    public ResponseResult removeToken(@RequestParam("token") String token) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(token)) {
            return result.failed("token不能为空");
        }
        int code = this.userService.removeToken(token);
        return code == 0 ? result.failed("注销失败") : result.success("注销成功",null);
    }

    @RequestMapping(value = "/findUserListByRange",method = RequestMethod.GET)
    public ResponseResult<List<Long>> findUserListByRange(@RequestParam("start") Long start, @RequestParam("end") Long end) {
        ResponseResult<List<Long>> result = new ResponseResult<>();
        List<Long> userList = this.userService.findUserListByRange(start,end);
        return CollectionUtils.isEmpty(userList) ? result.failed("不存在这些用户"):result.success("成功",userList);
    }

    @RequestMapping(value = "/findUserListByLimit",method = RequestMethod.GET)
    public ResponseResult<List<Long>> findUserListByLimit(@RequestParam("start") Long start,@RequestParam("offset") Long offset) {
        ResponseResult<List<Long>> result = new ResponseResult<>();
        List<Long> userList = this.userService.findUserListByLimit(start,offset);
        return CollectionUtils.isEmpty(userList) ? result.failed("不存在这些用户"):result.success("成功",userList);

    }
}
