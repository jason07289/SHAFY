package com.ssafysns.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Search;
import com.ssafysns.model.dto.SearchException;
import com.ssafysns.repository.SearchRepository;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	SearchRepository searchRepository;

	@Override
	public void insert(Search search) {
		try {
			searchRepository.save(search);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SearchException("Search 등록 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void delete(int no) {
		try {
			searchRepository.deleteById(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SearchException("Search 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void deleteBySearchtime(Date datetime) {
		try {
			// datetime이 현재 날짜보다 7일 이상 이전일 경우에만 delete를 수행하도록 수정하기!!!
			searchRepository.deleteBySearchtime(datetime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SearchException("Search 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void update(Search search) {
		try {
			searchRepository.save(search);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SearchException("Search 수정 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<Search> searchAll() {
		try {
			return searchRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SearchException("Search 목록 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<Search> searchBySearchtime() {
		try {
			return searchRepository.findBySearchtime();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SearchException("7일 이내의 Search 목록 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public int count() {
		return (int)searchRepository.count();
	}

}
