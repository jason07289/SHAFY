package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.Notice;
import com.ssafysns.model.dto.NoticeException;
import com.ssafysns.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;

	@Override
	public void insert(Notice notice) {
		try {
			noticeRepository.save(notice);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoticeException("공지사항 저장 중 오류가 발생했습니다.");
		}
	}

	@Override
	@Transactional
	public void delete(int no) {
		try {
			noticeRepository.updateDeleted(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoticeException("공지사항 삭제 중 오류가 발생했습니다.");
		}

	}

	@Override
	public void update(Notice notice) {
		try {
			noticeRepository.save(notice);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoticeException("공지사항 수정 중 오류가 발생했습니다.");
		}
	}

	@Override
	public Optional<Notice> search(int no) {
		try {
			return noticeRepository.findById(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoticeException("공지사항 검색 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<Notice> searchAll() {
		try {
			return noticeRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoticeException("공지사항 목록 검색 중 오류가 발생했습니다.");
		}
	}

	@Override
	public int count() {
		try {
			return (int) noticeRepository.count();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoticeException("공지사항 개수 조회 중 오류가 발생했습니다.");
		}
	}
}
