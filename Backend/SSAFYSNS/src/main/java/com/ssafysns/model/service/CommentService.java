package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Post;

public interface CommentService {
	public void insert(String id, Comment comment);
	public void update(Comment comment);
	public boolean  delete(String jwtId, int no);
	
	//cno로 하나의 댓글 조회
	public Comment search(int cno);
	//pno로 모든 Comment(댓글) 리스트 조회
	public List<Comment> searchPno(int pno);
	//pno로 모든 cno(댓글번호) 리스트 조회
	public List<Integer> searchCnoByPno(int pno); //해당 게시글 댓글번호 조회
	
	//회원 별 댓글 리스트
	public List<Comment> searchId(String id);
	
	//내가 댓글 남긴 모든 글 번호(pno) 조회
	public List<Integer> searchMyComment(String id);
	
	//hashtag에 해당하는 모든 글(deleted=1)의 댓글 리스트 가져오기
	public List<Comment> searchCommentList(String hashtag);

	//pno_list에 해당하는 모든 댓글 리스트
	public List<Comment> searchAllCommenetList(List<Integer> pno_list);
}
