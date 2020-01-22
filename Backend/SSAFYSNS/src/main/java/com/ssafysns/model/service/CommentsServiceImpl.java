package com.ssafysns.model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transaction;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Comments;
import com.ssafysns.repository.CommentsRepository;

@Service
public class CommentsServiceImpl implements CommentsService {
	
	@Autowired
	CommentsRepository commentsRepository;

	@Override
	public void insert(Comments comment) {
		try {
			commentsRepository.save(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Comments comment) {
		try {
			commentsRepository.save(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void delete(int no) {
		
		//deleted = true로 활성화
		try {
			commentsRepository.updateDeleted(no);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Comments search(String id) {
		return null;
	}

	@Override
	public List<Comments> searchComments() {
		List<Comments> comments = null;
		try {
			comments = commentsRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	@Override
	public List<Comments> searchId(String id) {
		List<Comments> comments = null;
		try {
			comments = commentsRepository.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	
}
