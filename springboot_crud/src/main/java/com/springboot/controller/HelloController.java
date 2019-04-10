package com.springboot.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.entity.Desk;

@Controller
public class HelloController {
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "<h1>hello world</h1>";
	}

	@RequestMapping("/success")
	public String success(Map<String, Object> map) {
		// templates/success.html
		map.put("hello", "<h1>雷猴!</h1>");
		map.put("mydesk", new Desk[] { new Desk("red", 21), new Desk("yellow", 23) });
		map.put("flag", false);
		return "success";
	}
}