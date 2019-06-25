package swarm.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import swarm.server.domains.Developer;
import swarm.server.repositories.DeveloperRepository;

@Service
@GraphQLApi
public class DeveloperService {

	private final DeveloperRepository developerRepository; 

	@Autowired
	public DeveloperService(DeveloperRepository developerRepository) {
		this.developerRepository = developerRepository;
	}
	
	public Optional<Developer> developerById(Long id) {
		return developerRepository.findById(id);
	}
	
	@GraphQLQuery(name = "developer")
	public Developer login(@GraphQLArgument(name = "name") String name) {
		return developerRepository.findByNameAllIgnoringCase(name);
	}
	
	@GraphQLQuery(name = "developers")
	public Iterable<Developer> allDevelopers() {
		return developerRepository.findAll();
	}
	
	public Developer save(Developer developer) {
		return developerRepository.save(developer);
	}
	
	@GraphQLMutation(name = "developerCreate")
	public Developer createDeveloper(Developer developer) {
		return developerRepository.save(developer);
	}
}
