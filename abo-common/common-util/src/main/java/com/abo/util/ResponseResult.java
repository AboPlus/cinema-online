package com.abo.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Abo
 * @date : 2022/1/21 20:23
 */
@Data
public class ResponseResult {

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("返回状态码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<>();

    /** 构造方法 */
    public ResponseResult() {}

    /** 静态方法 */
    public static ResponseResult success() {
        ResponseResult res = new ResponseResult();
        res.setSuccess(true);
        res.setCode(ResultCode.SUCCESS);
        res.setMessage("请求成功");
        return res;
    }

    public static ResponseResult error() {
        ResponseResult res = new ResponseResult();
        res.setSuccess(false);
        res.setCode(ResultCode.ERROR);
        res.setMessage("请求失败");
        return res;
    }

    public ResponseResult success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    public ResponseResult message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResponseResult code(int code) {
        this.setCode(code);
        return this;
    }

    public ResponseResult data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public ResponseResult data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
