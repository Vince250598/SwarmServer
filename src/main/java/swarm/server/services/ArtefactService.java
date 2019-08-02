package swarm.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import swarm.server.domains.Artefact;
import swarm.server.repositories.ArtefactRepository;

@GraphQLApi
@Service
public class ArtefactService {
	
	private final ArtefactRepository artefactRepository;
	
	@Autowired
	public ArtefactService(ArtefactRepository artefactRepository) {
		this.artefactRepository = artefactRepository;
	}
	
	@GraphQLQuery(name = "artefact")
	public Optional<Artefact> artefactById(@GraphQLArgument(name = "Id") Long Id) {
		return artefactRepository.findById(Id);
	}

	@GraphQLQuery(name = "artefacts")
	public Iterable<Artefact> allArtefacts() {
		return artefactRepository.findAll();
	}
}
