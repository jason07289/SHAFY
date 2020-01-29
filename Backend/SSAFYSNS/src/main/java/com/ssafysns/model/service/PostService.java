package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.Post;

public interface PostService {
	public void insert(Post post);

	public void delete(int pno);

	public void update(Post post);

	public Optional<Post> search(int pno);

	public List<Post> searchAll();
	
	public List<Post> searchAllHashTags();
	
	public List<Post> search(String hashtag);	//hashtag를 가지는 post리스트 불러오기
	
	public List<Integer> searchPostNo(String hashtag); //hashtag를 가지는 post의 번호를 리스트로 불러오기

	public List<Integer> testAllHash(String id);
	
	public int count();
}
