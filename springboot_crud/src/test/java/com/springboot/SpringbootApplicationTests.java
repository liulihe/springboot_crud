package com.springboot;

import java.io.File;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.constant.LocalMAC;
import com.springboot.constant.MD5DigestAsHex;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Autowired
	DataSource dataSource;

	@Test
	public void contextLoads() throws SQLException {
		System.out.println(dataSource.getClass() + "-------------------------------------------------------------");
		Connection connection = dataSource.getConnection();
		System.out.println(connection.getClass());
		connection.close();
		System.out.println("=================================================================");
	}

	/**
	 * 使用逆向工程生成实体类，dao，映射文件
	 */
	@Test
	public void testMBGTest() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("MBG.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("生成成功");
	}

	@Test
	public void testMD5DigestAsHex() {
		String md5 = MD5DigestAsHex.md5("admin", "123456");
		System.out.println(md5);
	}

	@Test
	public void testGetLocalHost() throws UnknownHostException, SocketException {
		// 得到IP
		InetAddress ia = InetAddress.getLocalHost();
		LocalMAC.getLocalMac(ia);
	}

}
