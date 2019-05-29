package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping("/sessions/find")
    public Iterable<Session> findSessions(Long taskId, Long developerId) {
		return service.sessionsByTaskIdAndDeveloperId(taskId, developerId);
    }	
	
	@RequestMapping("/sessions/graph")
    public String getGraphData(Long sessionId, boolean addType) {
		return service.getGraphData(sessionId, addType);
    }
	
	@RequestMapping("/sessions/stack")
    public String getStackData(Long sessionId) {
		return service.getStackData(sessionId);
    }

	@RequestMapping("/sessions/interPathEdges")
    public String getInterPathEdges(Long sessionId) {
		return service.getInterPathEdges(sessionId);
    }
	
	@RequestMapping("/sessions/countElements")
    public int countElements(Long sessionId) {
		return service.countElements(sessionId);
    }

	@RequestMapping("/sessions/startingMethods")
    public Iterable<Method> getStartingMethods(Long sessionId) {
		return methodService.startingMethodsBySessionId(sessionId);
    }
	
	@RequestMapping("/sessions/endingMethods")
    public Iterable<Method> getEndingMethods(Long sessionId) {
		return methodService.endingMethodsBySessionId(sessionId);
    }
}
