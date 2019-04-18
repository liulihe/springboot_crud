package com.springboot.component;

import java.net.InetAddress;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.constant.LocalMAC;
import com.springboot.constant.MD5DigestAsHex;
import com.springboot.constant.MyConstant;

public class LoginInterceptor implements HandlerInterceptor {

	Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	/**
	 * 登录拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		String accountValue = "";
		String localLoginToken = "";
		if (cookies != null && cookies.length >= 1) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("account")) {
					accountValue = cookie.getValue();
				}
				if (cookie.getName().equalsIgnoreCase(MD5DigestAsHex.md5("loginToken", MyConstant.SECRET_KEY))) {
					localLoginToken = cookie.getValue();
				}
			}
		}

		// 获取本机地址
		InetAddress inetAddress = LocalMAC.getLocalHost();
		// 获取物理地址
		String localMac = LocalMAC.getLocalMac(inetAddress);
		// 将用户名，物理地址，密码，密匙用进行加密，存到浏览器作为登录token
		String loginToken = MD5DigestAsHex.md5(accountValue + localMac, MyConstant.SECRET_KEY);

		if (loginToken.equalsIgnoreCase(localLoginToken)) {
			logger.info("登录验证成功");
			return true;
		} else {
			logger.info("登录验证失败");
			request.getRequestDispatcher("/index.html").forward(request, response);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
