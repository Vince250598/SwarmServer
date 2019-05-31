package swarm.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Event;
import swarm.server.domains.Method;
import swarm.server.domains.Session;
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
	
	@GraphQLMutation
	public Event createEvent(@GraphQLArgument(name = "method") Method method, @GraphQLArgument(name = "session") Session session, 
			@GraphQLArgument(name = "charStart") String charStart, @GraphQLArgument(name = "charEnd") String charEnd, 
			@GraphQLArgument(name = "lineNumber") Integer lineNumber, @GraphQLArgument(name = "detail") String detail, 
			@GraphQLArgument(name = "kind") String kind) {
		return eventRepository.save(new Event(method, session, charStart, charEnd, lineNumber, detail, kind));
	}

}
