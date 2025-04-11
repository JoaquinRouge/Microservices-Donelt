package com.joaquinrouge.donelt.Task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquinrouge.donelt.Task.model.Task;
import com.joaquinrouge.donelt.Task.repository.ITaskRepository;

@Service
public class TaskService implements ITaskService{

	@Autowired
	private ITaskRepository taskRepo;
	
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

}
