package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.FavoritesHashtag;

public interface FavoritesHashtagService {
	public void insert(FavoritesHashtag favoritesHashtag);

	public void delete(int no);

	public void deleteById(String id);

	public void deleteByIdAndHashtag(String id, String hashtag);

	public void update(FavoritesHashtag favoritesHashtag);

	public Optional<FavoritesHashtag> search(int no);

	public List<FavoritesHashtag> searchById(String id);

	public List<FavoritesHashtag> searchAll();

	public int count();
}
