package com.ssafysns.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ssafysns.model.dto.FAQ;
import com.ssafysns.model.service.FAQService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="{*}", maxAge=6000)
@RestController
@RequestMapping("/faq")
@Api(value="SSAFY SNS", description="SSAFY SNS")
public class FAQController {
	
	@Autowired
	private FAQService faqService;
	
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> handleSuccess(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state",  "fail");
		resultMap.put("data",  data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value="", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<FAQ>> findAllFAQs() throws Exception {
		return new ResponseEntity<List<FAQ>>(faqService.searchAll(), HttpStatus.OK);
	}
	
	@ApiOperation(value="id로 FAQ 정보 검색", response = FAQ.class)
	@GetMapping("/{id}")
	public ResponseEntity<List<FAQ>> searchFAQ(@PathVariable String id) throws Exception{
		return new ResponseEntity<List<FAQ>> (faqService.search(id), HttpStatus.OK);
	}

	@ApiOperation(value="새로운 FAQ 생성")
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> userInsert(@RequestBody FAQ faq) throws Exception {
		System.out.println("controller: " + faq.toString());
		faqService.insert(faq);
		return handleSuccess("등록 완료");
	}
	
	@ApiOperation(value="no로 FAQ 삭제")
	@DeleteMapping("/{no}")
	public ResponseEntity<Map<String, Object>> DeleteComment(@PathVariable int no) throws Exception {
		System.out.println("controller... no: "+no);
		faqService.delete(no);
		return handleSuccess("faq 삭제 완료");
	}
	
	@ApiOperation(value="FAQ update")
	@PutMapping("")
	public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody FAQ faq) throws Exception {
		faqService.update(faq);
		return handleSuccess("faq 수정 완료");
	}
	
}