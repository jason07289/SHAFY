package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void delete(int pno) {
		try {
			Optional<Post> post = postRepository.findById(pno);
			if (post != null) {
				// deleted 컬럼의 값을 바꿔준다!
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void update(Post post) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 수정 중 오류가 발생했습니다.");
		}
	}

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
	public List<Post> searchAll() {
		try {
			return postRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 목록 검색 중 오류가 발생했습니다.");
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

}
