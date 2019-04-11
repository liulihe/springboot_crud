package com.springboot.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.dao.EmployeeDao;
import com.springboot.entity.Employee;

@Controller
public class MemberController {

	@Autowired
	EmployeeDao empDao;

	@GetMapping("/emps")
	public String getMembers(Model model) {
		Collection<Employee> emps = empDao.getAll();
		model.addAttribute("emps", emps);
		return "member/list";
	}

}
