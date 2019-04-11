package com.springboot.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.constant.MyConstant;

@Controller
public class LoginController {

	@PostMapping("/login.html")
	public String login(@RequestParam(value = "username") String username,
			@RequestParam(name = "password") String password, Map<String, Object> map, HttpSession session) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || !password.equals("123456")) {
			map.put(MyConstant.ERR_MSG, "账号或密码错误");
			return "login";
		} else {
			session.setAttribute(MyConstant.CUR_ACCOUNT, username);
			return "redirect:/main.html";
		}
	}
}
