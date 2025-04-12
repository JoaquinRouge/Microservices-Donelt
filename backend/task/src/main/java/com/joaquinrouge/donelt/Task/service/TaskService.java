package com.joaquinrouge.donelt.Task.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.joaquinrouge.donelt.Task.client.INotificationClient;
import com.joaquinrouge.donelt.Task.dto.NotificationDto;
import com.joaquinrouge.donelt.Task.model.Task;
import com.joaquinrouge.donelt.Task.repository.ITaskRepository;

@Service
public class TaskService implements ITaskService{

	@Autowired
	private ITaskRepository taskRepo;
	
	@Autowired
	private INotificationClient notiClient;
	
	@Override
	public List<Task> getAll(){
		return taskRepo.findAll();
	}
	
	@Override
	public Task getTask(Long id) {
		return taskRepo.findById(id)
		.orElseThrow(()-> new IllegalArgumentException("Task with id " + id + " not found"));
	}
	
	@Override
	public List<Task> getTasksForUser(Long userId) {
		return taskRepo.findByUserId(userId);
	}

	@Override
	public List<Task> getUncompletedTasks(Long userId) {
		return taskRepo.findByUserIdAndCompletedFalse(userId);
	}

	@Override
	public Task createTask(Task task) {
		return taskRepo.save(task);
	}

	@Override
	public void deleteTask(Long id) {
		if(!taskRepo.existsById(id)) {
			throw new IllegalArgumentException("Task with id " + id + " not found");
		}
	}

	@Override
	public Task updateTask(Task task) {
		
		Task taskFromDb = this.getTask(task.getId());
		
		taskFromDb.setCompleted(false);
		taskFromDb.setDescription(task.getDescription());
		taskFromDb.setTitle(task.getTitle());
		
		return taskRepo.save(taskFromDb);
	}

	@Override
	@Scheduled(cron = "0 13 17 * * ?")
	public void generateNotifications() {
	    LocalDate today = LocalDate.now();

	    for (Task task : this.getAll()) {
	        long daysRemaining = ChronoUnit.DAYS.between(today, task.getExpirationDate());

	        if (daysRemaining >= 0 && daysRemaining <= 3) {
	            notiClient.createNotification(
	                new NotificationDto(
	                    task.getUserId(),
	                    task.getTitle() + " expires in " + daysRemaining + " days"
	                )
	            );
	        }
	    }
	}


}
