/*package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.services.InvocationService;

@RestController
public class InvocationRestController {

	@Autowired
	private InvocationService service;
	
	@RequestMapping("invocations/getInvocationsByMethods")
    public String getInvocationsByMethods(Long sessionId, Long invokingId, Long invokedId) {
		return service.getInvocationsByMethods(sessionId, invokingId, invokedId);
    }	
}*/
