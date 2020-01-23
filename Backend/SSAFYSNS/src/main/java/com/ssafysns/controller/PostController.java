package com.ssafysns.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.PostService;
import com.ssafysns.repository.CommentRepository;

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
	
	class Pair {
		private Post post;
		private List<Comment> comments = new ArrayList<Comment>();
		
		public Pair() {}
		public Pair(Post post, List<Comment> comments) {
			this.post = post;
			this.comments.addAll(comments);
		}		
		
		public Post getPost() {
			return post;
		}
		public void setPost(Post post) {
			this.post = post;
		}
		public List<Comment> getComments() {
			return comments;
		}
		public void setComments(List<Comment> comments) {
			this.comments = comments;
		}
		
		@Override
		public String toString() {
			return "Pair [post=" + post + ", comments=" + comments.toString() + "]";
		}
		
	}

//	@ApiOperation(value = "모든 Post 목록 조회")
//	@ResponseBody
//	@GetMapping("/list/{pno}")
//	public ResponseEntity<List<Comment>> searchAll() throws Exception {
//		List<Pair> lists = new ArrayList<Pair>();
//		
//		List<Comment> comments = commentService.joinPost(pno);
//		
//		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
//	}

	
	public ResponseEntity<List<Pair>> searchAll() throws Exception {
//	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		List<Post> post_list = postService.searchAll();
		System.out.println("post_list.size() = "+ post_list.size());
		List<Comment> comment_list = commentService.searchPno(1);
		System.out.println("comment_list.size() = "+ comment_list.size());
		
		List<Pair> lists = new ArrayList<Pair>();
		lists.add(new Pair(post_list.get(0), comment_list));
		
		System.out.println("==========================================");
		System.out.println(lists.get(0).post.toString());
		System.out.println(lists.get(0).comments.get(0).getContent());
		System.out.println(lists.get(0).comments.get(1).getContent());
		System.out.println(lists.get(0).comments.get(2).getContent());
		return new ResponseEntity<List<Pair>>(lists, HttpStatus.OK);
//		return handleSuccess(postService.searchAll());
	}

//	@ApiOperation(value = "pno에 해당하는 Post 조회")
//	@GetMapping("/{pno}")
//	public ResponseEntity<Map<String, Object>> search(@PathVariable int pno) throws Exception {
//		return handleSuccess(postService.search(pno));
//	}
	@ApiOperation(value = "hashtag에 해당하는 Comment 조회")
	@GetMapping("/{hashtag}")
	public ResponseEntity<List<Comment>> searchPno(@PathVariable String hashtag) throws Exception {
		List<Comment> comments = commentService.joinPost(hashtag);
//		
		System.out.println("InController....");
		System.out.println(comments.toString());
		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
//		return handleSuccess(postService.search(pno));
	}

	@ApiOperation(value = "Post 등록")
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Post post) throws Exception {
		postService.insert(post);
		return handleSuccess("Post 등록 완료");
	}

	@ApiOperation(value = "pno에 해당하는 Post 삭제")
	@DeleteMapping("/{pno}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int pno) throws Exception {
		postService.delete(pno);
		return handleSuccess("Post 삭제 완료");
	}

	@ApiOperation(value = "Post 수정")
	@PutMapping("")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Post post) throws Exception {
		postService.update(post);
		return handleSuccess("Post 수정 완료");
	}

	//수정 - no넣어주셈
	@ApiOperation(value="", response=List.class)
	@PostMapping("/comment/list/{no}")
	public ResponseEntity<List<Comment>> findAllComments() throws Exception {
		return new ResponseEntity<List<Comment>>(commentService.searchPno(1), HttpStatus.OK);
	}
	
	@ApiOperation(value="id로 Comment 검색", response=Comment.class)
	@GetMapping("/comment/list/{id}")
	public ResponseEntity<List<Comment>> findById(@PathVariable String id) throws Exception {
		return new ResponseEntity<List<Comment>> (commentService.searchId(id), HttpStatus.OK);
	}
	
	@ApiOperation(value="Comment 작성")
	@PostMapping("/comment/new")
	public ResponseEntity<Map<String, Object>> commentInsert(@RequestBody Comment comment) throws Exception {
		commentService.insert(comment);
		return handleSuccess("댓글 등록 완료");
	}
	
	@ApiOperation(value="Comment update")
	@PostMapping("/comment/update")
	public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Comment comment) throws Exception {
		commentService.update(comment);
		return handleSuccess("댓글 수정 완료");
	}
	
	@ApiOperation(value="no로 Comments 삭제")
	@GetMapping("/comment/delete/{no}")
	public ResponseEntity<Map<String, Object>> DeleteComment(@PathVariable int no) throws Exception {
		System.out.println("controller... no: "+no);
		commentService.delete(no);
		return handleSuccess("댓글 삭제 완료");
	}
	


	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e) {
		return handleFail(e.getMessage(), HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
