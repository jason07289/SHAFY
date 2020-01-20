package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafysns.dto.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

}
