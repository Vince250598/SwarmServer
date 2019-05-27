package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Method;
import swarm.server.domains.Session;
import swarm.server.services.MethodService;
import swarm.server.services.SessionService;

@RestController
public class SessionRestController {

	@Autowired
	private SessionService service;
	
	@Autowired
	private MethodService methodService;
	
	@RequestMapping("/sessions/find/{taskId}/{developerId}")
    public Iterable<Session> findSessions(@PathVariable Long taskId, @PathVariable Long developerId) {
		return service.sessionsByTaskIdAndDeveloperId(taskId, developerId);
    }	
	
	@RequestMapping("/sessions/graph/{sessionId}/{addType}")
    public String getGraphData(@PathVariable Long sessionId, @PathVariable boolean addType) {
		return service.getGraphData(sessionId, addType);
    }
	
	@RequestMapping("/sessions/stack/{sessionId}")
    public String getStackData(@PathVariable Long sessionId) {
		return service.getStackData(sessionId);
    }

	@RequestMapping("/sessions/interPathEdges/{sessionId}")
    public String getInterPathEdges(@PathVariable Long sessionId) {
		return service.getInterPathEdges(sessionId);
    }
	
	@RequestMapping("/sessions/countElements/{sessionId}")
    public int countElements(@PathVariable Long sessionId) {
		return service.countElements(sessionId);
    }

	@RequestMapping("/sessions/startingMethods/{sessionId}")
    public Iterable<Method> getStartingMethods(@PathVariable Long sessionId) {
		return methodService.startingMethodsBySessionId(sessionId);
    }
	
	@RequestMapping("/sessions/endingMethods/{sessionId}")
    public Iterable<Method> getEndingMethods(@PathVariable Long sessionId) {
		return methodService.endingMethodsBySessionId(sessionId);
    }
}
