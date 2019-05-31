package swarm.server.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Type;
import swarm.server.services.TypeService;

@RestController
public class TypeRestController {

	@Autowired
	private TypeService typeService;
	
	@RequestMapping("/types/getBySessionId/")
    public Iterable<Type> getBySessionId(Long sessionId) {
		return typeService.typesBySessionId(sessionId);
    }
	
	@PostMapping("/types")
	public Type newType(@RequestBody Type type) {
		return typeService.save(type);
	}
	
	@RequestMapping("/types/{id}")
	public Optional<Type> typeById(@PathVariable Long id) {
		return typeService.typeById(id);
	}
	
}