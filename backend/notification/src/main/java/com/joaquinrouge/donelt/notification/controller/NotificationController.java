package com.joaquinrouge.donelt.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaquinrouge.donelt.notification.model.Notification;
import com.joaquinrouge.donelt.notification.service.INotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	private final INotificationService notiService;
	
	public NotificationController(INotificationService notiService) {
		this.notiService = notiService;
	}
	
	@GetMapping("/user/id/{id}")
	public ResponseEntity<Object> getByUserId(@PathVariable("id") Long id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notiService.getByUserId(id));
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createNotification(@RequestBody Notification notification){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(notiService.createNotification(notification));
	}
	
	@DeleteMapping("/delete/{deleteId}")
	public ResponseEntity<Object> deleteNotification(@PathVariable("deleteId") Long deleteId){
		try {
			notiService.deleteNotification(deleteId);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
