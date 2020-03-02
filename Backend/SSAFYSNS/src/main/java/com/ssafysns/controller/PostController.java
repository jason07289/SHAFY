package com.ssafysns.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.exception.UnauthorizedException;
import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Notification;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.PostVote;
import com.ssafysns.model.dto.User;
import com.ssafysns.model.dto.Vote;
import com.ssafysns.model.dto.VoteRecord;
import com.ssafysns.model.service.BookmarkService;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.FollowHashtagService;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.LikesService;
import com.ssafysns.model.service.NotificationService;
import com.ssafysns.model.service.PostService;
import com.ssafysns.model.service.TabHashtagService;
import com.ssafysns.model.service.UserService;
import com.ssafysns.model.service.VoteService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/post")
@EnableAutoConfiguration
public class PostController {
	@Autowired
	PostService postService;

	@Autowired
	CommentService commentService;
	
	@Autowired
	LikesService likesService;
	
	@Autowired
	FollowHashtagService followService;
	
	@Autowired
	BookmarkService bookmarkService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TabHashtagService tabService;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	VoteService voteService;
	
	@Autowired
	JwtService jwtService;
	
	static int limit = 3;
	
	@ApiOperation("게시글, 투표 등록")
	@PostMapping("/vote")
	public ResponseEntity<Map<String, Object>> registerVote(@RequestBody VoteRecord voteRecord) throws Exception {
		String jwtId = jwtService.get("userid");
		
		voteRecord.setId(jwtId);
		voteService.insert(voteRecord);
		
		return handleSuccess("투표 완료");
//		String id = jwtService.get("userid");
//		
//		System.out.println("투표 시작");
//		System.out.println(postVote.toString());
//		
//		Post post = postVote.getPost();
//		Vote vote = postVote.getVote();
//		System.out.println(post.getContent());
//		System.out.println(vote.toString());
//		boolean message = false;
//		
//		if(vote.getTitle() != "") {
//			System.out.println("투표가 있네용");
//			// Post 등록
//			post.setChk(0);
//			message = insertPost(id, post);
//			
//			// Vote 등록
//			Post check = postService.checkForVote(id);
//			if(check == null) {
//				System.out.println("에러ㅠㅠㅠㅠㅠ");
//			} else {
//				vote.setPno(check.getPno());
//			}
//			vote.setId(id);
//			voteService.insert(vote);
//		} else { //투표가 없을 경우
//			System.out.println("투표가 없어용");
//			post.setChk(1);
//			message = insertPost(id, post);
//		}
//		
//		if(!message) {
//			return handleFail("공지사항 권한이 없습니다.", HttpStatus.OK);
//		} else {
//			return handleSuccess("게시글이 등록되었습니다.");
//		}
		
	}
	
	public Boolean insertPost(String id, Post post) throws Exception {
		System.out.println("insertPost start!");
		String auth = jwtService.get("auth");
//		System.out.println("\n\nAUTH\n\n");
//		System.out.println("auth: "+auth);
		
		//관리자가 아닌데 공지사항 히든태그를 사용할 경우 BAD_REQUEST
		System.out.println("공지사항테스트~");
		if((auth==null 
				|| (auth!=null && !auth.equals("관리자")))
				&& post.getHashtag().startsWith("#공지사항")) {
			System.out.println("공지사항 권한이 없습니다.");
			return false;
		} else {
			postService.insert(id, post);
		}
		return true;
	}
	
	/**
	 * 주소 분기
	 * @throws Exception 
	 */
	@ApiOperation(value="/tab/{hashtag}/{page} - 북마크/댓글단글/작성한 글/Tab")
	@GetMapping("/tab/{hashtag}/{page}")
	public ResponseEntity<Map<String, Object>> allocation(@PathVariable String hashtag, @PathVariable int page) throws Exception {
		Map<String, Object> result_map = new HashMap<String, Object>();
		
		if(hashtag.startsWith("...bookmark")) {
			System.out.println("스크랩 게시글");
			result_map = getBookmark(result_map, page);
		} else if(hashtag.startsWith("...comment")) {
			System.out.println("댓글 쓴 게시글");
			result_map = getMyCommentedPost(result_map, page);
		} else if(hashtag.startsWith("...post")) {
			System.out.println("내가 쓴 게시글");
			result_map = getMyPost(result_map, page);
		} else if(hashtag.startsWith("...best")){
			System.out.println("베스트 게시글");
			result_map = getBestPost(result_map, page);
		}else {
			result_map = getPostByOneHashtag(result_map, hashtag, page);
		}
		
		if(result_map.get("post")==null) {
			return handleFail(result_map, HttpStatus.OK);
		} else {
			result_map.put("state", "ok");
		}
		return handleSuccess(result_map);
//		return new ResponseEntity<Map<String, Object>>(result_map, HttpStatus.OK);
	}
	
