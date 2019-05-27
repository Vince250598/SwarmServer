package swarm.server.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Method;
import swarm.server.domains.Session;
import swarm.server.repositories.MethodRepository;
import swarm.server.repositories.SessionRepository;

@Service
@GraphQLApi
public class MethodService {

	private MethodRepository methodRepository; 
	private SessionRepository sessionRepository;
	
	public MethodService(MethodRepository methodRepository, SessionRepository sessionRepository) {
		this.methodRepository = methodRepository;
		this.sessionRepository = sessionRepository;
	}

	@GraphQLQuery
	public Iterable<Method> methodByTypeId(@GraphQLArgument(name = "typeId") Long typeId) {
		return methodRepository.findByTypeId(typeId);
	}
	
	@GraphQLQuery
    public Iterable<Method> startingMethodsBySessionId(@GraphQLArgument(name = "sessionId") Long sessionId){
    	Optional<Session> session = sessionRepository.findById(sessionId);
    	return methodRepository.getStartingMethods(session);
    }
    
	@GraphQLQuery
    public Iterable<Method> endingMethodsBySessionId(@GraphQLArgument(name = "sessionId") Long sessionId){
    	Optional<Session> session = sessionRepository.findById(sessionId);
    	return methodRepository.getEndingMethods(session);
    }
}
