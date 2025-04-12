package com.joaquinrouge.donelt.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.donelt.notification.model.Notification;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long>{
	Optional<List<Notification>> findByTaskId(Long id);
	Optional<List<Notification>> findByUserId(Long id);
}
