package com.jhzz.jhzzblog.vo.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Huanzhi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer Code;
    private Boolean success;
    private String message;
    private T data;


    public CommonResult(Integer code, Boolean success, String message) {
        Code = code;
        this.message = message;
        this.success = success;
    }

    public static CommonResult success(Object data) {
        return new CommonResult(200, true, "请求成功", data);
    }
    public static CommonResult success(Object data,String msg) {
        return new CommonResult(200, true, msg, data);
    }
    public static CommonResult fail() {
        return new CommonResult(444, false, "请求失败");
    }

    public static CommonResult fail(Integer Code, String msg) {
        return new CommonResult(Code, false, msg);
    }
}
