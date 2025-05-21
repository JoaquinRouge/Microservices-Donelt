package com.joaquinrouge.donelt.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.donelt.notification.model.Notification;
import com.joaquinrouge.donelt.notification.service.INotificationService;
import com.joaquinrouge.donelt.notification.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	private final INotificationService notiService;
	private final JwtUtils jwtUtils;
	
	public NotificationController(INotificationService notiService,JwtUtils jwtUtils) {
		this.notiService = notiService;
		this.jwtUtils = jwtUtils;
	}
	
	@GetMapping("/user/id/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Object> getByUserId(@PathVariable("id") Long id,
			HttpServletRequest request){
		
		 String token = request.getHeader("Authorization").substring(7);
		 DecodedJWT jwt = jwtUtils.validateJwt(token);
		 Long userIdFromToken = jwt.getClaim("id").asLong();
		
		 if(userIdFromToken != id) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		 }
		 
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notiService.getByUserId(id));
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated and hasRole('ADMIN')")
	public ResponseEntity<Object> createNotification(@RequestBody Notification notification){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(notiService.createNotification(notification));
	}
	
	@DeleteMapping("/delete/{deleteId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Object> deleteNotification(@PathVariable("deleteId") Long deleteId,
			HttpServletRequest request){
		
		 String token = request.getHeader("Authorization").substring(7);
		 DecodedJWT jwt = jwtUtils.validateJwt(token);
		 Long userIdFromToken = jwt.getClaim("id").asLong();
		
		 if(userIdFromToken != notiService.getById(deleteId).getUserId()) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		 }
		 
		try {
			notiService.deleteNotification(deleteId);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
