package swarm.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import swarm.server.domains.Namespace;
import swarm.server.repositories.NamespaceRepository;

@Service
@GraphQLApi
public class NamespaceService {

	private final NamespaceRepository  namespaceRepository;
	
	@Autowired
	public NamespaceService(NamespaceRepository namespaceRepository) {
		this.namespaceRepository = namespaceRepository;
	}
	
	public Namespace save(Namespace namespace) {
		return namespaceRepository.save(namespace);
	}

	@GraphQLMutation(name = "namespaceCreate", description = "create a new namespace")
	public Namespace namespaceCreate(Namespace namespace) {
		return namespaceRepository.save(namespace);
	}
	
	@GraphQLQuery(name = "namespace", description= "find a namespace by its ID")
	public Optional<Namespace> namespace(@GraphQLArgument(name = "id") Long id) {
		return namespaceRepository.findById(id);
	}
	
	@GraphQLQuery(name = "namespace", description = "find a namespace by its fullPath")
	public Optional<Namespace> namespace(@GraphQLArgument(name = "fullPath") String fullPath) {
		return namespaceRepository.findByFullPath(fullPath);
	}
	
}