	/**
	 * [Me]
	 * 스크랩 한 글
	 * @throws UnauthorizedException 
	 */
	public Map<String, Object> getBookmark(Map<String, Object> result_map, int page) throws UnauthorizedException {
		page = Integer.max(0, page*limit);
		String jwtId = jwtService.get("userid");
		
		List<Integer> pno_list = bookmarkService.searchPnoById(jwtId);
		List<Post> post_list = postService.search(pno_list, page);
		
		// 마지막페이지인지 확인
		Post isLast = postService.isLastPage(pno_list, page);

		if(isLast == null) {
			result_map.put("next", false);
		} else {
			result_map.put("next", true);
		}
		post_list = returnPost(post_list, pno_list, page);
		if(post_list.size()==0) {
			result_map.put("post", null);
		} else {
			result_map.put("post", post_list);
		}
		
		return result_map;
	}
	/**
	 * [Me]
	 * 내가 쓴 글
	 * @throws UnauthorizedException 
	 */
	public Map<String, Object> getMyPost(Map<String, Object> result_map, int page) throws UnauthorizedException {
		System.out.println("GET MY POST");
		page = Integer.max(0, page*limit);
		
		String jwtId = jwtService.get("userid");
		
		List<Integer> pno_list = postService.searchMyPost(jwtId);
		List<Post> post_list = postService.search(pno_list, page);
		
		// 마지막페이지인지 확인
		Post isLast = postService.isLastPage(pno_list, page);

		if(isLast == null) {
			result_map.put("next", false);
		} else {
			result_map.put("next", true);
		}
		post_list = returnPost(post_list, pno_list, page);
		
		if(post_list.size()==0) {
			result_map.put("post", null);
		} else {
			result_map.put("post", post_list);
		}
		
		return result_map;
	}
	
	/** 
	 * [Me]
	 * 댓글 남긴 글
	 * @throws UnauthorizedException 
	 */
	public Map<String, Object> getMyCommentedPost(Map<String, Object> result_map, int page) throws UnauthorizedException {
		page = Integer.max(0, page*limit);
		String jwtId = jwtService.get("userid");
		
		List<Integer> pno_list = commentService.searchMyComment(jwtId);
		List<Post> post_list = postService.search(pno_list, page);
		
		// 마지막페이지인지 확인
		Post isLast = postService.isLastPage(pno_list, page);

		if(isLast == null) {
			result_map.put("next", false);
		} else {
			result_map.put("next", true);
		}
		post_list = returnPost(post_list, pno_list, page);
		if(post_list.size()==0) {
			result_map.put("post", null);
		} else {
			result_map.put("post", post_list);
		}
		
		return result_map;
	}
	

