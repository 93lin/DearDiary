package org.tool;

import java.io.IOException;
import java.util.HashMap;

import org.fastmvc.data.Jsoner;
import org.jsoup.Jsoup;

public class IPAddress {

	private static String covertIP(String IP) {
		try {
			return Jsoup.connect("http://ipapi.sinaapp.com/api.php?f=json&ip=" + IP).header("Mimetype", "application/json; charset=utf-8").
			get().body().text().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  根據IP獲得城市名的全稱，如北京市，黑龍江省哈爾冰市
	 * @param IP
	 * @return
	 */
	public static synchronized String getCity(String IP) {
		HashMap<String,String> map = (HashMap<String, String>) Jsoner.parseObjectToMap(covertIP(IP));
		return map.get("area1").toString();
	}
}
