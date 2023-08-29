package com.base.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * session工具类
 */
public class SessionContext {
	public static final String IDENTIFY_CODE_KEY = "_consts_identify_code_key_";// 其他人不得占用
	public static final String AUTH_USER_KEY = "_consts_auth_user_key_";// 其他人不得占用
	//前端的login key
	public static final String  F_LOGIN_KEY = "F_LOGIN_KEY";
	//发送短信时间的key
	public static final String  UN_DX_KEY = "UN_DX_KEY";

	public static void setDxKey(HttpServletRequest request) {
		   request.getSession().setAttribute(UN_DX_KEY, new Date());
	}

	public static Date getDxKey(HttpServletRequest request) {
		  return  (Date) request.getSession().getAttribute(UN_DX_KEY);
	}

	public static void setFLogin(HttpServletRequest request,Object user) {
		request.getSession().setAttribute(F_LOGIN_KEY, user);
	}

	public static void fLogout(HttpServletRequest request) {
		request.getSession().removeAttribute(F_LOGIN_KEY);
	}

	public static  Object fIsLogin(HttpServletRequest request) {
		return request.getSession().getAttribute(F_LOGIN_KEY);
	}



	public static void logout(HttpServletRequest request) {
		    request.getSession().removeAttribute(AUTH_USER_KEY);
	}

	public static Object isLogin(HttpServletRequest request) {
		    return request.getSession().getAttribute(AUTH_USER_KEY);
	}

	public static void setLoginUser(HttpServletRequest request,Object user) {
		request.getSession().setMaxInactiveInterval(60*60*24); //设置后台登录时间为一天
		request.getSession().setAttribute(AUTH_USER_KEY, user);
	}

	// 获取验证码
	public static String getIdentifyCode(HttpServletRequest request) {
		if (request.getSession().getAttribute(IDENTIFY_CODE_KEY) != null) {
			return getAttribute(request, IDENTIFY_CODE_KEY).toString();
		} else {
			return null;
		}
	}

	public static void setIdentifyCode(HttpServletRequest request,String code) {
		request.getSession().setAttribute(IDENTIFY_CODE_KEY, code);
	}

	// 获取属性
	public static Object getAttribute(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}

	// 设置属性
	public static void setAttribute(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

	// 删除属性
	public static void removeAttribute(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

}
