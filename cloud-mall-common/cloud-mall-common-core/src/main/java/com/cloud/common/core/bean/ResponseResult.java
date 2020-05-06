package com.cloud.common.core.bean;

import com.cloud.common.core.enums.ResultCodeEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

/**
 * 通用的友好的响应对象
 *
 * @author nickyzhang
 * @since 0.0.1
 */
@Data
public class ResponseResult<T> {
    /** 状态码 */
    private int code;

    /** 返回的消息 */
    private String msg;

    /** 返回内容或者数据 */
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回操作成功，但是没有数据
     *
     * @return
     */
    public ResponseResult ok(){
        return success(null);
    }

    /**
     * 返回操作成功，有数据
     *
     * @param data 获取的数据
     */
    public ResponseResult success(T data){
        this.code = ResultCodeEnum.OK.getCode();
        this.msg = ResultCodeEnum.OK.getMessage();
        this.data = data;
        return this;
    }

    /**
     * 返回操作成功，有数据
     * @param msg
     * @param data
     * @return
     */
    public ResponseResult success(String msg, T data){
        this.code = ResultCodeEnum.OK.getCode();
        this.msg = msg;
        this.data = data;
        return this;
    }

    /**
     * 返回分页成功数据
     */
//    public ResponseResult pageSuccess(List data) {
//        PageInfo pageInfo = new PageInfo(data);
//        Map<String, Object> result = new HashMap<String, Object>(5);
//        result.put("pageSize", pageInfo.getPageSize());
//        result.put("totalPage", pageInfo.getPages());
//        result.put("total", pageInfo.getTotal());
//        result.put("pageNum", pageInfo.getPageNum());
//        result.put("list", pageInfo.getList());
//        this.code = ResultCodeEnum.OK.getCode();
//        this.msg = ResultCodeEnum.OK.getMessage();
//        this.data = result;
//        return this;
//    }

    /**
     * 普通失败提示信息
     */
    public ResponseResult failed() {
        this.code = ResultCodeEnum.NOT_FOUND.getCode();
        this.msg = "业务异常";
        return this;
    }

    /**
     * 操作失败,接口异常,参数验证失败
     *
     * @param msg 错误信息
     */
    public ResponseResult failed(String msg) {
        this.code = ResultCodeEnum.NOT_FOUND.getCode();
        this.msg = msg;
        return this;
    }

    /**
     * 未登录时使用
     *
     * @param data 错误信息
     */
    public ResponseResult unauthorized(T data) {
        this.code = ResultCodeEnum.AUTHORIZED_FAILED.getCode();
        this.msg = ResultCodeEnum.AUTHORIZED_FAILED.getMessage();
        this.data = data;
        return this;
    }

    /**
     * 未授权时使用
     *
     */
    public ResponseResult forbidden() {
        this.code = ResultCodeEnum.FORBIDDEN_EXCEPTION.getCode();
        this.msg = ResultCodeEnum.FORBIDDEN_EXCEPTION.getMessage();
        return this;
    }

    /**
     * 参数验证失败使用
     *
     * @param result 错误信息
     */
    public ResponseResult validateFailed(BindingResult result) {
        failed(result.getFieldError().getDefaultMessage());
        return this;
    }
}
