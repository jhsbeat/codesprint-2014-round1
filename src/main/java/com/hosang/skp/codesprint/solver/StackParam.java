package com.hosang.skp.codesprint.solver;

public class StackParam {

	private String query;
	private String prefix;
	private Answer answer;
	private int matchCount;
	private int wordFreq;
	private int limit;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	public int getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}
	public int getWordFreq() {
		return wordFreq;
	}
	public void setWordFreq(int wordFreq) {
		this.wordFreq = wordFreq;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
