package com.ssafysns.model.dto;

public class SearchResult {
	private int cnt;
	private String hashtag;

	public SearchResult() {
	}

	public SearchResult(int cnt, String hashtag) {
		super();
		this.cnt = cnt;
		this.hashtag = hashtag;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

}
