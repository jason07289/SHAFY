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

	// pno_list에 해당하는 모든 Post 조회(page)
	public List<Post> search(List<Integer> pno_list, int page);
	
	// 최근 한 달 게시글
	public List<Post> searchAMonth();
	// 최근 한 달 게시글의 pno_list
	public List<Integer> searchPnoAMonth();

	// [뉴스피드] 로그인 한 사용자의 follow하는 모든 pno_list;
	public List<Integer> followHash(String hashtag);
	// [뉴스피드] 로그인 한 사용자의 follow하는 모든 post(page)
	public List<Post> searchAllFollowList(List<Integer> pno_list, int page);
	
	// [Tab] hashtag를 가지는 모든 pno_list 불러오기
	public List<Integer> searchPnoByHash(String hashtag);
	// [Tab] hashtag를 가지는 모든 post(page)
	public List<Post> search(String hashtag, int page);
	
	// 최근 한 달 간 베스트 게시글 20개
	public List<Post> searchBest20();
	
	//내가 쓴 글 페이지 번호 가져오기
	public List<Integer> searchMyPost(String jwtId);

	// 마지막 페이지인지 확인(hashtag)
	public Post isLastPage(String hashtag, int limit);
	// 마지막 페이지인지 확인(pno_list)
	public Post isLastPage(List<Integer> pno_list, int page);
	
	public int count();
}
