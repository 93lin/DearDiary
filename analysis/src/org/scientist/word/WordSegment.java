package org.scientist.word;

import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

/**
 * 添加分詞門面
 * @author jhsr
 *
 */
public class WordSegment {
	/**
	 * 基本分詞
	 * @param str
	 * @return
	 */
	public static List<Term> baseAnalysis(String str) {
		return BaseAnalysis.parse(str);
	}
	
	/**
	 * 精準分詞
	 * @param str
	 * @return
	 */
	public static List<Term> preciseAnalysis(String str) {
		return ToAnalysis.parse(str);
	}
	public static void main(String... args) {
		List<Term> parse = BaseAnalysis.parse("你嗎的德安");
		System.out.println(parse.get(0).getNatrue().natureStr);
	}
	
}
