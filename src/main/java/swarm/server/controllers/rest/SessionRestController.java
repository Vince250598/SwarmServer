/*package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.services.SessionService;

@RestController
public class SessionRestController {

	@Autowired
	private SessionService service;
	
	@RequestMapping("/sessions/find")
    public String findSessions(Long projectId, Long developerId) {
		return service.findSessions(projectId, developerId);
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
    public String getStartingMethods(Long sessionId) {
		return service.getStartingMethods(sessionId);
    }
	
	@RequestMapping("/sessions/endingMethods")
    public String getEndingMethods(Long sessionId) {
		return service.getEndingMethods(sessionId);
    }
}*/
