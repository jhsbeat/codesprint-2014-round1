package com.hosang.skp.codesprint.solver;

public class Answer {

	private String answer;
	private float score;
	private int wordCount;
	private int wordFreq;
	
	public Answer() {
		super();
		this.answer = "";
		this.score = 0;
		this.wordCount = Integer.MAX_VALUE;
		this.wordFreq = 0;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public int getWordFreq() {
		return wordFreq;
	}
	public void setWordFreq(int wordFreq) {
		this.wordFreq = wordFreq;
	}
	
}
