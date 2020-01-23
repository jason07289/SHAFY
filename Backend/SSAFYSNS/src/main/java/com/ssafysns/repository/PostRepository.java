package com.ssafysns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	
	
	
}
