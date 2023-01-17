package com.weblog.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果数据返回通用类
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/2
 */

@Data
public class JsonResult<T> {
    /**
     *  编码：200成功，500和其它数字为失败或其他异常情况
     **/
    private Integer code;
    /**
     * 返回描述信息
     **/
    private String msg;
    /**
     * 信息返回数据
     **/
    private T data;
    /**
     *  动态数据
     **/

    private Map map = new HashMap();

    public JsonResult() {

    }

    public JsonResult(Integer code) {
        this.code = code;
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> JsonResult<T> success(String msg, T object) {
        JsonResult<T> result = new JsonResult<T>();
        result.msg = msg;
        result.data = object;
        result.code = 200;
        return result;
    }

    public static <T> JsonResult<T> success(String msg, Integer code) {
        JsonResult result = new JsonResult();
        result.msg = msg;
        result.code = code;
        return result;
    }

    public static <T> JsonResult<T> success( Integer code) {
        JsonResult result = new JsonResult();
        result.code = code;
        return result;
    }

    public static <T> JsonResult<T> success(String msg) {
        JsonResult<T> result = new JsonResult<T>();
        result.msg = msg;
        result.code = 200;
        return result;
    }

    public static <T> JsonResult<T> success() {
        JsonResult<T> result = new JsonResult<T>();
        result.code = 200;
        return result;
    }

    public static <T> JsonResult<T> error(String msg, Integer code) {
        JsonResult result = new JsonResult();
        result.msg = msg;
        result.code = code;
        return result;
    }

    public static <T> JsonResult<T> error(Integer code) {
        JsonResult result = new JsonResult();
        result.code = code;
        return result;
    }

    public static <T> JsonResult<T> error() {
        JsonResult result = new JsonResult();
        result.code = 500;
        return result;
    }

    public JsonResult<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
