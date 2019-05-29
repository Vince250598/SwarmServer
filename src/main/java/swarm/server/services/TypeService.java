package swarm.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Artefact;
import swarm.server.domains.Namespace;
import swarm.server.domains.Session;
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
	
	@GraphQLMutation
	public Type createType(@GraphQLArgument(name = "namespace") Namespace namespace, @GraphQLArgument(name = "session") Session session, 
			@GraphQLArgument(name = "fullName") String fullName, @GraphQLArgument(name = "fullPath") String fullPath, 
			@GraphQLArgument(name = "name") String name, @GraphQLArgument(name = "artefact") Artefact artefact) {
		return typeRepository.save(new Type(namespace, session, fullName, fullPath, name, artefact));
	}
	
	@GraphQLQuery
	public Iterable<Type> typesBySessionId(@GraphQLArgument(name = "sessionId") Long sessionId){
    	return typeRepository.findBySessionId(sessionId);
    }
}
