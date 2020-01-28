package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.BookmarkHashtag;

public interface BookmarkHashtagService {
	public void insert(BookmarkHashtag bookmarkHashtag);

	public void delete(int no);

	public void deleteById(String id);

	public void deleteByIdAndHashtag(String id, String hashtag);

	public void update(BookmarkHashtag bookmarkHashtag);

	public Optional<BookmarkHashtag> search(int no);

	public List<BookmarkHashtag> searchById(String id);

	public List<BookmarkHashtag> searchAll();

	public int count();
}
