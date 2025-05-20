package com.joaquinrouge.donelt.Task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.donelt.Task.model.Task;
import com.joaquinrouge.donelt.Task.service.ITaskService;
import com.joaquinrouge.donelt.Task.utils.JwtUtils;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/task")
public class TaskController {

	private final ITaskService taskService;
	private final JwtUtils jwtUtils;
	
	public TaskController(ITaskService taskService,JwtUtils jwtUtils) {
		this.taskService = taskService;
		this.jwtUtils = jwtUtils;
	}
	
	@GetMapping("/user/id/{userId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Object> tasksForUser(@PathVariable("userId") Long userId,
			HttpServletRequest request){
		
		 String token = request.getHeader("Authorization").substring(7);
		 DecodedJWT jwt = jwtUtils.validateJwt(token);
		 Long userIdFromToken = jwt.getClaim("id").asLong();
		
		 if(userIdFromToken != userId) {
			  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
		 }
		 
		return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasksForUser(userId));
	}
	
	@GetMapping("/user/id/{userId}/pending")
	public ResponseEntity<Object> pendingTasks(@PathVariable("userId") Long userId){
		return ResponseEntity.status(HttpStatus.OK).body(taskService.getUncompletedTasks(userId));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createTask(@RequestBody Task task){
		return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteTask(@PathVariable("id") Long id){
		try {
			taskService.deleteTask(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updateTask(@RequestBody Task task){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(taskService.createTask(taskService.updateTask(task)));
	}
	
	@PutMapping("/complete/{id}")
	public ResponseEntity<Object> completeTask(@PathVariable Long id){
		taskService.completeTask(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
}
