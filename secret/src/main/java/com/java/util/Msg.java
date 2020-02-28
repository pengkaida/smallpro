package com.java.util;

public class Msg {

	private String msg;

	private int code;

	public Msg() {

	}

	public Msg(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
