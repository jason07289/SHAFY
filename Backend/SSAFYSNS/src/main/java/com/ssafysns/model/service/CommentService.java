package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.model.dto.Comment;

public interface CommentService {
	public List<Comment> joinPost(String hashtag);
	public void insert(Comment comment);
	public void update(Comment comment);
	public void delete(int no);
	public Comment search(String id);
	public List<Comment> searchPno(int pno);//해당 게시글 댓글 조회
	public List<Comment> searchId(String id);	//회원별 댓글 조회
//	public List<Comments> searchAll();
}
