package com.base.controller;

import com.base.bean.Admin;
import com.base.util.SessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("jump")
public class JumpController {

	@RequestMapping("toLogin")
	public String toLogin() {
		  return "login";
	}

	@RequestMapping("toIndex")
	public String index(HttpServletRequest request,ModelMap  modelMap) {
		Admin admin = (Admin) SessionContext.isLogin(request);
		if(admin == null) {
			return "redirect:/jump/toLogin";
		}
		modelMap.addAttribute("admin", admin);
		return "index";
	}

	@RequestMapping("toDoctorList")
	public String toDoctorList() {
		return "doctor/doctorList";
	}

	@RequestMapping("toDoctorAdd")
	public String toDoctorAdd() {
		return "doctor/doctorAdd";
	}

	@RequestMapping("toPatientList")
	public String toPatientList() {
		return "patient/patientList";
	}

	@RequestMapping("toPatientAdd")
	public String toPatientAdd() {
		return "patient/patientAdd";
	}

	@RequestMapping("toChatList")
	public String toChatList() {
		return "chat/chatList";
	}

	@RequestMapping("toChatAdd")
	public String toChatAdd() {
		return "chat/chatAdd";
	}

	@RequestMapping("toAppointmentList")
	public String toAppointmentList() {
		return "appointment/appointmentList";
	}

	@RequestMapping("toAppointmentAdd")
	public String toAppointmentAdd() {
		return "appointment/appointmentAdd";
	}

	@RequestMapping("toAdminList")
	public String toAdminList() {
		return "admin/adminList";
	}

	@RequestMapping("toAdminAdd")
	public String toAdminAdd() {
		return "admin/adminAdd";
	}


}
