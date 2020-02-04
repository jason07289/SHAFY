package com.ssafysns.model.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.CommentException;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.LikesException;
import com.ssafysns.repository.CommentRepository;
import com.ssafysns.repository.LikesRepository;

@Service
public class LikesServiceImpl implements LikesService {
	
	@Autowired
	LikesRepository likesRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	// 좋아요
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
				
				// + 해당 댓글/게시글 좋아요 개수 증가
				likesUp(cno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("좋아요를 누를 수 없습니다.");
		}
	}

	private void likesUp(int cno) {
		
		try {
			commentRepository.likesUp(cno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("댓글 좋아요 수를 증가시킬 수 없습니다.");
		}
		
	}

	private void likesDown(int cno) {
		
		try {
			commentRepository.likesDown(cno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("댓글 좋아요 수를 감소시킬 수 없습니다.");
		}
		
	}

	// 좋아요 취소
	@Transactional
	@Override
	public void delete(String id, int pno, int cno) {
		try {
			likesRepository.deleteLikes(id, pno, cno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("");
		}
	}

	//[뉴스피드] 여러개의 pno리스트에 해당하는 각 게시글 좋아요 기록 가져오기
	@Override
	public List<Likes> searchByAllPno(List<Integer> pno) {
		List<Likes> likes = null;
		try {
			likes = likesRepository.findByAllPno(pno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("게시글 목록의 좋아요를 가져올 수 없습니다.");
		}
		return likes;
	}

	// 해당 게시글의 좋아요 기록 찾기(한 개의 글에 해당하는)
	@Override
	public List<Likes> searchByPno(int pno) {
		List<Likes> likes = null;
		try {
			likes = searchByPno(pno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("게시글의 좋아요를 가져올 수 없습니다.");
		}
		return likes;
	}
	
	// 좋아요 누른 모든 post 번호  찾기(id로 검색)
	@Override
	public List<Integer> searchById(String id) {
		List<Integer> lists = null;
		try {
			lists = likesRepository.searchById(id);
			if(lists == null)
				System.out.println("좋아요를 누른 게시글이 존재하지 않습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new LikesException("좋아요를 누른 게시글을 가져올 수 없습니다.");
		}
		return lists;
	}

	
	@Override
	@Transactional
	public void likeCheck(int cno, String id) {
		try {
//			likesRepository.likeCheck(cno, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Comment> selectCno(int pno) {
		List<Comment> comment_list = new ArrayList<Comment>();
		List<Map<String, Object>> list = null;

		System.out.println("출력 시작");
		try {
			list = likesRepository.selectCno(pno);
			
			Comment comment = null;
			for(int i = 0, size = list.size(); i<size; i++) {
				System.out.println(i+". ------------------------");
				//Comment(cno, pno, parent, id, nickname, datetime, content, delete, like_count, like_check)
				Map<String, Object> now = list.get(i);
				int cno = Integer.parseInt(now.get("cno").toString());
				int ppno = Integer.parseInt(now.get("pno").toString());
//				Integer parent = Integer.parseInt(now.get("parent").toString());
				String id = now.get("id").toString();
//				String nickname = now.get("nickname").toString();
				String from = now.get("datetime").toString();
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = fm.parse(from);
				String content = now.get("content").toString();
				
//				comment = new Comment(cno, ppno, (Integer)cno, id, "nickname", date, content, 0, 0, false, null, null, null);
				comment_list.add(comment);
				comment = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comment_list;
	}

	@Override
	public List<Boolean> likeTest(List<Integer> cno_list) {
		
		List<Boolean> boolean_list = likesRepository.checkLikeTest(cno_list);
		System.out.println(boolean_list.toString());
		
		return boolean_list;
	}


}
