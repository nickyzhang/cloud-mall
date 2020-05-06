package com.cloud;

import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.EncryptUtils;
import com.cloud.dto.user.UserDTO;
import com.cloud.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudUserApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    GenIdService genIdService;

    @Test
    public void add() {

        UserDTO user = new UserDTO();
        user.setUserId(genIdService.genId());
        String[] elements = usernameAndEmail();
        user.setUsername(elements[0]);
        String salt = EncryptUtils.genSalt(16);
        String password = EncryptUtils.encrypt(user.getPassword(),salt);
        user.setSalt(salt);
        user.setPassword("123abcABC");
        user.setPhone(phone());
        user.setEmail(elements[1]);
        user.setAvator("http://images.cloud.com/image/user/"+user.getUsername()+"-"+getNum(1000,9999)+".png");
        user.setStatus(1);
        user.setNickname("cloud_"+user.getPhone());
        user.setUpdateDate(LocalDateTime.now());
        user.setCreateDate(LocalDateTime.now());

        this.userService.save(user);
    }

    public static int getNum(int start,int end) {
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

    public static String[] usernameAndEmail() {
        String first = xingList[getNum(0,xingList.length-1)];
        String second = nameList[getNum(0,nameList.length-1)];
        String[] elements = new String[2];
        elements[0] = first+second;
        StringBuilder sb = new StringBuilder();
        sb.append(elements[0]).append(getNum(100,9999)).append(email_suffix[getNum(0,email_suffix.length-1)]);
        elements[1] = sb.toString();
        return elements;
    }
}
