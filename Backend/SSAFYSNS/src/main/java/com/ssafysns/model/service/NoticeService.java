package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.Notice;

public interface NoticeService {
	public void insert(Notice notice);

	public void delete(int no);

	public void update(Notice notice);

	public Optional<Notice> search(int no);

	public List<Notice> searchAll();

	public int count();

}
