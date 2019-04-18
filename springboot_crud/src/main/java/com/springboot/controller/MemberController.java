package com.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.springboot.entity.TMember;
import com.springboot.service.TMemberService;

@Controller
public class MemberController {

	@Autowired
	TMemberService tMemberServiceImp;

	Logger logger = LoggerFactory.getLogger(MemberController.class);

	@GetMapping("/member")
	public String getMembers(Model model) {
		List<TMember> members = tMemberServiceImp.getMembers();
		model.addAttribute("members", members);
		return "member/list";
	}
}