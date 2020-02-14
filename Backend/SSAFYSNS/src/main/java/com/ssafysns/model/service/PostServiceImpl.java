package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.exception.NoticeException;
import com.ssafysns.exception.PostException;
import com.ssafysns.model.dto.Post;
import com.ssafysns.repository.PostRepository;
import com.ssafysns.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public void insert(String id, Post post) {
		try {
			postRepository.save(post);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 저장 중 오류가 발생했습니다.");
		}
	}

	@Override
	@Transactional
	public Boolean delete(int pno) {
		Post post = null;
		
		try {
			post = postRepository.findById(pno).get();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("pno에 해당하는 게시글 정보를 불러올 수 없습니다.");
		}

		try {
			postRepository.updateDeleted(pno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 삭제 중 오류가 발생했습니다.");
		}
		
		return true;
	}

	//게시글 수정버튼	
	@Override
	public Boolean update(Post post) {
		try {
			postRepository.save(post);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 수정 중 오류가 발생했습니다.");
		}
		return true;
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
	
	// pno_list와 page로 조회
	@Override
	public List<Post> search(List<Integer> pno_list, int page) {
		List<Post> post_list = null;
		try {
			post_list = postRepository.findByPnoList(pno_list, page, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post_list;
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
	
	// 최근 한 달 모든 게시글 조회
	@Override
	public List<Post> searchAMonth() {
		try {
			return postRepository.findAMonth();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("최근 한달 간 게시물 목록 검색 중 오류가 발생했습니다.");
		}
	}
	// 최근 한 달 게시글의 pno_list 조회
	@Override
	public List<Integer> searchPnoAMonth() {
		try {
			return postRepository.findPnoAMonth();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("최근 한달 간 게시물 번호 목록 검색 중 오류가 발생했습니다.");
		}
	}

	/**
	 * ID 체크***
	 * 뉴스피드
	 */
	// [뉴스피드] 로그인 한 사용자의 follow하는 모든 pno_list
	@Override
	public List<Integer> followHash(String hashtag) {
		List<Integer> pno_list = null;
		try {
			pno_list = postRepository.followHash(hashtag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pno_list;
	}
	// [뉴스피드] 로그인 한 사용자의 follow하는 모든 post(page)
	@Override
	public List<Post> searchAllFollowList(List<Integer> pno_list, int page) {
		List<Post> posts = null;
		
		try {
			posts = postRepository.findByPnoList(pno_list, page, 20);
		} catch(Exception e) {
			e.printStackTrace();
			throw new PostException("팔로우하는 모든 게시글 조회 중 오류가 발생했습니다.");
		}
		
		return posts;
	}
	
	// [Tab] hashtag를 가지는 모든 pno_list 불러오기
	@Override
	public List<Integer> searchPnoByHash(String hashtag) {
		List<Integer> pno_list = null;		
		try {
			pno_list = postRepository.findPnoByHashtag(hashtag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pno_list;
	}
	
	// [Tab] hashtag를 가지는 모든 post(page)
	@Override
	public List<Post> search(String hashtag, int page) {
		List<Post> posts = null;
		
		try {
			posts = postRepository.findByHashtag(hashtag, page, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return posts;
	}

	// 최근 한 달 간 베스트 게시글 20개
	@Override
	public List<Post> searchBest20() {
		List<Post> posts = null;
		try {
			posts = postRepository.findAMonth();
			posts = posts.subList(0, Integer.min(posts.size(), 20));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}
	
	// 내가 쓴 글 페이지 번호 리스트 가져오기
	@Override
	public List<Integer> searchMyPost(String jwtId) {
		List<Integer> pno_list = null;
		try {
			pno_list = postRepository.searchMyPost(jwtId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pno_list;
	}

	@Override
	public Post isLastPage(String hashtag, int page) {
		Post post = null;
		try {
			post = postRepository.isLastPage(hashtag, page+20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}
	
	@Override
	public Post isLastPage(List<Integer> pno_list, int page) {
		Post post = null;
		try {
			post = postRepository.isLastPage(pno_list, page+20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
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
