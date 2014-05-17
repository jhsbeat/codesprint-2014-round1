package com.hosang.skp.codesprint.learning;

import java.util.HashMap;
import java.util.Map;

public class WordCounter {

	private Map<String, Integer> wordCountMap;

	public WordCounter() {
		super();
		this.wordCountMap = new HashMap<String, Integer>();
	}
	
	/**
	 * 해당 Word 의 Count 를 증가시킨다.
	 * @param word
	 */
	public void addWord(String word){
		if(this.wordCountMap.get(word) == null){
			this.wordCountMap.put(word, new Integer(1));
		}else{
			this.wordCountMap.put(word, this.wordCountMap.get(word) + 1);
		}
	}

	public Map<String, Integer> getWordCountMap() {
		return wordCountMap;
	}

	
	
}
