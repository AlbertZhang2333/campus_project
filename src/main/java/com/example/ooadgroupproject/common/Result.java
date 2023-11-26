package com.example.ooadgroupproject.common;

import lombok.Data;

@Data
public class Result {

    private int code;//对应编码
    private String msg;//返回信息

    private Long total;//总数

    private Object data;//数据

    public static Result fail() {
        return result(400, "Fail", 0L, null);
    }

    public static Result success() {
        return result(200, "Success", 0L, null);
    }

    public static Result success(Object data) {
        return result(200, "Success", 0L, data);
    }

    public static Result success(Long total, Object data) {
        return result(200, "Success", total, data);
    }

    public static Result result(int code, String msg, Long total, Object data) {
        Result ret = new Result();
        ret.setCode(code);
        ret.setMsg(msg);
        ret.setTotal(total);
        ret.setData(data);

        return ret;
    }
}