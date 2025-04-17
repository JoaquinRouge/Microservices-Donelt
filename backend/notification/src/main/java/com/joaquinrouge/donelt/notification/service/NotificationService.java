package com.joaquinrouge.donelt.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joaquinrouge.donelt.notification.model.Notification;
import com.joaquinrouge.donelt.notification.repository.INotificationRepository;

@Service
public class NotificationService implements INotificationService {

	private final INotificationRepository notiRepo;
	
	public NotificationService(INotificationRepository notiRepo) {
		this.notiRepo = notiRepo;
	}

	@Override
	public List<Notification> getByUserId(Long id) {
		return notiRepo.findByUserId(id)
				.orElseThrow(()-> new IllegalArgumentException(
						"Notification with id" + id + "not found"));
	}

	@Override
	public Notification createNotification(Notification notification) {
		return notiRepo.save(notification); //No need of previous validations
	}

	@Override
	public void deleteNotification(Long id) {
		if(!notiRepo.existsById(id)) {
			throw new IllegalArgumentException("Notification not found");
		}
		
		notiRepo.deleteById(id);
		
	}

}
