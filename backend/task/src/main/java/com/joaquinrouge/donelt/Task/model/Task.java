package com.joaquinrouge.donelt.Task.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private boolean completed;
	
	@Column(nullable = false)
	private LocalDate creationDate;
	
	@Column(nullable = false)
	private LocalDate expirationDate;
	
	@Column(nullable = true)
	private LocalDate lastNotified;	
	
	@Column(nullable = false)
	private Long userId;
	
	public Task() {
		
	}

	public Task(Long id, String title, String description, boolean completed, LocalDate creationDate,
			LocalDate expirationDate,LocalDate lastNotified, Long userId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.creationDate = creationDate;
		this.expirationDate = expirationDate;
		this.lastNotified = lastNotified;
		this.userId = userId;
	}
	
	public LocalDate getLastNotified() {
		return lastNotified;
	}

	public void setLastNotified(LocalDate lastNotified) {
		this.lastNotified = lastNotified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
