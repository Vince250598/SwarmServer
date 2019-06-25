package swarm.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import swarm.server.domains.Event;
import swarm.server.repositories.EventRepository;

@Service
@GraphQLApi
public class EventService {
	
	private final EventRepository eventRepository;
	
	@Autowired
	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	public Event save(Event event) {
		return eventRepository.save(event);
	}
	
	@GraphQLMutation(name = "eventCreate")
	public Event createEvent(Event event) {
		return eventRepository.save(event);
	}

}
