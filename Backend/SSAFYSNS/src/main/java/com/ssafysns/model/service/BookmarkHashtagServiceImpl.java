package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.BookmarkHashtag;
import com.ssafysns.model.dto.BookmarkHashtagException;
import com.ssafysns.repository.BookmarkHashtagRepository;

@Service
public class BookmarkHashtagServiceImpl implements BookmarkHashtagService {

	@Autowired
	BookmarkHashtagRepository bookmarkHashtagRepository;

	@Override
	public void insert(BookmarkHashtag bookmarkHashtag) {
		try {
			bookmarkHashtagRepository.save(bookmarkHashtag);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkHashtagException("BookmarkHashtag 등록 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void delete(int no) {
		try {
			bookmarkHashtagRepository.deleteById(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkHashtagException("BookmarkHashtag 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void update(BookmarkHashtag bookmarkHashtag) {
		try {
			bookmarkHashtagRepository.save(bookmarkHashtag);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkHashtagException("BookmarkHashtag 수정 중 오류가 발생했습니다.");
		}
	}

	@Override
	public Optional<BookmarkHashtag> search(int no) {
		try {
			return bookmarkHashtagRepository.findById(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkHashtagException(no + "로 BookmarkHashtag 조회 중 오류가 발생했습니다.");
		}
	}

	public List<BookmarkHashtag> searchById(String id) {
		try {
			return bookmarkHashtagRepository.findByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkHashtagException(id + "로 BookmarkHashtag 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<BookmarkHashtag> searchAll() {
		try {
			return bookmarkHashtagRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkHashtagException("BookmarkHashtag 목록 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public int count() {
		try {
			return (int) bookmarkHashtagRepository.count();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkHashtagException();
		}
	}

}
