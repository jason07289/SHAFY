package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.Post;

public interface LikesService {
	
	public void insert(Likes likes);
	public void delete(String id, int pno, int cno);
	
	public List<Integer> searchById(String id);	//좋아요 누른 글 목록 검색
//	public List<Likes> searchByAllPno(List<Integer> pno); //게시글 좋아요 기록 검색
	public List<Likes> searchByPno(int pno);
	
	public void likeCheck(int cno, String id);

	public List<Integer> selectCnoById(String id);
	
	public List<Boolean> likeBooleanList(List<Integer> cno_list, int pno);

}
