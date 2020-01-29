package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.NoticeException;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.PostException;
import com.ssafysns.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public void insert(Post post) {
		try {
			postRepository.save(post);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 저장 중 오류가 발생했습니다.");
		}
	}

	@Override
	@Transactional
	public void delete(int pno) {
		try {
			postRepository.updateDeleted(pno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void update(Post post) {
		try {
			postRepository.save(post);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 수정 중 오류가 발생했습니다.");
		}
	}

	// 모든 Post 조회
	@Override
	public List<Post> searchAll() {
		try {
			return postRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 목록 검색 중 오류가 발생했습니다.");
		}
	}

	// pno로 Post 조회
	@Override
	public Optional<Post> search(int pno) {
		try {
			return postRepository.findById(pno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 검색 중 오류가 발생했습니다.");
		}
	}
	
	
	@Override
	public int count() {
		try {
			return (int) postRepository.count();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 개수 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<Post> searchAllHashTags() {
		
		return null;
	}

	// Hashtag로 Post 조회
	@Override
	public List<Post> search(String hashtag) {
		List<Post> posts = null;
		
		try {
			posts = postRepository.findByHashtag(hashtag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return posts;
	}
	
	@Override
	public List<Integer> searchPostNo(String hashtag) {
		System.out.println("==============Pno List 출력==============");
		List<Integer> pno_list = null;		
		try {
			pno_list = postRepository.findPnoByHashtag(hashtag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pno_list;
	}

	/**
	 * ID 체크***
	 */
	@Override
	public List<Integer> testAllHash(String id) {
		List<Integer> all_hash_pno_list = null;
		try {
			all_hash_pno_list  = postRepository.testAllHash(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all_hash_pno_list;
	}

}
