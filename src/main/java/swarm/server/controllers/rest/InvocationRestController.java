package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Invocation;
import swarm.server.services.InvocationService;

@RestController
public class InvocationRestController {

	@Autowired
	private InvocationService service;
	
	@RequestMapping(value = "invocations/getInvocationsByMethods/{sessionId}/{invokingId}/{invokedId}", method = RequestMethod.GET)
	@ResponseBody
    public Iterable<Invocation> getInvocationsByMethods(@PathVariable Long sessionId, @PathVariable Long invokingId, @PathVariable Long invokedId) {
		return service.getInvocationsByMethods(sessionId, invokingId, invokedId);
    }	
}
