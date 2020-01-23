package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.TabHashtag;
import com.ssafysns.model.dto.TabHashtagException;
import com.ssafysns.repository.TabHashtagRepository;

@Service
public class TabHashtagServiceImpl implements TabHashtagService {

	@Autowired
	TabHashtagRepository tabHashtagRepository;

	@Override
	public void insert(TabHashtag tabHashtag) {
		try {
			tabHashtagRepository.save(tabHashtag);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("TabHashtag 등록 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void delete(int no) {
		try {
			tabHashtagRepository.deleteById(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("TabHashtag 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void update(TabHashtag tabHashtag) {
		try {
			tabHashtagRepository.save(tabHashtag);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("TabHashtag 수정 중 오류가 발생했습니다.");
		}
	}

	@Override
	public Optional<TabHashtag> search(int no) {
		try {
			return tabHashtagRepository.findById(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("TabHashtag 조회 중 오류가 발생했습니다.");
		}
	}

	public List<TabHashtag> searchById(String id) {
		try {
			return tabHashtagRepository.findByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("TabHashtag 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<TabHashtag> searchAll() {
		try {
			return tabHashtagRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("TabHashtag 목록 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public int count() {
		try {
			return (int) tabHashtagRepository.count();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException();
		}
	}

}
