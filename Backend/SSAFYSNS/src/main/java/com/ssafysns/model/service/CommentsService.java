package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.model.dto.Comments;

public interface CommentsService {
	public void insert(Comments comments);
	public void update(Comments comments);
	public void delete(int no);
	public Comments search(String id);
	public List<Comments> searchComments();//해당 게시글 댓글 조회
	public List<Comments> searchId(String id);	//회원별 댓글 조회
//	public List<Comments> searchAll();
}
