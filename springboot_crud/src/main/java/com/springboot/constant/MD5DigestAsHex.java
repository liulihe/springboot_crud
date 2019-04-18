package com.springboot.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

public class MD5DigestAsHex {

	/**
	 * 生成密码
	 */
	public static String md5(String text, String key) {
		return DigestUtils.md5DigestAsHex((text + key).getBytes());
	}

	/**
	 * 验证密码
	 */
	public static boolean loginVerify(String text, String key, String md5) throws Exception {
		Logger logger = LoggerFactory.getLogger(MD5DigestAsHex.class);
		// 根据传入的密钥进行验证
		String md5Text = md5(text, key);
		if (md5Text.equalsIgnoreCase(md5)) {
			logger.info("验证成功！");
			return true;
		}
		return false;
	}

}
