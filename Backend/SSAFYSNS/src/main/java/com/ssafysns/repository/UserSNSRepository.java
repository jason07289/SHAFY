package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.UserSNS;

@Repository
public interface UserSNSRepository extends JpaRepository<UserSNS, Integer> {
	public UserSNS findByEmailAndType(String email,String type);
}
