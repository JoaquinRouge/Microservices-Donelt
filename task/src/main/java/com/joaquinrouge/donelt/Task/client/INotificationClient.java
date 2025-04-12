package com.joaquinrouge.donelt.Task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.joaquinrouge.donelt.Task.dto.NotificationDto;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface INotificationClient {
	@PostMapping("/api/notification/create")
	public NotificationDto createNotification(@RequestBody NotificationDto notification);
}