	/**
	 * 베스트 게시글
	 */
	public Map<String, Object> getBestPost(Map<String, Object> result_map, int page) throws Exception {
		// 해시태그에 해당하는 게시글 리스트 가져오기
		List<Post> post_list = postService.searchAMonth();
		List<Integer> pno_list = postService.searchPnoAMonth();
		
		for(int i = 0, size = post_list.size(); i<size; i++) {
			Post post = post_list.get(i);
			post.setLike_count(post.getPostlike().size());
		}
		post_list = returnPost(post_list, pno_list, page);
		
		// 리스트 정렬
		Collections.sort(post_list, new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return o1.getLike_count()-o2.getLike_count();
			}
		});
		
		post_list = post_list.subList(0, Integer.min(20, post_list.size()));
		
		result_map.put("next", false);
		if(post_list.size()==0) {
			result_map.put("post", null);
		} else {
			result_map.put("post", post_list);
		}
		
		return result_map;
	}

	/**
	 * [탭]
	 * - 한 개의 hashtag에 해당하는 모든 Post, Comment, Likes 조회
	 */	
	public Map<String, Object> getPostByOneHashtag(Map<String, Object> result_map, String hashtag, int page) throws Exception {
		/*
		 * hashtag 탭 해시태그는 Frontend에서 보내줘야 함.
		 */
		page = Integer.max(0, page*limit);
		hashtag = "#"+hashtag+"#";
		
		List<Post> post_list = searchPostByHash(hashtag, page);	//개수 제한
		List<Integer> pno_list = postService.searchPnoByHash(hashtag);	//개수 제한
		
		// 마지막페이지인지 확인
		Post isLast = postService.isLastPage(hashtag, page);

		if(isLast == null) {
			result_map.put("next", false);
		} else {
			result_map.put("next", true);
		}
		post_list = returnPost(post_list, pno_list, page);
		if(post_list.size()==0) {
			result_map.put("post", null);
		} else {
			result_map.put("post", post_list);
		}
		
		return result_map;
	}
	
	/**
	 * [뉴스피드]
	 * - 여러개의 Hashtag에 해당하는 Post와 그에 해당하는 Comment, Likes 조회
	 */
	@ApiOperation(value="[Newsfeed] 여러개의 Hashtag에 해당하는 게시글 리스트와 게시글+댓글+좋아요 정보 반환")
	@GetMapping("/newsfeed/{page}")
	public ResponseEntity<Map<String, Object>> searchHashtagComment(@PathVariable int page) throws Exception {
		/**
		 * JWT 토큰으로 받아오기
		 */
		// Follow Hashtag를 가지고 있는 모든 게시글 리스트 가져오기
		page = Integer.max(0, page*limit);
		
		String jwtId = jwtService.get("userid");
		
		String follow_tag = followService.searchById(jwtId).get().getHashtag();
		System.out.println("\n팔로우 해시태그: "+follow_tag);
		if(follow_tag == null || follow_tag.equals("")) {
			follow_tag = "#공지사항";
		} else {
			follow_tag = follow_tag.replace("#", "#|#");
			follow_tag = follow_tag.substring(2)+"#|#공지사항";
		}
		
//		System.out.println("follow_tag: "+follow_tag);
		System.out.println("\n리턴 포스트 시작\n");
		List<Integer> pno_list = postService.followHash(follow_tag);
		List<Post> post_list = postService.search(pno_list, page);
		/**
		 * 여기 바꾸기
		 */
		post_list = returnPost(post_list, pno_list, page);
		
		System.out.println("\n리턴 포스트 완료\n");
		
		Map<String, Object> result_map = new HashMap<String, Object>();
		
		if(post_list == null || post_list.size()==0) {
			return handleFail(result_map, HttpStatus.OK);
		} else {
			result_map.put("post", post_list);
		}
		
		// 마지막페이지인지 확인
		Post isLast = postService.isLastPage(pno_list, page);

		if(isLast == null) {
			result_map.put("next", false);
		} else {
			result_map.put("next", true);
		}
		
		return handleSuccess(result_map);
		
	}
	
	/**
	 * [공통 코드]
	 * - Comment, Likes 처리
	 * @throws UnauthorizedException 
	 */
	public List<Post> returnPost(List<Post> posts, List<Integer> pno_list, int page) throws UnauthorizedException{
		String jwtId = jwtService.get("userid");
		String jwtAuth = jwtService.get("auth");
		
		List<Post> post_list = posts;
		
		// 내가 좋아요 누른 모든 게시글 번호 + 현재 게시글 리스트 중 내가 좋아요 눌렀는지 여부
		List<Integer> my_like_post = likesService.selectPnoById(jwtId);
		List<Boolean> like_boolean_list = likesService.likeBooleanPost(my_like_post, pno_list);

		// 내가 북마크 한 모든 북마크 번호 + 북마크 눌렀는지 여부
		List<Integer> my_bookmark_post = bookmarkService.searchPnoById(jwtId);
		List<Boolean> bookmark_boolean_list = likesService.likeBooleanPost(my_bookmark_post, pno_list);
		
		// 내가 누른 모든 [댓글] 좋아요 리스트
		List<Integer> my_like_comment = likesService.selectCnoById(jwtId);
		
		
		System.out.println();
		System.out.println("like_boolean_list.size() == "+ like_boolean_list.size());
		System.out.println("bookmark_boolean_list.size() == "+ bookmark_boolean_list.size());
		System.out.println();
		
		for(int i = 0, post_size = post_list.size(); i<post_size; i++) {
			Post post = post_list.get(i);
			boolean like_chk = like_boolean_list.get(page+i);
			boolean bookmark_chk = bookmark_boolean_list.get(page+i);
			funcPost(jwtAuth, jwtId, post, like_chk, bookmark_chk, my_like_comment);
		}
		return post_list;
	}
	
	private void funcPost(String jwtAuth, String jwtId, Post post, boolean like_chk, boolean bookmark_chk, List<Integer> my_like_comment) {
		
		// Like_Count, Like_Check 설정
		if(like_chk) {
			post.setLike_check(true);
		}
		post.setLike_count(post.getPostlike().size());
		post.setPostlike(null);
		
		// 북마크 체크 여부 설정
		if(bookmark_chk) {
			post.setBookmark_check(true);
		}
		
		// 투표 체크 여부
		Vote vote = null;
		if(post.getVote()!=null) { //투표가 있을 경우
			System.out.println("투표오오오");
			vote = post.getVote();
			int vno = vote.getVno();
			VoteRecord voteRecord = voteService.isVoted(jwtId, vno);
			int a_value = voteService.getAValue(vno);
			int b_value = voteService.getBValue(vno);
		
			if(voteRecord == null) {
				vote.setMy_value("No");
			} else if(voteRecord.getChoice()==1) {
				vote.setMy_value("A");
			} else {
				vote.setMy_value("B");
			}
			vote.setA_value(a_value);
			vote.setB_value(b_value);
			vote.setPost(null);
			vote.setUser(null);
		}
		
		/**
		 *  게시글 익명 처리
		 */
		System.out.println("게시글 익명처리중");
		if(post.getId().equals(jwtId) || (jwtAuth!=null && jwtAuth.equals("관리자"))) {
			post.setAuth(true);
		}
		if(post.getAnonymous() == 1) {
			post.setNickname("익명");
			post.setProfile(null);
		} else {
			post.setProfile(post.getUser().getImg());
			post.setNickname(post.getUser().getNickname());
		}
		post.setId(null);			// 나중에 개인 식별 코드 컬럼으로 대체
		post.setUser(null);
		
		// 해당 게시글(pno)에 달린 댓글 리스트
		List<Comment> temp_comments_list = post.getComment();
		if(temp_comments_list == null) {
			System.out.println("댓글이 없네요");
		} else {
			System.out.println("댓글이 있네요");
		}
		// 내가 좋아요 누른 댓글 리스트
		List<Boolean> like_boolean_list = likesService.likeBooleanComment(my_like_comment, post.getPno());
		
		temp_comments_list = funcComment(jwtAuth, jwtId, temp_comments_list, like_boolean_list);
		
	}
	
	
	/**
	 * [공통 코드] 댓글 관리
	 */
	private List<Comment> funcComment(String jwtAuth, String jwtId, List<Comment> temp_comments_list, List<Boolean> like_boolean_list) {
		System.out.println("FuncComment");
		if(temp_comments_list != null) {
			for(int j = 0, comm_size = temp_comments_list.size(); j<comm_size; j++) {
				Comment temp_comment = temp_comments_list.get(j);
				
				// StackOverflow 방지
				temp_comment.setLike_count(temp_comment.getLike().size());
				temp_comment.setLike(null); 
				if(like_boolean_list != null && like_boolean_list.get(j)) {	//내가 좋아요를 누른 댓글일 경우
					temp_comment.setLike_check(true);
				}
				
				/**
				 *  댓글 익명 처리
				 */
				if(temp_comment.getId().equals(jwtId) || (jwtAuth!=null && jwtAuth.equals("관리자"))) {
					temp_comment.setAuth(true);
				}
				if(temp_comment.getAnonymous() == 1) {
					temp_comment.setNickname("익명");
				} else {
					temp_comment.setNickname(temp_comment.getUser().getNickname());
				}
				temp_comment.setId(null);			// 나중에 개인 식별 코드 컬럼으로 대체
//				System.out.println(temp_comment.getNickname());
				temp_comment.setUser(null);
				temp_comment.setPost(null);
				
				if(temp_comment.getParent()==0) {
					temp_comment.setParent(temp_comment.getCno());
				}
			}
		}
		Collections.sort(temp_comments_list, new Comparator<Comment>() {
			@Override
			public int compare(Comment o1, Comment o2) {
				if(o1.getParent()==o2.getParent()) {
					return o1.getCno()-o2.getCno();
				} else {
					return o1.getParent()-o2.getParent();
				}
			}
		});
		return temp_comments_list;
	}
	
	
	
	
	/**
	 * [Me]
	 * - 내가 좋아요 누른 글 목록 가져오기
	 */
	@ApiOperation(value="내가 좋아요 누른 게시글 번호 가져오기")
	@PostMapping("/me/{page}")
	public ResponseEntity<List<Post>> searchPostLikesById(@PathVariable int page) throws Exception {
		
		page = Integer.max(0, page*limit);
		
		String jwtId = jwtService.get("userid");		
		List<Integer> pno_list = likesService.selectPnoById(jwtId); //좋아요 누른 글 리스트 리스트 반환
		List<Post> post_list = postService.searchAllFollowList(pno_list, page);	//개수 제한
		
		returnPost(post_list, pno_list, page);
		
		return new ResponseEntity<List<Post>>(post_list, HttpStatus.OK);
	}
	
	/**
	 * Post CRUD
	 */
	// DB테이블에 있는 모든 Post 조회
	@ApiOperation(value = "(DB테이블에 있는)모든 Post 목록 조회")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		List<Post> posts = postService.searchAll();
		
		String jwtId = jwtService.get("userid");
		
		for(int i = 0, size = posts.size(); i<size; i++) {
			Post post = posts.get(i);
			post.setUser(null);
			post.setPostlike(null);
			
			List<Comment> comment_list = post.getComment();
			
			for(int j = 0, jsize = comment_list.size(); j<jsize; j++) {
				Comment comment = post.getComment().get(j);
				comment.setLike(null);
				
				if(comment.getParent()==0) {
					comment.setParent(comment.getCno());
				}
				
				/**
				 *  댓글 익명 처리
				 */
				if(comment.getId().equals(jwtId)) {
					comment.setAuth(true);
				}
				if(comment.getAnonymous() == 1) {
					comment.setNickname("익명");
				} else {
					comment.setNickname(comment.getUser().getNickname());
				}
				comment.setId(null);			// 나중에 개인 식별 코드 컬럼으로 대체
				comment.setUser(null);
			}
			
			Collections.sort(comment_list, new Comparator<Comment>() {
				@Override
				public int compare(Comment o1, Comment o2) {
					if(o1.getParent()==o2.getParent()) {
						return o1.getCno()-o2.getCno();
					} else {
						return o1.getParent()-o2.getParent();
					}
				}
			});
		}
		return handleSuccess(posts);
	}
	
	// Post 등록
	@ApiOperation(value = "Post 등록")
	@PostMapping
	public ResponseEntity<Map<String, Object>> insert(@RequestBody PostVote postVote) throws Exception {
		
		String id = jwtService.get("userid");
		
		System.out.println("투표 시작");
		System.out.println(postVote.toString());
		
		Post post = postVote.getPost();
		post.setId(id);
		Vote vote = postVote.getVote();
		System.out.println(post.getContent());
//		System.out.println(vote.toString());
		boolean message = false;
		
		if(vote!=null && vote.getTitle() != "") {
			System.out.println("투표가 있네용");
			// Post 등록
			post.setChk(0);
			message = insertPost(id, post);
			String hashtag = post.getHashtag();
			if(hashtag == null || !hashtag.contains("#투표")) {
				hashtag = "#투표"+hashtag;
			}
			
			// Vote 등록
			Post check = postService.checkForVote(id);
			if(check == null) {
				System.out.println("에러ㅠㅠㅠㅠㅠ");
			} else {
				vote.setPno(check.getPno());
			}
			vote.setId(id);
			voteService.insert(vote);
		} else { //투표가 없을 경우
			System.out.println("투표가 없어용");
			post.setChk(1);
			System.out.println("체크");
			message = insertPost(id, post);
		}
		
//		String auth = jwtService.get("auth");
//		System.out.println("auth: "+auth);
		
//		//관리자가 아닌데 공지사항 히든태그를 사용할 경우 BAD_REQUEST
//		if((auth==null 
//				|| (auth!=null && !auth.equals("관리자")))
//				&& post.getHashtag().startsWith("#공지사항")) {
//			System.out.println("공지사항 권한이 없습니다.");
//			return handleFail("fail", HttpStatus.BAD_REQUEST);
//		} else {
//			postService.insert(id, post);
//		}
		
		if(!message) {
			return handleFail("공지사항 권한이 없습니다.", HttpStatus.OK);
		} else {
			return handleSuccess("게시글이 등록되었습니다.");
		}
//		String id = post.getId();
//
//		// 닉네임 설정
//		User user = userService.MyInfo();
//		String nickname = user.getNickname();
//		post.setNickname(nickname);
//		
//		/**
//		 * post.getId()와  Id가 다를 때 비교 해야 하나?
//		 */
//		//관리자가 아닌데 공지사항 히든태그를 사용할 경우 BAD_REQUEST
//		if((user.getAuth()==null 
//				|| (user.getAuth()!=null && !user.getAuth().equals("관리자")))
//				&& post.getHashtag().startsWith("공지사항")) {
//			System.out.println("공지사항 권한이 없습니다.");
//			return handleFail("fail", HttpStatus.BAD_REQUEST);
//		} else {
//			postService.insert(id, post);
//		}
//		return handleSuccess("Post 등록 완료");
	}
	
	// Post 삭제(pno)
	@ApiOperation(value = "pno에 해당하는 Post 삭제")
	@DeleteMapping("/{pno}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int pno) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String jwtId = jwtService.get("userid");
		Post post = postService.search(pno).get();
		
		if(post.getId().equals(jwtId)) {
			postService.delete(pno);
			return handleSuccess("Post 삭제 완료");
		} else {
			return handleFail("게시글 삭제 권한이 없습니다.", HttpStatus.OK);
		}
	}
	
	// Post 수정
	@ApiOperation(value = "Post 수정")
	@PutMapping()
	public ResponseEntity<Map<String, Object>> update(@RequestBody Post post) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String jwtId = jwtService.get("userid");
		
		if(post.getId().equals(jwtId)) {
			postService.update(post);
			return handleSuccess("게시글 수정 완료");
		} else {
			/**
			 * 여기서 return을 뭐를 해야 할까?
			 */
			return handleFail("게시글 수정 권한이 없습니다.", HttpStatus.OK);
//			return handleFail(new HashMap<String, String>().put("message", "수정권한이 없습니다."), HttpStatus.BAD_REQUEST);
		}

	}
	
	/**
	 * Pno로 게시글+댓글+좋아요 정보 조회
	 */
	@ApiOperation(value="{pno}에 해당하는 게시글, 댓글, 좋아요 조회")
	@GetMapping("/{pno}")
	public ResponseEntity<Map<String, Object>> searchByPno(@PathVariable int pno) throws Exception {
		
		Post post = search(pno);
		List<Integer> pno_list = new ArrayList<Integer>();
		pno_list.add(pno);
		
//		System.out.println("pno: "+pno+", size: "+pno_list.size());
		
		String jwtId = jwtService.get("userid");
		String jwtAuth = jwtService.get("auth");
		// 내가 좋아요 누른 모든 번호 + 좋아요 눌렀는지 여부
		List<Integer> my_like_post = likesService.selectPnoById(jwtId);
		List<Boolean> like_boolean_post = likesService.likeBooleanPost(my_like_post, pno_list);
		List<Integer> my_like_comment = likesService.selectCnoById(jwtId); 
		// 내가 북마크 한 모든 북마크 번호 + 북마크 눌렀는지 여부
		List<Integer> my_bookmark_post = bookmarkService.searchPnoById(jwtId);
		List<Boolean> bookmark_boolean_list = likesService.likeBooleanPost(my_bookmark_post, pno_list);
				
		funcPost(jwtAuth, jwtId, post, like_boolean_post.get(0), bookmark_boolean_list.get(0), my_like_comment);

		Map<String, Object> return_map = new HashMap<String, Object>();
		if(post == null) {
			return_map.put("state", "fail");
		} else {
			return_map.put("state", "ok");
			return_map.put("post", post);
		}
		
		return handleSuccess(return_map);
//		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	// pno에 해당하는 Post 조회 (함수)
	@ApiOperation(value = "pno에 해당하는 Post 조회")
	public Post search(int pno) throws Exception {
		return postService.search(pno).get();
	}

	@ApiOperation(value="{pno}에 해당하는 댓글, 좋아요 조회")
	@GetMapping("/comment/{pno}")
	// pno에 해당하는 CommentList 조회
	public ResponseEntity<List<Comment>> searchComments(@PathVariable int pno) throws Exception {
		List<Comment> comments = commentService.searchPno(pno);
		
		String jwtId = jwtService.get("userid");
		String jwtAuth = jwtService.get("auth");
		
		// 내가 누른 모든 [댓글] 좋아요 리스트
		List<Integer> my_like_comment = likesService.selectCnoById(jwtId);
		// 내가 좋아요 누른 댓글 리스트
		List<Boolean> like_boolean_list = likesService.likeBooleanComment(my_like_comment, pno);
		
		comments = funcComment(jwtAuth, jwtId, comments, like_boolean_list);
				
		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
	}
	
	
	// 해시태그에 해당하는 Post 리스트 조회 (함수)
	public List<Post> searchPostByHash(String hashtag, int page) throws Exception {
		List<Post> posts = postService.search(hashtag, page);
		return posts;
	}
	
		
	/**
	 * Comment
	 */
	// Comment 작성
	@ApiOperation(value="Comment 작성")
	@PostMapping("/comment")
	public ResponseEntity<Map<String, Object>> commentInsert(@RequestBody Comment comment) throws Exception {
		String jwtId = jwtService.get("userid");
		
		comment.setDatetime(new Date());
		comment.setId(jwtId);
		commentService.insert(jwtId, comment);

		String id = null;
		
		// 댓글 알림 처리
		Notification notification = null;
		if(comment.getParent() == 0) { //댓글일 경우 - 게시글 작성자에게 전송
			id = postService.search(comment.getPno()).get().getId();
			notification =  new Notification(id, new Date(), comment.getPno(), 2, 0, comment.getContent());
		} else { //대댓글일 경우 - 댓글 작성자에게 전송
			id = commentService.search(comment.getCno()).getId();
			notification =  new Notification(id, new Date(), comment.getPno(), 4, 0, comment.getContent());
		}
		
		notificationService.insert(notification);
		
		return handleSuccess("댓글 등록 완료");
	}
	
	// Comment 수정
	@ApiOperation(value="Comment update")
	@PutMapping("/comment")
	public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Comment comment) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String jwtId = jwtService.get("userid");
		
		if(comment.getId() == jwtId) {
			commentService.update(comment);
			return handleSuccess("댓글 수정 완료");
		} else {
			return handleSuccess("댓글 삭제 권한이 없습니다.");
		}
	}
	
	// Comment 삭제
	@ApiOperation(value="no로 Comments 삭제")
	@DeleteMapping("/comment/{no}")
	public ResponseEntity<Map<String, Object>> DeleteComment(@PathVariable int no) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String jwtId = jwtService.get("userid");
		Boolean msg = commentService.delete(jwtId, no);
		
		if(msg == true) {
			return handleSuccess("댓글 삭제 완료");
		} else {
			return handleSuccess("댓글 삭제 권한이 없습니다.");
		}
	}
	
	// [함수] - 하나의 게시글(pno)에 해당하는 댓글 리스트
	public List<Comment> OnePostComment(int pno) throws Exception {
		List<Comment> comments = commentService.searchPno(pno);
		return comments;
	}	
	
	// [함수] - 여러개의 게시글(pno 리스트)에 해당하는 댓글 리스트
	public List<Comment> AllPostComment(List<Integer> pnolist) throws Exception {
		List<Comment> comments = commentService.searchAllCommenetList(pnolist);
		return comments;
	}
	
//	@ApiOperation("cno로 comment 조회")
//	@GetMapping("/comment/one/{cno}")
//	public ResponseEntity<Comment> SearchByCno(@PathVariable int cno) throws Exception {
//		System.out.println("댓글 조회 시작");
//		Comment comment = commentService.search(cno);
//		System.out.println("댓글 조회 완료");
//		System.out.println(comment.getUser().getNickname());
//		return new ResponseEntity<Comment>(comment, HttpStatus.OK);
//	}
	
	/**
	 * ExceptionHandler
	 */
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e) {
		return handleFail(e.getMessage(), HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	} 

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
