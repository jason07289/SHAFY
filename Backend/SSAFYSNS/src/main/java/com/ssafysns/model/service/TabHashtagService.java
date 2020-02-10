package com.ssafysns.model.service;

import java.util.Optional;

import com.ssafysns.model.dto.TabHashtag;

public interface TabHashtagService {

	public void update(TabHashtag tabHashtag);

	public Optional<TabHashtag> searchById(String id);

//	public void insert(TabHashtag tabHashtag);
//
//	public void delete(int no);

//	public void deleteById(String id);

//	public void deleteByIdAndHashtag(String id, String hashtag);

	// public Optional<TabHashtag> search(int no);

//	public List<TabHashtag> searchAll();

//	public int count();
}
