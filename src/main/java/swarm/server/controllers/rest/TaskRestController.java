package swarm.server.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Task;
import swarm.server.services.TaskService;

@RestController
public class TaskRestController {

	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/tasks/getByDeveloperId")
	public Iterable<Task> taskByDeveloperId(Long developerId) {
		return taskService.TasksByDeveloperId(developerId);
	}
	
	@PostMapping("/tasks") // "/methods" in SwarmManager, strange?
	public Task newTask(@RequestBody Task task) {
		return taskService.save(task);
	}
	
	@RequestMapping("/tasks/all")
	public Iterable<Task> allTasks() {
		return taskService.allTasks();
	}
	
	@RequestMapping("/tasks/{id}")
	public Optional<Task> taskById(@PathVariable Long id) {
		return taskService.taskById(id);
	}

	@PutMapping("/tasks/{id}")
	public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
		task.setId(id);
		return taskService.save(task);
	}
	
}
