package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
}
