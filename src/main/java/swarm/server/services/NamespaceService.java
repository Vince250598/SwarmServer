package swarm.server.services;

import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Namespace;
import swarm.server.repositories.NamespaceRepository;

@Service
@GraphQLApi
public class NamespaceService {

	private NamespaceRepository  namespaceRepository;
	
	public NamespaceService(NamespaceRepository namespaceRepository) {
		this.namespaceRepository = namespaceRepository;
	}

	@GraphQLQuery
	public Namespace namespaceByFullPath(@GraphQLArgument(name = "fullPath") String fullPath) {
		return namespaceRepository.findByFullPath(fullPath);
	} 

}
