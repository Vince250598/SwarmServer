package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Invocation;
import swarm.server.services.InvocationService;

@RestController
public class InvocationRestController {

	@Autowired
	private InvocationService invocationService;
	
	@RequestMapping("invocations/getInvocationsByMethods")
    public Iterable<Invocation> getInvocationsByMethods(Long sessionId, Long invokingId, Long invokedId) {
		return invocationService.getInvocationsByMethods(sessionId, invokingId, invokedId);
    }	
}
