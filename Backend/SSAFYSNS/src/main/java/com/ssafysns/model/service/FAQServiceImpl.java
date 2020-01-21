package com.ssafysns.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.FAQ;
import com.ssafysns.repository.FAQRepository;

@Service
public class FAQServiceImpl implements FAQService {
	
	@Autowired
	FAQRepository faqRepository;

	@Override
	public void insert(FAQ faq) {
		try {
			faqRepository.save(faq);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(FAQ faq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FAQ search(String id) {
		FAQ faq = null;
		try {
			faq =  faqRepository.findById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return faq;
	}

	@Override
	public List<FAQ> searchAll() {
		List<FAQ> faqs = null;
		try {
			faqs =  faqRepository.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return faqs;
	}

}
