package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.TabHashtag;

public interface TabHashtagService {
	public void insert(TabHashtag tabHashtag);

	public void delete(int no);

	public void update(TabHashtag tabHashtag);

	public Optional<TabHashtag> search(int no);

	public List<TabHashtag> searchById(String id);

	public List<TabHashtag> searchAll();

	public int count();
}
