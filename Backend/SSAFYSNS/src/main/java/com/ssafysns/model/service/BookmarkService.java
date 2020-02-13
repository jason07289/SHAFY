package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.Bookmark;

public interface BookmarkService {

	public void insert(Bookmark bookmark);

	public void deleteById(String id);

	public void deleteByIdAndPno(String id, int pno);

	public List<Bookmark> searchById(String id);

//	public void delete(int no);

//	public void update(Bookmark bookmark);

//	public Optional<Bookmark> search(int no);

//	public List<Bookmark> searchAll();

//	public int count();
}
