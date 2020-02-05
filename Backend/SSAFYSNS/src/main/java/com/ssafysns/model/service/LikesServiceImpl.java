package com.ssafysns.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.CommentException;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.LikesException;
import com.ssafysns.model.dto.Post;
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
	@Override
	public void insert(Likes likes) {
		/**
		 * 1. 중복 체크
		 * 2. (pno에 해당하는 게시글에 cno번째의 댓글이 있는지 확인) 
		 * 	2-1. cno가-1일 때도 확인
		 * 3. 
		 */
		try {
			int cno = likes.getCno();
			int pno = likes.getPno();
			Comment comment = null;
			
			try {
				comment = commentRepository.searchByPnoCno(pno, cno);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommentException("Pno, Cno에 해당하는 댓글을 가져올 수 없습니다.");
			}
			
			if(comment == null) {
				throw new LikesException("해당 게시글번호와 댓글번호에 일치하는 데이터가 존재하지 않습니다.");
			} else {
				likesRepository.save(likes);				
//				likesUpComment(cno);	// + 해당 댓글/게시글 좋아요 개수 증가
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("좋아요를 누를 수 없습니다.");
		}
	}

	// 게시글 좋아요
	public void insert(PostLikes postLikes) {
		/**
		 * 1. 중복 체크
		 */
		try {
			int pno = postLikes.getPost().getPno();
			Post post = null;
			
			try {
				post = postRepository.findByPno(pno);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommentException(pno+"번 글을 가져올 수 없습니다.");
			}
			
			if(post == null) {
				throw new LikesException("해당 게시글번호가 존재하지 않습니다.");
			} else {
				postLikesRepository.save(postLikes);
//				likesUpPost(pno);		// + 해당 게시글 좋아요 개수 증가
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("게시글에 좋아요를 누를 수 없습니다.");
		}
	}
	
	// 댓글 likes 올리기
	private void likesUpComment(int cno) {		
	
	}
	// 댓글 likes 내리기
	private void likesDownComment(int cno) {		
	
	}

	// 게시글 likes 올리기
	private void likesUpPost(int pno) {		

	}
	// 게시글 likes 내리기
	private void likesDownPost(int pno) {		
	
	}
	
	
	// 댓글 좋아요 취소
	@Transactional
	@Override
	public void delete(String id, int pno, int cno) {
		try {
			likesRepository.deleteLikes(id, pno, cno);
//			likesDownComment(cno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("");
		}
	}
	// 게시글 좋아요 취소
	@Transactional
	@Override
	public void delete(String id, int pno) {
		try {
			likesRepository.deleteLikes(id, pno);
//			likesDownPost(pno);
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
			lists = likesRepository.searchPnoById(id);
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
			pno_list = likesRepository.searchPnoById(jwtId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pno_list;
	}

}
