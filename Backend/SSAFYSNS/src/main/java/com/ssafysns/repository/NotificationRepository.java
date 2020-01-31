package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
