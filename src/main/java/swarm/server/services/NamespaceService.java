package swarm.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
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
	
	public Optional<Namespace> namespaceById(Long id) {
		return namespaceRepository.findById(id);
	}

	@GraphQLQuery
	public Namespace namespaceByFullPath(@GraphQLArgument(name = "fullPath") String fullPath) {
		return namespaceRepository.findByFullPath(fullPath);
	} 

	@GraphQLMutation
	public Namespace createNamespace(@GraphQLArgument(name = "name") String name, @GraphQLArgument(name = "fullPath") String fullPath) {
		return namespaceRepository.save(new Namespace(name, fullPath));
	}
}
