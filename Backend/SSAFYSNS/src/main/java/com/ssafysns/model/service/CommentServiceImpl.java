package com.ssafysns.model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transaction;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	
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
	public void insert(Comment comment) {
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
	public void delete(int no) {
		
		//deleted = true로 활성화
		try {
			commentRepository.updateDeleted(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Comment search(String id) {
		return null;
	}

	@Override
	public List<Comment> searchPno(int no) {
		List<Comment> comments = null;
		try {
			comments = commentRepository.findByPno(no);
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
	
	
}
