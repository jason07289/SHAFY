package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.FollowHashtag;

public interface FollowHashtagService {
	public boolean update(FollowHashtag followHashtag);

	public Optional<FollowHashtag> searchById(String id);

// public void insert(FollowHashtag followHashtag);

// public void delete(int no);

//	public void deleteById(String id);

//	public void deleteByIdAndHashtag(String id, String hashtag);

//	public Optional<FollowHashtag> search(int no);

//	public List<FollowHashtag> searchAll();

//	public int count();
}
