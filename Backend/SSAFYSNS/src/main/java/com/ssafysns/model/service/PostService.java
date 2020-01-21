package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.Post;

public interface PostService {
	public void insert(Post post);

	public void delete(int pno);

	public void update(Post post);

	public Optional<Post> search(int pno);

	public List<Post> searchAll();

	public int count();
}
