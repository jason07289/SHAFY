package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Post;

public interface CommentService {
	public List<Comment> joinPost(String hashtag);
	public List<Comment> searchCommentList(String hashtag);
	public void insert(String id, Comment comment);
	public void update(Comment comment);
	public boolean  delete(String jwtId, int no);
	public Comment search(String id);
	public List<Comment> searchPno(int pno);//해당 게시글 댓글 조회
	public List<Comment> searchId(String id);	//회원별 댓글 조회
	public List<Comment> searchAllCommenetList(List<Integer> list);
}
