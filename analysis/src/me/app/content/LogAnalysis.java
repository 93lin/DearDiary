package me.app.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import me.Const;

import org.fastmvc.bridge.Bridge;
import org.fastmvc.data.Jsoner;
import org.fastmvc.db.MySqlDB;
import org.fastmvc.db.SqlParser;
import org.fastmvc.excption.ServletRuntimeException;
import org.fastmvc.util.Url;
import org.scientist.word.WordStatistics;

public class LogAnalysis {

	@Url(value = "json/log/analysis")
	public void analysis(Bridge bridge) throws ServletRuntimeException {
		
		//針對請求，首先進行驗證
		if(!bridge.at("user_id").integer().validateSucced || !bridge.at("top").integer().validateSucced) {
			bridge.res.setStatus(Const.PARAMETER_ERROR);
			return;
		}
		int userId = Integer.valueOf(bridge.req.getParameter("user_id"));
		int top = Integer.valueOf(bridge.req.getParameter("top"));
		
		int page_number = getPageNumByCount(userId);
		WordStatistics statics = new WordStatistics();
		if(statics.SerFileExist(userId)) {
			statics.deSerilizeObject(userId);
			if(page_number == statics.getKeywords().get(WordStatistics.PAGE_NUM_INDEX)) {
				statics.serilizeObject(userId, statics.getKeywords());
				bridge.writeJson(statics.getTop(statics.getKeywords(), top));
				statics.cleanKeywords();
				statics = null;
			} else {
				String diary[] = getPage(statics.getKeywords().get(WordStatistics.PAGE_NUM_INDEX), page_number, userId);
				HashMap<String, Integer> keywords = statics.staticsToHash(diary);
				statics.serilizeObject(userId, keywords);
				bridge.writeJson(statics.getTop(keywords, top));
				statics.cleanKeywords();
				statics = null;
			}
		} else {
			String [] posts = getPosts(userId);
			HashMap<String, Integer> keywords = statics.staticsToHash(posts);
			statics.serilizeObject(userId, keywords);
			bridge.writeJson(statics.getTop(keywords, top));
			statics.cleanKeywords();
			statics = null;
		}
	}
	
	/**
	 * 篩選沒有被統計的文章
	 * @param page_begin
	 * @param page_end
	 * @param userId
	 * @return
	 */
	private String[] getPage(int page_begin, int page_end, int userId) {
		SqlParser parser = new SqlParser();
		String page[] = new String[page_end - page_begin];
		parser.select("diary_content").from("diary").where("diary_userId").equal(userId).and("diary_id").more(page_begin + 1).and("diary_id")
		.lower(page_end);
		try {
			String result = MySqlDB.getInstance().executeSql(parser);
			ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>) Jsoner.parseObjectToMapList(result);
			for(int i = 0; i < data.size(); i++) {
				page[i] = data.get(i).get("diary_content");
			}
			return page;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 獲取用戶的文章數
	 * @param userId
	 * @return
	 */
	private int getPageNum(int userId) {
		SqlParser parser = new SqlParser();
		parser.select().from("diary").where("diary_userId").equal(userId);
		try {
			String result = MySqlDB.getInstance().executeSql(parser);
			ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>) Jsoner.parseObjectToMapList(result);
			return data.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 獲取用戶的文章數
	 * @param userId
	 * @return
	 */
	private int getPageNumByCount(int userId) {
		SqlParser parser = new SqlParser();
		parser.selectCount().from("diary").where("diary_userId").equal(userId);
		try {
			String result = MySqlDB.getInstance().executeSql(parser);
			HashMap<String, Integer> data = (HashMap<String, Integer>) Jsoner.parseObjectToMap(result);
			return data.get("count");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 獲取用戶所有的文章
	 * @param userId
	 * @return
	 */
	private String[] getPosts(int userId) {
		SqlParser parser = new SqlParser();
		ArrayList<String> page = new ArrayList<String>();
		parser.select("diary_content").from("diary").where("diary_userId").equal(userId);
		try {
			String result = MySqlDB.getInstance().executeSql(parser);
			ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>) Jsoner.parseObjectToMapList(result);
			for(int i = 0; i < data.size(); i++) {
				page.add(data.get(i).get("diary_content"));
			}
			String[] temp = new String[page.size()];
			for(int i = 0; i < temp.length; i++) {
				temp[i] = page.get(i);
			}
			return temp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
