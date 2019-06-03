package swarm.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import swarm.server.domains.Method;
import swarm.server.domains.Session;
import swarm.server.domains.Type;
import swarm.server.repositories.MethodRepository;
import swarm.server.repositories.SessionRepository;

@Service
@GraphQLApi
public class MethodService {

	private final MethodRepository methodRepository; 
	private final SessionRepository sessionRepository;
	
	@Autowired
	public MethodService(MethodRepository methodRepository, SessionRepository sessionRepository) {
		this.methodRepository = methodRepository;
		this.sessionRepository = sessionRepository;
	}
	
	public Optional<Method> methodById(Long id) {
		return methodRepository.findById(id);
	}
	
	public Method save(Method method) {
		return methodRepository.save(method);
	}
	
	@GraphQLMutation
	public Method createMethod(@GraphQLArgument(name = "type") Type type, @GraphQLArgument(name = "key") String key, 
			@GraphQLArgument(name = "name") String name, @GraphQLArgument(name = "signature") String signature) {
		return methodRepository.save(new Method(type, key, name, signature));
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
