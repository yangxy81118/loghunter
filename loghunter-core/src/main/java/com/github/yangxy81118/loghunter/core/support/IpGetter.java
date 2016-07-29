package com.github.yangxy81118.loghunter.core.support;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IpGetter {
	/**
	 * 单网卡名称
	 */
	private static final String NETWORK_CARD = "eth0";

	/**
	 * 绑定网卡名称
	 */
	private static final String NETWORK_CARD_BAND = "bond0";

	/**
	 * 
	 * Description: 得到本机名<br>
	 * 
	 * @return
	 * @see
	 */
	public static String getLocalHostName() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostName();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Description: linux下获得本机IPv4 IP<br>
	 * 
	 * @return
	 * @see
	 */
	public static String getLocalIP() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> e1 = (Enumeration<NetworkInterface>) NetworkInterface
					.getNetworkInterfaces();
			while (e1.hasMoreElements()) {
				NetworkInterface ni = e1.nextElement();

				// 单网卡或者绑定双网卡
				if ((NETWORK_CARD.equals(ni.getName()))
						|| (NETWORK_CARD_BAND.equals(ni.getName()))) {
					Enumeration<InetAddress> e2 = ni.getInetAddresses();
					while (e2.hasMoreElements()) {
						InetAddress ia = e2.nextElement();
						if (ia instanceof Inet6Address) {
							continue;
						}
						ip = ia.getHostAddress();
					}
					break;
				} else {
					continue;
				}
			}
		} catch (SocketException e) {
		}
		return ip;
	}
	
	public static void main(String[] args) {
		System.out.println(getLocalIP());
	}
}
