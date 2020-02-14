package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.exception.BookmarkException;
import com.ssafysns.model.dto.Bookmark;
import com.ssafysns.repository.BookmarkRepository;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	@Autowired
	BookmarkRepository bookmarkRepository;

	@Override
	public void insert(Bookmark bookmark) {
		try {
			Optional<Bookmark> find = bookmarkRepository.findByUserIdAndPno(bookmark.getId(), bookmark.getPno());
			if (find.isPresent()) {
				throw new BookmarkException("Bookmark 등록 중 오류가 발생했습니다. : 이미 존재하는 bookmark입니다.");
			} else {
				bookmarkRepository.save(bookmark);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkException("Bookmark 등록 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void deleteById(String id) {
		try {
			bookmarkRepository.deleteByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkException("Bookmark 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void deleteByIdAndPno(String id, int pno) {
		try {
			bookmarkRepository.deleteByUserIdAndPno(id, pno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkException("Bookmark 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<Bookmark> searchById(String id) {
		try {
			return bookmarkRepository.findByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookmarkException(id + "로 Bookmark 조회 중 오류가 발생했습니다.");
		}
	}

//	@Override
//	public void delete(int no) {
//		try {
//			bookmarkRepository.deleteById(no);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BookmarkException("Bookmark 삭제 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public void update(Bookmark bookmark) {
//		try {
//			bookmarkRepository.save(bookmark);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BookmarkException("Bookmark 수정 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public Optional<Bookmark> search(int no) {
//		try {
//			return bookmarkRepository.findById(no);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BookmarkException(no + "로 Bookmark 조회 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public List<Bookmark> searchAll() {
//		try {
//			return bookmarkRepository.findAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BookmarkException("Bookmark 목록 조회 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public int count() {
//		try {
//			return (int) bookmarkRepository.count();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BookmarkException("Bookmark 개수 조회 중 오류가 발생했습니다.");
//		}
//	}

}
