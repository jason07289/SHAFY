package com.ssafysns.model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transaction;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Post;
import com.ssafysns.repository.CommentRepository;
import com.ssafysns.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void insert(String id, Comment comment) {
		try {
			commentRepository.save(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Comment comment) {
		try {
			commentRepository.save(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public boolean delete(String jwtId, int no) {
		
		String id = commentRepository.findById(no).get().getId();
		if(id.equals(jwtId)) {
			try {
				commentRepository.updateDeleted(no);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("댓글 삭제 완료");
			return true;
		} else {
			System.out.println("댓글 삭제 실패");
			return false;		
		}
	}
	

	//cno로 하나의 댓글 조회
	@Override
	public Comment search(int cno) {
		 Comment comment = null;
		 try {
			System.out.println("cno is: "+cno);
			comment = commentRepository.findByCno(cno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comment;
	}

	//pno로 모든 Comment(댓글) 리스트 조회
	@Override
	public List<Comment> searchPno(int pno) {
		List<Comment> comments = null;
		try {
			comments = commentRepository.findByPno(pno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	//pno로 모든 cno(댓글번호) 리스트 조회
	@Override
	public List<Integer> searchCnoByPno(int pno) {
		List<Integer> cno_list = null;
		try {
			cno_list = commentRepository.searchCnoByPno(pno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cno_list;
	}

	//회원 별 댓글 리스트
	@Override
	public List<Comment> searchId(String id) {
		List<Comment> comments = null;
		try {
			comments = commentRepository.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}	

	//내가 댓글 남긴 모든 글 번호(pno) 조회
	@Override
	public List<Integer> searchMyComment(String id) {
		List<Integer> pno_list = null;
		try {
			pno_list = commentRepository.searchMyComment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pno_list;
	}
	
	//해시태그에 해당하는 모든 글(deleted=1)의 댓글 리스트 가져오기
	@Override
	public List<Comment> searchCommentList(String hashtag) {
		List<Comment> comments = null;
		try {
			comments = commentRepository.joinCustomComment(hashtag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	//pno_list에 해당하는 모든 댓글 리스트 출력
	@Override
	public List<Comment> searchAllCommenetList(List<Integer> pno_list) {
		List<Comment> comments = null;
		
		try {
			comments = commentRepository.customAllPno(pno_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return comments;
	}
	
}
