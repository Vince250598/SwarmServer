package swarm.server.controllers.rest;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Method;
import swarm.server.domains.Session;
import swarm.server.services.MethodService;
import swarm.server.services.SessionService;

@RestController
public class SessionRestController {

	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private MethodService methodService;
	
	@RequestMapping("/sessions/find")
    public Iterable<Optional<Session>> findSessionsByTaskIdAndDeveloperId(Long taskId, Long developerId) {
		if(developerId == null) {
			return sessionService.sessionsByTaskId(taskId);
		} else {
			return sessionService.sessionsByTaskIdAndDeveloperId(taskId, developerId);
		}
    }
	
	@RequestMapping("/sessions/{id}")
	public Optional<Session> sessionById(@PathVariable Long id) {
		return sessionService.sessionById(id);
	}
	
	@RequestMapping("/sessions/graph")
    public String getGraphData(Long sessionId, boolean addType) {
		return sessionService.getGraphData(sessionId, addType);
    }
	
	@RequestMapping("/sessions/stack")
    public String getStackData(Long sessionId) {
		return sessionService.getStackData(sessionId);
    }

	@RequestMapping("/sessions/interPathEdges")
    public String getInterPathEdges(Long sessionId) {
		return sessionService.getInterPathEdges(sessionId);
    }
	
	@RequestMapping("/sessions/countElements")
    public int countElements(Long sessionId) {
		return sessionService.countElements(sessionId);
    }

	@RequestMapping("/sessions/startingMethods")
    public Iterable<Method> getStartingMethods(Long sessionId) {
		return methodService.startingMethodsBySessionId(sessionId);
    }
	
	@RequestMapping("/sessions/endingMethods")
    public Iterable<Method> getEndingMethods(Long sessionId) {
		return methodService.endingMethodsBySessionId(sessionId);
    }
	
	@PostMapping("/sessions")
	public Session newSession(@RequestBody Session session) {
		return sessionService.save(session);
	}
	
	@RequestMapping("/sessions/all")
	public Iterable<Session> allSessions() {
		return sessionService.allSessions();
	}
	
	@PutMapping("/sessions/{id}") //The date format is yyyy-MM-dd'T'HH:mm:ss'Z'
	public Session updateSession(@PathVariable Long id , @RequestBody Session session) {
		session.setId(id);
		return sessionService.save(session);
	}
}
