package com.base.transfer;

/**
 * 4开头是客户端方面的错误
 * @author admin
 *
 */
public enum Msg {

	C400_001(400_001,"验证码错误"),
	C400_002(400_002,"注册失效,请重新发送验证码"),
	C400_003(400_003,"用户名已经存在,抱歉(灬ꈍ ꈍ灬)"),

	C400_004(400_004,"旧密码不对,请重新输入"),
	C400_005(200,"修改密码成功O(∩_∩)O"),
	C400_006(400_006,"用户账号或者密码错误"),
	C400_007(400_007,"用户账号已被超级管理员限制登录,请联系超级管理员"),
	;

    private int code;
	private String message;
	private Msg(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


}
