package com.joaquinrouge.donelt.notification.service;

import java.util.List;
import java.util.Optional;

import com.joaquinrouge.donelt.notification.model.Notification;

public interface INotificationService {
	Notification getById(Long id);
	List<Notification> getByUserId(Long id);
	Notification createNotification(Notification notification);
	void deleteNotification(Long id);
}
