package swarm.server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Invocation;
import swarm.server.domains.Method;
import swarm.server.domains.Session;
import swarm.server.repositories.InvocationRepository;

@Service
@GraphQLApi
public class InvocationService {

	private final InvocationRepository invocationRepository;
	
	@Autowired
	public InvocationService(InvocationRepository invocationRepository) {
		this.invocationRepository = invocationRepository;
	}
	
	public Invocation save(Invocation invocation) {
		return invocationRepository.save(invocation);
	}
	
	@GraphQLMutation
	public Invocation createInvocation(@GraphQLArgument(name = "invoking") Method invoking, @GraphQLArgument(name = "invoked") Method invoked, 
			@GraphQLArgument(name = "session") Session session, @GraphQLArgument(name = "isVirtual") boolean isVirtual) {
		return invocationRepository.save(new Invocation(invoking, invoked, session, isVirtual));
	}

	@GraphQLQuery(name="getInvocationsByMethods")
	public Iterable<Invocation> getInvocationsByMethods(
			@GraphQLArgument(name = "sessionId") Long sessionId, 
			@GraphQLArgument(name = "invokingId") Long invokingId, 
			@GraphQLArgument(name = "invokedId") Long invokedId) {
		return invocationRepository.findByMethods(sessionId, invokingId, invokedId);
	} 
	
	
}
