package com.joaquinrouge.donelt.Task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.donelt.Task.model.Task;

@Repository
public interface ITaskRepository extends JpaRepository<Task,Long>{
	List<Task> findByUserId(Long id);
	List<Task> findByUserIdAndCompletedFalse(Long id);
}
