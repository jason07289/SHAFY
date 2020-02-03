package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.Post;

public interface PostService {
	public void insert(String id, Post post);

	public Boolean delete(String id, int pno);

	public Boolean update(String id, Post post);

	public Optional<Post> search(int pno);

	public List<Post> searchAll();

	public List<Integer> followHashPno(String id); // [뉴스피드] follow하는 모든 pno 리스트 가져오기
	
	public List<Post> searchAllFollowList(List<Integer> pno_list); // [뉴스피드] follow하는 모든 게시글 리스트 가져오기
	
	public List<Integer> searchPnoList(String hashtag); //[tab] hashtag를 가지는 pno 리스트로 불러오기
	
	public List<Post> search(String hashtag);	//[tab] hashtag를 가지는 [tab] 리스트 불러오기
	
	public int count();

//	public Post searchById(int pno);
}
