package swarm.server.services;

import org.springframework.stereotype.Service;


import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.repositories.EventRepository;

@GraphQLApi
@Service
public class EventService {
	
	private EventRepository eventRepository;
	
	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

}
