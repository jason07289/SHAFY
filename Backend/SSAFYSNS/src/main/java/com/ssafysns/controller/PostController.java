package com.ssafysns.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.FollowHashtag;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.FollowHashtagService;
import com.ssafysns.model.service.PostService;
import com.ssafysns.repository.CommentRepository;

import io.swagger.annotations.ApiOperation;
import javassist.compiler.ast.Pair;

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
	FollowHashtagService followService;
	
	/**
	 * Post CRUD
	 */
	// 모든 Post 조회
	@ApiOperation(value = "모든 Post 목록 조회")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		return handleSuccess(postService.searchAll());
	}
	
	// Post 등록
	@ApiOperation(value = "Post 등록")
	@PostMapping
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Post post) throws Exception {
		String id = post.getId();
		String hashtag = post.getHashtag();
		
		//관리자가 아닌데 공지사항 히든태그를 사용할 경우 BAD_REQUEST
		if(!id.equals("admin") && hashtag.startsWith("__공지사항__")) {
			return handleFail("fail", HttpStatus.BAD_REQUEST);
		} else {
			postService.insert(post);
		}
		return handleSuccess("Post 등록 완료");
	}
	
	// Post 삭제(pno)
	@ApiOperation(value = "pno에 해당하는 Post 삭제")
	@DeleteMapping("/{pno}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int pno) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String jwtID = "";
		postService.delete(jwtID, pno);
		return handleSuccess("Post 삭제 완료");
	}
	
	// Post 수정
	@ApiOperation(value = "Post 수정")
	@PutMapping("")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Post post) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String jwtID = "";
		postService.update(jwtID, post);
		return handleSuccess("Post 수정 완료");
	}
	
	/**
	 * Pno에 해당하는 모든 Post 조회
	 */
	@ApiOperation(value="{pno}에 해당하는 Post와 댓글 조회")
	@GetMapping("/post/{pno}")
	public ResponseEntity<Map<String, Object>> searchByPno(@PathVariable int pno) throws Exception {
		Map<String, Object> pnoMap = new HashMap<String, Object>();
		
		Post post = search(pno);
		if(post != null) {
			List<Comment> comments = searchComments(pno);
			pnoMap.put("post", post);
			pnoMap.put("comment", comments);
		}
		
		return new ResponseEntity<Map<String, Object>>(pnoMap, HttpStatus.OK);
	}
	
	// pno에 해당하는 Post 조회 (함수)
	@ApiOperation(value = "pno에 해당하는 Post 조회")
	public Post search(int pno) throws Exception {
		return postService.search(pno).get();
	}

	// pno에 해당하는 CommentList 조회 (함수)
	public List<Comment> searchComments(int pno) throws Exception {
		List<Comment> comments = commentService.searchPno(pno);
		return comments;
	}
	
	/**
	 * 한 개의 hashtag에 해당하는 모든 Post 조회
	 */
	@ApiOperation(value="하나의 hashtag에 해당하는 Post & Comment 목록 조회")
	@GetMapping("/{hashtag}/one")
	public ResponseEntity<Map<String, Object>> searchByHashtag(@PathVariable String hashtag) throws Exception {
		List<Post> posts = searchPostByHash(hashtag);
		List<Comment> comments = searchCommentByHash(hashtag);
		
		Map<String, Object> aHashMap = new HashMap<String, Object>();
		aHashMap.put("post", posts);
		aHashMap.put("comment", comments);
		
		return handleSuccess(aHashMap);
	}
	
	// 해시태그에 해당하는 Post 리스트 조회 (함수)
	public List<Post> searchPostByHash(String hashtag) throws Exception {
		List<Post> posts = postService.search(hashtag);
		return posts;
	}
	
	// 해시태그에 해당하는 Comment 리스트 조회(함수)
	@ApiOperation(value = "hashtag에 해당하는 Comment 조회")
	public List<Comment> searchCommentByHash(String hashtag) throws Exception {
		
		List<Comment> comments = commentService.joinPost(hashtag);

		//return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		return comments;
	}
	
	/**
	 * 여러개의 Hashtag에 해당하는 Post와 그에 해당하는 Comment 조회
	 */
	@ApiOperation(value="여러개의 Hashtag에 해당하는 Post와 Comment")
	@GetMapping("/hashtag/all")
	public ResponseEntity<List<Comment>> searchHashtagComment() throws Exception {
		/**
		 * JWT 토큰으로 받아오기
		 */
		String id = "kimssafy"; 
		List<Integer> pno_list_by_all_hash = postService.testAllHash(id);
		List<Comment> comments_by_all_hash = commentService.searchAllCommenetList(pno_list_by_all_hash);
		
//		List<Comment> comments = commentService.searchCommentList(hashtag);
//		System.out.println(comments.toString());
		return new ResponseEntity<List<Comment>>(comments_by_all_hash, HttpStatus.OK);
	}
	
	
	
	
	/**
	 * Comment
	 */
	// Comment 작성
	@ApiOperation(value="Comment 작성")
	@PostMapping("/comment/new")
	public ResponseEntity<Map<String, Object>> commentInsert(@RequestBody Comment comment) throws Exception {
		commentService.insert(comment);
		return handleSuccess("댓글 등록 완료");
	}
	
	// Comment 수정
	@ApiOperation(value="Comment update")
	@PostMapping("/comment/update")
	public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Comment comment) throws Exception {
		/**
		 * 작성자만 가능
		 */		
		commentService.update(comment);
		return handleSuccess("댓글 수정 완료");
	}
	
	// Comment 삭제
	@ApiOperation(value="no로 Comments 삭제")
	@GetMapping("/comment/delete/{no}")
	public ResponseEntity<Map<String, Object>> DeleteComment(@PathVariable int no) throws Exception {
		/**
		 * 작성자만 가능
		 */		
		commentService.delete(no);
		return handleSuccess("댓글 삭제 완료");
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
	
//	/**
//	 * 여러 Hashtag에 해당하는 Post와 그에 해당하는 Comment 조회
//	 */
//	@ApiOperation(value="여러 Hashtag에 해당하는 Post와 그에 해당하는 Comment 조회")
//	@PostMapping("/newsfeed")
//	public ResponseEntity<List<Comment>> searchAllHashtagComment(@PathVariable String hashtag) throws Exception {
//		/**
//		 * 원래는 hashtags를 PathVariable로 받는게 아니고 id를 이용해서 조회해야 한다.
//		 */
//		List<Integer> list = postService.searchPostNo(hashtag);
//		
//		List<Comment> comments = commentService.searchAllCommenetList(list);
//		System.out.println("모든 해시태그 받는 리스트");
//		
//		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
//	}
	
	
	//수정 - no넣어주셈
	@ApiOperation(value="", response=List.class)
	@PostMapping("/comment/list/{no}")
	public ResponseEntity<List<Comment>> findAllComments() throws Exception {
		return new ResponseEntity<List<Comment>>(commentService.searchPno(1), HttpStatus.OK);
	}
	
//	@ApiOperation(value="id로 Comment 검색", response=Comment.class)
//	@GetMapping("/comment/list/{id}")
//	public ResponseEntity<List<Comment>> findById(@PathVariable String id) throws Exception {
//		return new ResponseEntity<List<Comment>> (commentService.searchId(id), HttpStatus.OK);
//	}
	
	
//	public ResponseEntity<List<Pair>> searchAll() throws Exception {
////	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
//		List<Post> post_list = postService.searchAll();
//		System.out.println("post_list.size() = "+ post_list.size());
//		List<Comment> comment_list = commentService.searchPno(1);
//		System.out.println("comment_list.size() = "+ comment_list.size());
//		
//		List<Pair> lists = new ArrayList<Pair>();
//		lists.add(new Pair(post_list.get(0), comment_list));
//		
//		System.out.println("==========================================");
//		System.out.println(lists.get(0).post.toString());
//		System.out.println(lists.get(0).comments.get(0).getContent());
//		System.out.println(lists.get(0).comments.get(1).getContent());
//		System.out.println(lists.get(0).comments.get(2).getContent());
//		return new ResponseEntity<List<Pair>>(lists, HttpStatus.OK);
////		return handleSuccess(postService.searchAll());
	
	

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
