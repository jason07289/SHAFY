package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.FavoritesHashtag;
import com.ssafysns.model.dto.FavoritesHashtagException;
import com.ssafysns.model.dto.TabHashtagException;
import com.ssafysns.repository.FavoritesHashtagRepository;

@Service
public class FavoritesHashtagServiceImpl implements FavoritesHashtagService {

	@Autowired
	FavoritesHashtagRepository favoritesHashtagRepository;

	@Override
	public void insert(FavoritesHashtag favoritesHashtag) {
		try {
			Optional<FavoritesHashtag> find = favoritesHashtagRepository.findByUserIdAndHashtag(favoritesHashtag.getId(),
					favoritesHashtag.getHashtag());
			if (find.isPresent()) {
				throw new FavoritesHashtagException("FavoritesHashtag 등록 중 오류가 발생했습니다. : 이미 존재하는 hashtag입니다.");
			} else {
				favoritesHashtagRepository.save(favoritesHashtag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FavoritesHashtagException("FavoritesHashtag 등록 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void delete(int no) {
		try {
			favoritesHashtagRepository.deleteById(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FavoritesHashtagException("FavoritesHashtag 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void deleteById(String id) {
		try {
			favoritesHashtagRepository.deleteByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("FavoritesHashtag 삭제 중 오류가 발생했습니다.");
		}
	}

	public void deleteByIdAndHashtag(String id, String hashtag) {
		try {
			favoritesHashtagRepository.deleteByUserIdAndHashtag(id, hashtag);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("FavoritesHashtag 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void update(FavoritesHashtag favoritesHashtag) {
		try {
			favoritesHashtagRepository.save(favoritesHashtag);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FavoritesHashtagException("FavoritesHashtag 수정 중 오류가 발생했습니다.");
		}
	}

	@Override
	public Optional<FavoritesHashtag> search(int no) {
		try {
			return favoritesHashtagRepository.findById(no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FavoritesHashtagException(no + "로 FavoritesHashtag 조회 중 오류가 발생했습니다.");
		}
	}

	public List<FavoritesHashtag> searchById(String id) {
		try {
			return favoritesHashtagRepository.findByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FavoritesHashtagException(id + "로 FavoritesHashtag 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<FavoritesHashtag> searchAll() {
		try {
			return favoritesHashtagRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FavoritesHashtagException("FavoritesHashtag 목록 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public int count() {
		try {
			return (int) favoritesHashtagRepository.count();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FavoritesHashtagException();
		}
	}

}
