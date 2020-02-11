package com.ssafysns.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.LikesException;
import com.ssafysns.model.dto.PostLikes;
import com.ssafysns.repository.CommentRepository;
import com.ssafysns.repository.LikesRepository;
import com.ssafysns.repository.PostLikesRepository;
import com.ssafysns.repository.PostRepository;

@Service
public class LikesServiceImpl implements LikesService {
	
	@Autowired
	LikesRepository likesRepository;
	
	@Autowired
	PostLikesRepository postLikesRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PostRepository postRepository;
	
	// 댓글 좋아요
	@Transactional
	@Override
	public boolean insert(Likes likes) {
		/**
		 * 1. (pno에 해당하는 게시글에 cno번째의 댓글이 있는지 확인) 
		 */
		try {
			try {
				Likes temp = likesRepository.checkLikes(likes.getId(), likes.getCno());
				Comment comment = commentRepository.checkComment(likes.getPno(), likes.getCno());
				if(temp==null) {
					if(comment==null) {
						throw new LikesException();
					} else {
						likesRepository.save(likes);
					}
					return true;
				} else {
					System.out.println("좋아요를 취소합니다.");
					delete(likes);
					return false;
				}			
			} catch (LikesException le) {
				throw new LikesException("좋아요를 누를 수 없습니다.");
			} catch (Exception e) {
				e.printStackTrace();
				throw new LikesException("댓글에 좋아요를 누를 수 없습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("좋아요를 누를 수 없습니다.");
		}
	}

	// 게시글 좋아요
	@Transactional
	@Override
	public boolean insert(PostLikes postLikes) {
		/**
		 * 1. 중복 체크
		 */
		try {
			PostLikes temp = postLikesRepository.checkPostLikes(postLikes.getId(), postLikes.getPno());
			if(temp==null) {
				postLikesRepository.save(postLikes);
				return true;
			} else {
				System.out.println("게시글 좋아요를 취소합니다.");
				delete(postLikes);
				return false;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("게시글에 좋아요를 누를 수 없습니다.");
		}
	}
	
	
	// 댓글 좋아요 취소
	@Transactional
	@Override
	public void delete(Likes likes) {
		try {
			likesRepository.deleteLikes(likes.getId(), likes.getPno(), likes.getCno());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("");
		}
	}
	// 게시글 좋아요 취소
	@Transactional
	@Override
	public void delete(PostLikes postLikes) {
		try {
			postLikesRepository.deleteLikes(postLikes.getId(), postLikes.getPno());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("");
		}
	}


	// 좋아요 누른 모든 post 번호  찾기(id로 검색)
	@Override
	public List<Integer> searchById(String id) {
		List<Integer> lists = null;
		try {
			lists = postLikesRepository.searchPnoById(id);
			if(lists == null)
				System.out.println("좋아요를 누른 게시글이 존재하지 않습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("좋아요를 누른 게시글을 가져올 수 없습니다.");
		}
		return lists;
	}


	@Override
	public List<Boolean> likeBooleanComment(List<Integer> my_like_comment, int pno) {
		List<Boolean> boolean_list = null;
		
		try {
			boolean_list = likesRepository.checkLikeComment(my_like_comment, pno);
			System.out.println(boolean_list.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return boolean_list;
	}

	@Override
	public List<Boolean> likeBooleanPost(List<Integer> my_like_post, List<Integer> follow_list) {
		List<Boolean> boolean_list = null;
		
		try {
			boolean_list = likesRepository.checkLikePost(my_like_post, follow_list);
			System.out.println(boolean_list.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return boolean_list;
	}
	

	//내가 좋아요 누른 댓글 번호 리스트로 가져오기
	@Override
	public List<Integer> selectCnoById(String id) {
		List<Integer> cno_list = null;
		try {
			cno_list = likesRepository.searchCnoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cno_list;
	}
	
	//내가 좋아요 누른 게시글 번호 리스트로 가져오기
	@Override
	public List<Integer> selectPnoById(String jwtId) {
		List<Integer> pno_list = null;
		try {
			pno_list = postLikesRepository.searchPnoById(jwtId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pno_list;
	}

}
