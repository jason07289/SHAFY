package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.TabHashtag;

@Repository
public interface TabHashtagRepository extends JpaRepository<TabHashtag, Integer> {

}
