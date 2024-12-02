package com.qin.cwgl.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
//通用的 API 响应结果封装类，用于统一处理后端接口的返回格式
//A general API response result encapsulation class, used to unify the return format of the backend interface
/**
 * 返回数据 统一封装响应
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	// 默认构造函数，创建成功响应
	public R() {
		put("code", 0); // 状态码 0 表示成功
		put("msg", "success"); // 默认成功消息
	}
	// 默认错误响应
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Unknown exception, please contact the administrator");
	}
	// 自定义错误消息
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	// 自定义错误码和消息
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}
	// 带消息的成功响应
	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	// 带数据的成功响应
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	// 默认成功响应
	public static R ok() {
		return new R();
	}
	//链式调用方法
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public  Integer getCode() {

		return (Integer) this.get("code");
	}

}
