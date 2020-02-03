package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.model.dto.FAQ;

public interface FAQService {
	
	public void insert(FAQ faq);
	public void update(FAQ faq);
	public void delete(int no);
	public List<FAQ> search(String id);
	public List<FAQ> searchAll();
	
}
