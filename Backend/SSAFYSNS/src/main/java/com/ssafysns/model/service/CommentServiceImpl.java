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
	@Transactional
	public List<Comment> joinPost(String hashtag) {
		List<Comment> comments = null;
		try {
			System.out.println("In Service...hashtag is.." + hashtag);
			comments = commentRepository.joinCustom(hashtag);
			if(comments.size()==0)
				System.out.println(hashtag + "번 게시글에 댓글이 존재하지 않습니다.");
			else
				System.out.println(comments.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	@Override
	public List<Comment> searchCommentList(String hashtag) {
		List<Comment> comments = null;
		try {
			System.out.println("In Service... hashtag is.." + hashtag);
			comments = commentRepository.joinCustomComment(hashtag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	@Override
	public void insert(String id, Comment comment) {
		
//		String nickname = userRepository.findById(id).get().getNickname();
//		comment.setNickname(nickname);
		
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
		System.out.println("jwtId: "+jwtId+", id: "+id);
		if(id.equals(jwtId)) {
			try {
				System.out.println("댓글 삭제중...");
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
	
	
}
