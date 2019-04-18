package com.springboot.controller;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.constant.LocalMAC;
import com.springboot.constant.MD5DigestAsHex;
import com.springboot.constant.MyConstant;
import com.springboot.service.TAdminService;

@Controller
public class LoginController {

	@Autowired
	TAdminService adminServiceImp;

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 登录
	 */
	@PostMapping("/login.html")
	public String login(@RequestParam(value = "username") String username,
			@RequestParam(name = "password") String password, Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws UnknownHostException, SocketException {
		// 输入信息验证
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			map.put(MyConstant.ERR_MSG, "账号或密码不能为空");
		}
		// 登录验证
		if (!adminServiceImp.login(username, password)) {
			map.put(MyConstant.ERR_MSG, "账号或密码错误");
			return "login";
		} else {
			// 获取本机地址
			InetAddress inetAddress = LocalMAC.getLocalHost();
			// 获取物理地址
			String localMac = LocalMAC.getLocalMac(inetAddress);
			// 将用户名，物理地址，密匙用进行加密，存到浏览器作为登录token
			String loginToken = MD5DigestAsHex.md5(username + localMac, MyConstant.SECRET_KEY);

			Cookie tokenCookie = new Cookie(MD5DigestAsHex.md5("loginToken", MyConstant.SECRET_KEY), loginToken);
			Cookie accountCookie = new Cookie("account", username);

			// 设置cookie的有效路径
			tokenCookie.setPath("/camgr");
			accountCookie.setPath("/camgr");

			// cookie有效期
			tokenCookie.setMaxAge(60 * 60 * 6);
			accountCookie.setMaxAge(60 * 60 * 6);

			// 存到浏览器
			response.addCookie(tokenCookie);
			response.addCookie(accountCookie);

			// 登录过后重定向，防止表单重复提交
			return "redirect:/main.html";
		}
	}

	/**
	 * 注销
	 */
	@GetMapping("/logout.html")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie tokenCookie = new Cookie(MD5DigestAsHex.md5("loginToken", MyConstant.SECRET_KEY), "");
		Cookie accountCookie = new Cookie("account", "");

		// 让cookie失效
		tokenCookie.setMaxAge(0);
		accountCookie.setMaxAge(0);

		// 刷新到浏览器
		response.addCookie(accountCookie);
		response.addCookie(tokenCookie);

		return "login";
	}
}