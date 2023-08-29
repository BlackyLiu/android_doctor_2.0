package com.base.transfer;


public class ResultBean {

	/**
	 * 200 ok
	 *
	 */
	private Integer code = 200;

	private Object data;

	private String msg;

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}



	public static ResultBean ok() {
		 return ok("");
	}

	public static ResultBean ok(Object data) {
		 ResultBean bean = new ResultBean();
		 bean.setData(data);
		 bean.setMsg("成功");
		 return bean;
	}

	public static ResultBean error500() {
		return common(500,"服务器错误","");
	}

	public static ResultBean error500(String msg) {
		return common(500,msg,"");
	}

	public static ResultBean error400() {
		return common(400,"客户端错误","");
	}

	public static ResultBean error400(String msg) {
		return common(400,msg,"");
	}

	public static ResultBean msg(String msg) {
		return msg(200, msg);
	}

	public static ResultBean msg(Msg m) {
		  return msg(m.getCode(),m.getMessage());
	}

	public static ResultBean msg(Integer code,String msg) {
		return common(code, msg, "");
	}

	public static ResultBean common(Integer code,String msg,Object data) {
		 ResultBean bean = new ResultBean();
		 bean.setData(data);
		 bean.setMsg(msg);
		 bean.setCode(code);
		 return bean;
	}






}
