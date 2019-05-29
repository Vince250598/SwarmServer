package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Type;
import swarm.server.services.TypeService;

@RestController
public class TypeRestController {

	@Autowired
	private TypeService service;
	
	@RequestMapping("/types/getBySessionId/")
    public Iterable<Type> getBySessionId(Long sessionId) {
		return service.typesBySessionId(sessionId);
    }
}