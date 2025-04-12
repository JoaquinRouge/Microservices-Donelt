package com.joaquinrouge.donelt.Task.dto;

public class NotificationDto {
	
	private Long id;
	private Long userId;
	private String message;
	
	private NotificationDto(){
		
	}

	public NotificationDto(Long userId, String message) {
		super();
		this.userId = userId;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
