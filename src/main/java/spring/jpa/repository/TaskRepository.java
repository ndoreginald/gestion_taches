package spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import spring.jpa.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByProjectId(Long projectId);

}
