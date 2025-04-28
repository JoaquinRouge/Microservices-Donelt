package com.joaquinrouge.donelt.notification.service;

import java.util.List;

import com.joaquinrouge.donelt.notification.model.Notification;

public interface INotificationService {
	List<Notification> getByUserId(Long id);
	Notification createNotification(Notification notification);
	void deleteNotification(Long id);
}
