package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
	
}
