package org.scientist.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.app.entity.Keyword;

import org.ansj.domain.Term;

public class WordStatistics implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String[] noun = { "n", "nr", "ns", "nsf", "nt", "nz", "nl",
			"nw", "s" };
	private HashMap<String, Integer> keywords = null;
	private static final String PATH = "D:\\object\\";
	private static final String SURFIX = ".hash.out";
	public static final String PAGE_NUM_INDEX ="keywords_page_number";
	public WordStatistics() {
		keywords = new HashMap<String, Integer>();
	}

	public WordStatistics(HashMap<String, Integer> keywordsmap) {
		keywords = keywordsmap;
	}

	/**
	 * 將統計結果寫會到hash
	 * 
	 * @param args
	 * @return
	 */
	public HashMap<String, Integer> staticsToHash(String... args) {
		for (int i = 0; i < args.length; i++) {
			List<Term> terms = WordSegment.baseAnalysis(args[i]);
			for (Term term : terms) {
				if (existNoun(term)) {
					insertIntoHash(term);
				}
			}
		}
		
		//添加文章游標，用於檢查是否有沒被處理過的文章
		if(keywords.containsKey(PAGE_NUM_INDEX)) {
			keywords.put(PAGE_NUM_INDEX, keywords.get(PAGE_NUM_INDEX) + args.length);
		} else {
			keywords.put(PAGE_NUM_INDEX, args.length);
		}
		return keywords;
	}

	/**
	 * 將keywords序列化到文件
	 * @param userId
	 * @param map
	 * @return
	 */
	public boolean serilizeObject(int userId, Map<String, Integer> map) {
		File file = new File(PATH + userId + SURFIX);
		if(file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream fos = new FileOutputStream(PATH + userId + SURFIX);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 從文件中反序列化hash對象
	 * @param userId
	 * @return
	 */
	public void deSerilizeObject(int userId) {
		if(!ifFileExist(PATH + userId + SURFIX)) {
			return ;
		}
		try {
			FileInputStream fis = new FileInputStream(PATH + userId + SURFIX);
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			HashMap<String, Integer> keyword = (HashMap<String, Integer>) ois.readObject();
			ois.close();
			this.keywords = keyword;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	/**
	 * 判定文件是否存在
	 * @param fileName
	 * @return
	 */
	public boolean ifFileExist(String fileName) {
		return new File(fileName).exists();
	}
	public void cleanKeywords() {
		keywords = null;
	}
	
	public boolean SerFileExist(int userId) {
		return new File(PATH + userId + SURFIX).exists();
	}

	/**
	 * 將一個短語添加到hash中
	 * 
	 * @param term
	 */
	private void insertIntoHash(Term term) {
		if (this.keywords.containsKey(term.getName())) {
			this.keywords.put(term.getName(), this.keywords.get(term.getName()) + 1);
		} else {
			keywords.put(term.getName(), 1);
		}
	}

	/**
	 * 判定術語是不是名詞
	 * 
	 * @param str
	 * @return
	 */
	private static boolean existNoun(Term str) {
		for (String s : noun) {
			if (str.getNatrue().natureStr.equalsIgnoreCase(s))
				return true;
		}
		return false;
	}
	
	/**
	 * 獲得文章處理的起始頁數
	 * @param keyword
	 * @return
	 */
	public int getBeginPageId(HashMap<String, Integer> keyword) {
		return keyword.get(PAGE_NUM_INDEX);
	}
	
	public static void main(String ...args) {
		int userId = 11202020;
		String pages[] = {"有俄罗斯国会议员，9号在社交网站推特表示，美国中情局前雇员斯诺登，已经接受委内瑞拉的庇护，不过推文在发布几分钟后随即删除。俄罗斯当局拒绝发表评论，而一直协助斯诺登的维基解密否认他将投靠委内瑞拉。　　俄罗斯国会国际事务委员会主席普什科夫，在个人推特率先披露斯诺登已接受委内瑞拉的庇护建议，令外界以为斯诺登的动向终于有新进展。　　不过推文在几分钟内旋即被删除，普什科夫澄清他是看到俄罗斯国营电视台的新闻才这样说，而电视台已经作出否认，称普什科",
		"委内瑞拉驻莫斯科大使馆、俄罗斯总统府发言人、以及外交部都拒绝发表评论。而维基解密就否认斯诺登已正式接受委内瑞"};
		WordStatistics words = new WordStatistics();
		if(!words.SerFileExist(userId)) {
			System.out.println("文件不存在");
			words = new WordStatistics();
			words.staticsToHash(pages);
			words.serilizeObject(userId, words.keywords);
			println(words.keywords);
			words.cleanKeywords();
		} else {
			System.out.println("文件存在");
			words = new WordStatistics();
			words.deSerilizeObject(userId);
			println(words.keywords);
			words.cleanKeywords();
		}
	}
	
	/**
	 * 根據哈希，獲得關鍵詞的排序數組
	 * @param keywords2
	 * @param top
	 * @return
	 */
	public Keyword[] getTop(HashMap<String, Integer> keywords2, int top) {
		
		//申請一個對比字串
		Keyword keywordArr[];
		int top_temp = top;
		if(keywords2.size()>=top_temp) {
		} else {
			top_temp = keywords2.size();
		}
		//同c，必須初始化才能進行下部操作
		keywordArr = new Keyword[top_temp];
		for(int i = 0; i<keywordArr.length; i++) {
			keywordArr[i] = new Keyword();
		}
		Set<String> set = keywords2.keySet();
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			Object key = it.next();
			for(int i = 0; i < top_temp; i++) {
				if(Integer.valueOf(keywords2.get(key))>= keywordArr[i].getNumber()) {
					for(int j = top_temp - 1; j > i; j--) {
						keywordArr[j] = keywordArr[j-1];//向後翻盤
					}
					keywordArr[i] = new Keyword(key.toString(), Integer.valueOf(keywords2.get(key)));
					break;
				}
			}
		}
		return keywordArr;
	}
	
	/**
	 * 打印MAP
	 * @param keywords2
	 */
	public static void println(HashMap<String, Integer> keywords2) {
		Set<String> set = keywords2.keySet();
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			Object key = it.next();
			System.out.println(key.toString() + ":" + keywords2.get(key).toString());
		}
	}

	public HashMap<String, Integer> getKeywords() {
		return keywords;
	}

	public void setKeywords(HashMap<String, Integer> keywords) {
		this.keywords = keywords;
	}
	
}
