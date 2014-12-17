package org.tool;

import java.io.IOException;

import org.jsoup.Jsoup;

public class WeatherReport {

	/**
	 * 根據IP地址獲得相應的天氣情況，參數如下：
	 * 參考的API爲心知天氣API
	 * {
	    "status": "OK",                     //状态信息。正常返回时值为"OK"，异常时返回具体错误信息。
	    "weather": [{                       //城市天气数组。同时查询多个城市时，该数组则包含多个对象。
	        "city_name": "北京",            //城市名
	        "city_id": "CHBJ000000",        //城市ID，V2.0以上采用心知城市ID编码
	        "last_update": "2014-01-26T12:52:57+08:00", //数据更新时间。该城市的本地时间，采用国际ISO8601时间格式标准。
	        "now": {                        //实时天气
	            "text": "多云",             //天气情况
	            "code": "4",                //天气情况代码 (天气代码与天气图标对应说明)
	            "temperature": "1",         //当前实时温度
	            "feels_like": "-4",         //当前实时体感温度
	            "wind_direction": "南",     //风向
	            "wind_speed": "15.64",      //风速。单位：km/h
	            "wind_scale": "3",          //风力等级。根据风速计算的风力等级，参考百度百科定义：风力等级。**V1.0新增**
	            "humidity": "47",           //湿度。单位：百分比%
	            "visibility": "11.0",       //能见度。单位：公里km
	            "pressure": "1015.92",      //气压。单位：百帕hPa
	            "pressure_rising": "N/A",   //气压变化。0或steady为稳定，1或rising为升高，2或falling为降低
	            "air_quality": null         //now.json不返回空气质量。如只需空气质量数据，请访问air.json。
	            }
	        }
	    ]
		}
	 * @param ip
	 * @return
	 */
	public static synchronized String getWeather(String ip) {
		try {
			return Jsoup.connect("https://api.thinkpage.cn/v2/weather/now.json?city=" + ip +"&language=zh-chs&unit=c&aqi=city&key=VPVEJTQRLN").
			header("Mimetype", "application/json; charset=utf-8").ignoreContentType(true).
			get().body().text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String ... args) {
		System.out.println(getWeather("220.181.111.86"));
		System.out.println(IPAddress.getCity("122.205.7.82"));
	}
}
