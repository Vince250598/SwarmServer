package swarm.server.services;

import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Developer;
import swarm.server.repositories.DeveloperRepository;

@Service
@GraphQLApi
public class DeveloperService {

	private DeveloperRepository developerRepository; 

	public DeveloperService(DeveloperRepository developerRepository) {
		this.developerRepository = developerRepository;
	}
	
	@GraphQLQuery
	public Developer login(@GraphQLArgument(name = "name") String name) {
		return developerRepository.findByNameAllIgnoringCase(name);
	}
	
	@GraphQLQuery
	public Iterable<Developer> allDevelopers() {
		return developerRepository.findAll();
	}
}
