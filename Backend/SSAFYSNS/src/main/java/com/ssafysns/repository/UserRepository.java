package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
