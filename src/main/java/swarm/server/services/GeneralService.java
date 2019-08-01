package swarm.server.services;

import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class GeneralService {
	
	@GraphQLQuery(name = "health")
	public String health() {
		return "UP";
	}
}