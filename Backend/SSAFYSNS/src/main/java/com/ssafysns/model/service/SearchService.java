package com.ssafysns.model.service;

import java.util.Date;
import java.util.List;

import com.ssafysns.model.dto.Search;

public interface SearchService {
	public void insert(Search search);

	public void delete(int no);

	public void deleteBySearchtime(Date datetime);

	public void update(Search search);

	public List<Search> searchAll();

	public List<Search> searchBySearchtime();

	public int count();
}
