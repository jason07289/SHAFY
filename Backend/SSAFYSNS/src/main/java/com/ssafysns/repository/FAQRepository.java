package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.FAQ;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Integer> {

}
