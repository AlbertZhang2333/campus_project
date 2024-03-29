package com.example.ooadgroupproject.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;//对应编码
    private String msg;//返回信息

    private Long total;//总数

    private Object data;//数据
    private boolean ifSuccess;

    public static Result fail() {
        return resultF(400, "Fail", 0L, null);
    }

    public static Result success() {
        return resultS(200, "Success", 0L, null);
    }

    public static Result success(Object data) {
        return resultS(200, "Success", 0L, data);
    }
    public static Result fail(Object data) {
        return resultF(400, "Fail", 0L, data);
    }
    public static Result success(Long total, Object data) {
        return resultS(200, "Success", total, data);
    }

    public static Result resultS(int code, String msg, Long total, Object data) {
        Result ret = new Result();
        ret.setCode(code);
        ret.setMsg(msg);
        ret.setTotal(total);
        ret.setData(data);
        ret.ifSuccess=true;
        return ret;
    }
    public static Result resultF(int code, String msg, Long total, Object data) {
        Result ret = new Result();
        ret.setCode(code);
        ret.setMsg(msg);
        ret.setTotal(total);
        ret.setData(data);
        ret.ifSuccess=false;
        return ret;
    }
}
