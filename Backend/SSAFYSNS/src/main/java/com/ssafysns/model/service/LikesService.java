package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.PostLikes;

public interface LikesService {
	
	public void insert(Likes likes);
	public void insert(PostLikes likes);
	
	public void delete(Likes likes);	//댓글 좋아요 취소
	void delete(PostLikes postLikes);	//게시글 좋아요 취소
	
	public List<Integer> searchById(String id);	//좋아요 누른 글 목록 검색
	
	public List<Integer> selectCnoById(String jwtId);		//내가 좋아요 누른 모든 댓글 번호
	public List<Integer> selectPnoById(String jwtId);	//내가 좋아요 누른 모든 게시글 번호
	
	public List<Boolean> likeBooleanComment(List<Integer> my_like_comment, int pno);
	public List<Boolean> likeBooleanPost(List<Integer> my_like_post, List<Integer> follow_list);
	
}
