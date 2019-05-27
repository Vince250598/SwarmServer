package swarm.server.services;


import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Invocation;
import swarm.server.repositories.InvocationRepository;

@Service
@GraphQLApi
public class InvocationService {

	private InvocationRepository invocationRepository;
	
	public InvocationService(InvocationRepository invocationRepository) {
		this.invocationRepository = invocationRepository;
	}

	@GraphQLQuery(name="getInvocationsByMethods")
	public Iterable<Invocation> getInvocationsByMethods(
			@GraphQLArgument(name = "sessionId") Long sessionId, 
			@GraphQLArgument(name = "invokingId") Long invokingId, 
			@GraphQLArgument(name = "invokedId") Long invokedId) {
		/*Gson gson = new Gson();
		return gson.toJson(invocationRepository.findByMethods(sessionId, invokingId, invokedId));*/
		return invocationRepository.findByMethods(sessionId, invokingId, invokedId);
	} 
	
	
}
