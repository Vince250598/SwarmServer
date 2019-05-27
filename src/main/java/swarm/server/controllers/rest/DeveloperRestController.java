package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Developer;
import swarm.server.services.DeveloperService;

@RestController
public class DeveloperRestController {

	@Autowired
	private DeveloperService developerService; 

	@RequestMapping("/login/{name}")
    public Developer login(@PathVariable String name) {
		return developerService.login(name);
    }
}
