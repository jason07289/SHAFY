package com.ssafysns.model.service;

import java.util.Date;
import java.util.List;

import com.ssafysns.model.dto.Notification;

public interface NotificationService {
	public void insert(Notification notification);

	public void delete(Date datetime);

	public void update(Notification notification);

	public List<Notification> searchAll();

	public long count(String id);

	public boolean userAlarmCheck(String id);

}
