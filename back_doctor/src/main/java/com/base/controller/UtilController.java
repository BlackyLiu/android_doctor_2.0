package com.base.controller;

import com.base.util.VerCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UtilController {


	/**
	 *  生成验证码 并放入session中
	 * @param request
	 * @param response
	 */
	@RequestMapping("/code")
	public void code(HttpServletRequest request, HttpServletResponse response) {
		VerCodeUtil.init(request, response);
	}


	@RequestMapping("/toImg")
	public String toImg(){
		return  "img/img";
	}







}
