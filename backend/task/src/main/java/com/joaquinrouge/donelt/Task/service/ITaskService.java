package com.joaquinrouge.donelt.Task.service;

import java.util.List;

import com.joaquinrouge.donelt.Task.model.Task;

public interface ITaskService {
	List<Task> getAll();
	Task getTask(Long id);
	List<Task> getTasksForUser(Long userId);
	List<Task> getUncompletedTasks(Long userId);
	Task createTask(Task task);
	void deleteTask(Long id);
	Task updateTask(Task task);
	void generateNotifications();
	void completeTask(Long id);
}
