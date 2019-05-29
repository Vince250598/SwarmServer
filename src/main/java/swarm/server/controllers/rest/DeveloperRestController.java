package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Developer;
import swarm.server.services.DeveloperService;

@RestController
public class DeveloperRestController {

	@Autowired
	private DeveloperService developerService; 

	@RequestMapping("/login")
    public Developer login(String name) {
		return developerService.login(name);
    }
	
	@PostMapping("developers")
	public Developer newDeveloper(@RequestBody Developer newDeveloper) {
		return developerService.save(newDeveloper);
	}
}
