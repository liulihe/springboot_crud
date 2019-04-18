package com.springboot.constant;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalMAC {

	public static InetAddress getLocalHost() throws UnknownHostException, SocketException {
		// 得到IP
		InetAddress inetAddress = InetAddress.getLocalHost();
		return inetAddress;
	}

	public static String getLocalMac(InetAddress inetAddress) throws SocketException, UnknownHostException {
		Logger logger = LoggerFactory.getLogger(LocalMAC.class);
		// 获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
		logger.info("mac数组长度：" + mac.length);
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			// 字节转换为整数
			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);
			logger.info("每8位:" + str);
			if (str.length() == 1) {
				sb.append("0" + str);
			} else {
				sb.append(str);
			}
		}
		logger.info("本机MAC地址:" + sb.toString().toUpperCase());
		return sb.toString().toUpperCase();
	}
}
