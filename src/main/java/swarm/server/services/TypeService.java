package swarm.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import swarm.server.domains.Artefact;
import swarm.server.domains.Namespace;
import swarm.server.domains.Session;
import swarm.server.domains.Type;
import swarm.server.domains.TypeWrapper;
import swarm.server.repositories.ArtefactRepository;
import swarm.server.repositories.TypeRepository;

@Service
@GraphQLApi
public class TypeService {

	private final TypeRepository typeRepository;
	private final ArtefactRepository artefactRepository;
	
	@Autowired
	public TypeService(TypeRepository typeRepository, ArtefactRepository artefactRepository) {
		this.typeRepository = typeRepository;
		this.artefactRepository = artefactRepository;
	}
	
	public Type typeByMethodId(Long methodId) {
		return typeRepository.findByMethodId(methodId);
	}
	
	public Optional<Type> typeById(Long id) {
		return typeRepository.findById(id);
	}
	
	public Type createTypeWithRest(TypeWrapper typeWrapper) {
		
		String source = typeWrapper.getSource();
		Type type = typeWrapper.getType();
		
		int typeHash = typeWrapper.hashCode();
		
		Artefact artefact = artefactRepository.findByTypeHash(typeHash);
		
		if(artefact == null) {
			artefact = new Artefact(source);
			artefact.setTypeHash(typeHash);
		}
		
		artefactRepository.save(artefact);
		type.setArtefact(artefact);
		
		return typeRepository.save(type);
	}
	
	@GraphQLMutation
	public Type createType(@GraphQLArgument(name = "namespace") Namespace namespace, @GraphQLArgument(name = "session") Session session, 
			@GraphQLArgument(name = "fullName") String fullName, @GraphQLArgument(name = "fullPath") String fullPath, 
			@GraphQLArgument(name = "name") String name, @GraphQLArgument(name = "source") String sourceCode) {
		
		Type type = new Type();
		type.setNamespace(namespace);
		type.setSession(session);
		type.setFullName(fullName);
		type.setFullPath(fullPath);
		type.setName(name);
		
		TypeWrapper typeWrapper = new TypeWrapper(type, sourceCode);
		
		int typeHash = typeWrapper.hashCode();
		
		Artefact artefact = artefactRepository.findByTypeHash(typeHash);
		
		if (artefact == null) {
			artefact = new Artefact(sourceCode);
			artefact.setTypeHash(typeHash);
		}
		
		artefactRepository.save(artefact);
		type.setArtefact(artefact);
		
		return typeRepository.save(type);
	}
	
	@GraphQLQuery
	public Iterable<Type> typesBySessionId(@GraphQLArgument(name = "sessionId") Long sessionId){
    	return typeRepository.findBySessionId(sessionId);
    }
}
