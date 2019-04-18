package com.springboot.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.dao.TMemberMapper;
import com.springboot.entity.TMember;
import com.springboot.service.TMemberService;

@Service
public class TMemberServiceImp implements TMemberService {
	@Autowired
	TMemberMapper memberMapper;

	@Override
	public List<TMember> getMembers() {
		List<TMember> members = memberMapper.selectByExample(null);
		for (TMember tMember : members) {
			System.out.println("当前member信息：" + tMember.toString());
		}
		return members;
	}
}
