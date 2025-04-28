package com.joaquinrouge.donelt.Task.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.joaquinrouge.donelt.Task.client.INotificationClient;
import com.joaquinrouge.donelt.Task.dto.NotificationDto;
import com.joaquinrouge.donelt.Task.model.Task;
import com.joaquinrouge.donelt.Task.repository.ITaskRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class TaskService implements ITaskService{

	private final ITaskRepository taskRepo;
	
	private final INotificationClient notiClient;
	
	public TaskService(ITaskRepository taskRepo,INotificationClient notiClient) {
		this.taskRepo = taskRepo;
		this.notiClient = notiClient;
	}
	
	
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
		
		taskRepo.deleteById(id);
		
	}

	@Override
	public Task updateTask(Task task) {
		
		Task taskFromDb = this.getTask(task.getId());
		
		taskFromDb.setExpirationDate(task.getExpirationDate());
		taskFromDb.setDescription(task.getDescription());
		taskFromDb.setTitle(task.getTitle());
		taskFromDb.setLastNotified(task.getLastNotified());
		
		return taskRepo.save(taskFromDb);
	}

	@Override
	@Scheduled(cron = "0 08 12 * * ?")
	@CircuitBreaker(name = "task-service", fallbackMethod = "generateNotificationFallbackMethod")
	public void generateNotifications() {
	    LocalDate today = LocalDate.now();

	    for (Task task : this.getAll()) {
	    	Long daysRemaining = ChronoUnit.DAYS.between(today, task.getExpirationDate());
	    	if(task.getLastNotified() == null || !task.getLastNotified().equals(today)) {
	    		if (daysRemaining >= 0 && daysRemaining <= 3) {
	    			task.setLastNotified(today);
	    			this.updateTask(task);
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

	@Override
	public void completeTask(Long id) {
		Task task = this.getTask(id);
		
		task.setCompleted(true);
		
		taskRepo.save(task);
	}

	public void generateNotificationFallbackMethod(Throwable t) {
		System.out.println("Notification fallback triggered: " + t.getMessage());
	}

}
