package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Event;
import swarm.server.services.EventService;

@RestController
public class EventRestController {
	
	@Autowired
	private EventService eventService;
	
	@PostMapping("/events")
	public Event newEvent(@RequestBody Event event) {
		return eventService.save(event);
	}
}
