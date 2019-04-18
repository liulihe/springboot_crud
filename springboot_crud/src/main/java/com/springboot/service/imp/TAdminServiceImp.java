package com.springboot.service.imp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.constant.MD5DigestAsHex;
import com.springboot.dao.TAdminMapper;
import com.springboot.entity.TAdmin;
import com.springboot.entity.TAdminExample;
import com.springboot.entity.TAdminExample.Criteria;
import com.springboot.service.TAdminService;

@Service
public class TAdminServiceImp implements TAdminService {
	Logger logger = LoggerFactory.getLogger(TAdminServiceImp.class);

	@Autowired
	TAdminMapper userMapper;

	public boolean login(String username, String password) {
		TAdminExample example = new TAdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andUAccountEqualTo(username);
		List<TAdmin> list = userMapper.selectByExample(example);
		if (list.isEmpty() || list.size() > 1) {
			logger.info("无此用户，请输入正确的用户名");
			return false;
		} else {
			TAdmin tUser = list.get(0);
			try {
				return MD5DigestAsHex.loginVerify(username, password, tUser.getuPassword());
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("登录失败，用户名或密码错误");
				return false;
			}
		}
	}

}
