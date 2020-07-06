package com.zdd.core.base;

import lombok.Data;

@Data
public class BaseResponse<T> {

	private Integer code;
	private String msg;
	private T data;

	public BaseResponse() {

	}

	public BaseResponse(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}