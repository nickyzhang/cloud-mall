package com.cloud.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON转换成POJO
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON转换成List
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将JSON转换成Map
     * @param jsonData
     * @return
     */
    public static Map<String,Object> jsonToMap(String jsonData) {
        Map<String,Object> result = null;
        try {
            result = MAPPER.readValue(jsonData, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> T convert(Object fromType, Class<T> clazz ) {
        return MAPPER.convertValue(fromType,clazz);
    }

    public static <T> T convert(String text, TypeReference<T> reference) {
        return JSONObject.parseObject(text,reference);
    }

    public static <T> T convert(Map<String,Object> map, TypeReference<T> reference) {
        return JSONObject.parseObject(JSONObject.toJSONString(map),reference);
    }
}
