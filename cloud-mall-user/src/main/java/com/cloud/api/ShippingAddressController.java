package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.mapper.SocialUserMapper;
import com.cloud.model.user.ShippingAddress;
import com.cloud.service.ShippingAddressService;
import com.cloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ShippingAddressController {

    @Autowired
    ShippingAddressService addressService;

    @Autowired
    UserService userService;

    @Autowired
    GenIdService genIdService;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    String[] provinces = {"北京市","上海市","天津市","重庆市","辽宁省","河北省","山东省",
    "四川省","湖北省","江苏省","浙江省","广东省"};

    static Map<String,String> cities = new HashMap<>();
    static {
        cities.put("北京市","北京市");
        cities.put("上海市","上海市");
        cities.put("天津市","天津市");
        cities.put("重庆市","重庆市");
        cities.put("辽宁省","大连市");
        cities.put("河北省","石家庄市");
        cities.put("山东省","青岛市");
        cities.put("四川省","成都市");
        cities.put("湖北省","武汉市");
        cities.put("江苏省","南京市");
        cities.put("浙江省","杭州市");
        cities.put("广东省","深圳市");
    }
    static Map<String,String[]> areas = new HashMap<>();
    static {
        areas.put("北京市",new String[]{"海淀区","东城区"});
        areas.put("上海市",new String[]{"闵行区","嘉定区"});
        areas.put("天津市",new String[]{"武清区","南开区"});
        areas.put("重庆市",new String[]{"渝北区","沙坪坝区"});
        areas.put("大连市",new String[]{"中山区","旅顺口区"});
        areas.put("石家庄市",new String[]{"路北区","开平区"});
        areas.put("青岛市",new String[]{"博山区","临淄区"});
        areas.put("成都市",new String[]{"金牛区","武侯区"});
        areas.put("武汉市",new String[]{"江汉区","武昌区"});
        areas.put("南京市",new String[]{"鼓楼区","秦淮区"});
        areas.put("杭州市",new String[]{"萧山区","滨江区"});
        areas.put("深圳市",new String[]{"南山区","福田区"});
    }

    static Map<String,String[]> details = new HashMap<>();
    static {
        details.put("海淀区",new String[]{
                "中央财经大学对面大柳树2号院1栋2单元501",
                "知春路73号",
                "颐和园路5号"
        });
        details.put("东城区",new String[]{
                "建国门内大街69号",
                "祈年大街18号",
                "广渠门内大街21号"
        });
        details.put("闵行区",new String[]{
                "东川路800号",
                "金都西路5077号",
                "曙东路与曙建路交叉口东南150米"
        });
        details.put("嘉定区",new String[]{
                "胜辛北路1661号",
                "园汽路1189",
                "园区路151弄207号"
        });
        details.put("武清区",new String[]{
                "杨村镇雍阳西道701号",
                "翠通路与强国道交叉口",
                "泉达路21号"
        });
        details.put("南开区",new String[]{
                "鞍山西道314号",
                "湖镜道1号",
                "卫津路92号"
        });
        details.put("渝北区",new String[]{
                "文华街东路12号",
                "朝千路1号",
                "景奇路223号"
        });
        details.put("沙坪坝区",new String[]{
                "大学城中路61号",
                "大学城南路55号",
                "大学城中路37号"
        });
        details.put("中山区",new String[]{
                "武昌街34号武昌小区",
                "华乐街1012室",
                "润景园1号"
        });
        details.put("旅顺口区",new String[]{
                "旅顺南路西段9号",
                "旅顺经济开发区顺乐街33号",
                "旅顺经济开发区滨港路999-26号"
        });
        details.put("路北区",new String[]{
                "裕华西路111号",
                "槐安东路136号",
                "友谊南大街258号"
        });
        details.put("开平区",new String[]{
                "建设北路156号",
                "裕华道与河东路交口南行200米",
                "路北区国矿路"
        });

        details.put("博山区",new String[]{
                "文登路28号",
                "芝泉路2号",
                "江西路62号"
        });

        details.put("临淄区",new String[]{
                "大顺路56甲11号附近",
                "临淄区闻韶路289号",
                "棠悦(学府路南200米)"
        });

        details.put("金牛区",new String[]{
                "华侨城原岸127栋2单元",
                "九里堤南路99号附8号",
                "茶店子安蓉路8号1幢"
        });

        details.put("武侯区",new String[]{
                "西南航空港经济开发区学府路一段24号",
                "智达2路777号",
                "武科东四路18号"
        });

        details.put("江汉区",new String[]{
                "青年路373号",
                "武汉中央商务区淮海路中段",
                "建设大道566号"
        });

        details.put("武昌区",new String[]{
                "珞珈山路16号(八一路299号)",
                "珞喻路1037号",
                "白沙二路"
        });

        details.put("鼓楼区",new String[]{
                "虎踞北路15号",
                "丁家桥87号",
                "西康路1号"
        });

        details.put("秦淮区",new String[]{
                "贡院西街53号",
                "钓鱼台89-2号",
                "王府大街朝天宫4号"
        });

        details.put("萧山区",new String[]{
                "风情大道2555号",
                "凌溪路70号",
                "北干山南路"
        });

        details.put("滨江区",new String[]{
                "滨文路548号",
                "信诚路99",
                "庙后王路299号"
        });

        details.put("南山区",new String[]{
                "南光路125号西50米",
                "南光路143号",
                "南山大道2114号"
        });

        details.put("福田区",new String[]{
                "福中三路市民中心C区",
                "梅岗路26号",
                "人民北路3071号"
        });
    }

    public static int getNum(int start, int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    static List<Long> userIdList = new ArrayList<>();

    @RequestMapping(value = "/user/address/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody ShippingAddress address) {
        address.setAddressId(this.genIdService.genId());
        address.setCreateDate(LocalDateTime.now());
        address.setUpdateDate(LocalDateTime.now());
        int code = this.addressService.save(address);
        return code == 0 ? new ResponseResult().failed("添加失败") :
                new ResponseResult().success("成功");
    }

    @RequestMapping(value = "/user/address/batchAdd",method = RequestMethod.POST)
    public ResponseResult saveList() {
        if (CollectionUtils.isEmpty(userIdList)) {
            System.out.println("我进来了.......");
            userIdList = this.userService.findAll();
        }

        List<ShippingAddress> addressList = new ArrayList<>(userIdList.size());
        String province = null;
        String city = null;
        String[] areaList = null;
        String area = null;
        String[] detailList = null;
        String detail = null;
        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        SocialUserMapper socialUserMapper = session.getMapper(SocialUserMapper.class);
        int count = 0;
        try {
            for (int i = 0; i < userIdList.size(); i++) {
                ShippingAddress address = new ShippingAddress();
                address.setAddressId(this.genIdService.genId());
                address.setUserId(userIdList.get(i));
                address.setCountry("中国");
                province = provinces[getNum(0,provinces.length-1)];
                address.setProvince(province);
                city = cities.get(province);
                address.setCity(city);
                areaList = areas.get(city);
                area = areaList[getNum(0,areaList.length-1)];
                address.setArea(area);
                detailList = details.get(area);
                detail = detailList[getNum(0,detailList.length-1)];
                address.setDetails(detail);
                address.setCreateDate(LocalDateTime.now());
                address.setUpdateDate(LocalDateTime.now());
                this.addressService.save(address);
                count++;
                if (count % 1000 == 0 || count == userIdList.size()-1) {
                    session.flushStatements();
                    session.clearCache();
                }
            }
            session.commit();
        } catch (Exception e) {
            log.error("[ShippingAddressController]批量添加数据发生异常: "+e.getMessage());
        } finally {
            session.close();
        }
        return new ResponseResult().success("成功");
    }


    @RequestMapping(value = "/user/address/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody ShippingAddress address) {
        address.setUpdateDate(LocalDateTime.now());
        int code = this.addressService.update(address);
        return code == 0 ? new ResponseResult().failed("添加失败") :
                new ResponseResult().success("成功");
    }

    @RequestMapping(value = "/user/address/findShippingAddressByAddressId",method = RequestMethod.GET)
    public ResponseResult<ShippingAddress> findShippingAddressByAddressId(@RequestParam("addressId") Long addressId){
        ResponseResult<ShippingAddress> result = new ResponseResult<>();
        ShippingAddress address = this.addressService.findShippingAddressByAddressId(addressId);
        return address == null ? new ResponseResult().failed("不存在") :
                new ResponseResult().success("成功",address);
    }

    @RequestMapping(value = "/user/address/findShippingAddressListByUserId",method = RequestMethod.GET)
    public ResponseResult<List<ShippingAddress>> findShippingAddressListByUserId(@RequestParam("userId") Long userId){
        ResponseResult<List<ShippingAddress>> result = new ResponseResult<>();
        List<ShippingAddress> addressList = this.addressService.findShippingAddressListByUserId(userId);
        return CollectionUtils.isEmpty(addressList) ? new ResponseResult().failed("不存在") :
                new ResponseResult().success("成功",addressList);
    }
}
