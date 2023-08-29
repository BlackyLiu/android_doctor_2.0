package com.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.bean.Admin;
import com.base.service.IAdminService;
import com.base.transfer.Msg;
import com.base.transfer.ResultBean;
import com.base.util.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BackLoginController {



	@Autowired
	private IAdminService adminService;



	@ResponseBody
	@RequestMapping("start_login")
	public ResultBean login(String data, HttpServletRequest request) {
		ResultBean resultBean = new ResultBean();
		JSONObject jsonObject = JSON.parseObject(data);
		String code = (String) jsonObject.get("code");
		if (!code.equalsIgnoreCase(SessionContext.getIdentifyCode(request))) {
			return ResultBean.msg(Msg.C400_001);
		}
		Admin admin = (Admin) JSONObject.parseObject(jsonObject.getString("admin"), Admin.class);
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username",admin.getUsername());
		queryWrapper.eq("password",admin.getPassword());
		Admin a = adminService.getOne(queryWrapper);
		if(a!=null){
			SessionContext.setLoginUser(request, a);
			return  ResultBean.ok(a);
		}else {
			return ResultBean.msg(Msg.C400_006);
		}
	}

	@RequestMapping("loginInfo")
	@ResponseBody
	public ResultBean loginInfo( HttpServletRequest request) {
		ResultBean resultBean = new ResultBean();
		Object login = SessionContext.isLogin(request);
		return resultBean.ok(login);
	}


	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		SessionContext.logout(request);
		return "redirect:/jump/toLogin";
	}

}
