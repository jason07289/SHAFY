package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.Post;

public interface PostService {
	public void insert(String id, Post post);

	public Boolean delete(int pno);

	public Boolean update(Post post);

	public Optional<Post> search(int pno);

	public List<Post> searchAll();
	
	public List<Post> searchAMonth();
	public List<Integer> searchPnoAMonth();
	
	public List<Integer> followHashPno(String id); // [뉴스피드] follow하는 모든 pno 리스트 가져오기
	
	public List<Post> searchAllFollowList(List<Integer> pno_list, int page); // [뉴스피드] follow하는 모든 게시글 리스트 가져오기
	
	public List<Integer> searchPnoByHash(String hashtag, int page); //[tab] hashtag를 가지는 pno 리스트로 불러오기
	
	public List<Post> search(String hashtag, int page);	//[tab] hashtag를 가지는 [tab] 리스트 불러오기
	
	public int count();

	public List<Post> searchBest20();	//베스트 게시글 20개 가져오기
	
	public Post isLastPage(String hashtag, int limit);

	public List<Post> followHash(String hashtag);
	
//	public Post searchById(int pno);
}
