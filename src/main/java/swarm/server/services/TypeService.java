package swarm.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Type;
import swarm.server.repositories.TypeRepository;

@Service
@GraphQLApi
public class TypeService {

	private final TypeRepository typeRepository;
	
	@Autowired
	public TypeService(TypeRepository typeRepository) {
		this.typeRepository = typeRepository;
	}
	
	@GraphQLQuery
	public Iterable<Type> typesBySessionId(@GraphQLArgument(name = "sessionId") Long sessionId){
    	return typeRepository.findBySessionId(sessionId);
    }
}
