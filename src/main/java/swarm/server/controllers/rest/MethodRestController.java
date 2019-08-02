package swarm.server.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Method;
import swarm.server.services.MethodService;

@RestController
public class MethodRestController {

	@Autowired
	private MethodService methodService; 

	@RequestMapping("methods/getByTypeId")
    public Iterable<Method> getByTypeId(Long typeId) {
		return methodService.methodByTypeId(typeId);
    }
	
	@PostMapping("/methods")
	public Method newMethod(@RequestBody Method method) {
		return methodService.save(method);
	}
		
	@RequestMapping("/methods/{id}")
	public Optional<Method> getById(@PathVariable Long id) {
		return methodService.methodById(id);
	}
}
