package swarm.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Breakpoint;
import swarm.server.domains.Product;
import swarm.server.repositories.BreakpointRepository;
import swarm.server.repositories.ProductRepository;

@Service
@GraphQLApi
public class BreakpointService {
	
	private BreakpointRepository breakpointRepository;
	private ProductRepository productRepository;
	
	public BreakpointService(BreakpointRepository breakpointRepo, ProductRepository productRepo) {
		this.breakpointRepository = breakpointRepo;
		this.productRepository = productRepo;
	}
	
	@GraphQLQuery
    public Iterable<Breakpoint> allBreakpoints() {
        return breakpointRepository.findAll();
    }
    
	@GraphQLQuery
    public Iterable<Breakpoint> breakpointsByTaskId(@GraphQLArgument(name = "taskId") Long taskId){
		return breakpointRepository.findByTaskId(taskId);
    }
    
	@GraphQLQuery(name = "getTable")
    public String getTable(@GraphQLArgument(name = "productId") Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		List<Breakpoint> breakpoints = breakpointRepository.findByProduct(product);

		StringBuffer buffer = new StringBuffer("{");
		long total = breakpoints.size();

		buffer.append("\"draw\": 1,\n");
		buffer.append("\"recordsTotal\": " + total + ",\n");
		buffer.append("\"recordsFiltered\": " + total + ",\n");
		buffer.append("\"data\": [\n");

		for (Breakpoint breakpoint : breakpoints) {
			buffer.append("[\"" + breakpoint.getType().getSession().getTask().getTitle().substring(0, 11) + "\",\n");
			buffer.append("\"" + breakpoint.getType().getSession().getDeveloper().getName() + "\",\n");
			buffer.append("\"" + breakpoint.getType().getFullName() + "\",\n");
			buffer.append("\"" + breakpoint.getLineNumber() + "\"],\n");
		}

		String output = buffer.toString().substring(0, buffer.toString().length() - 2);
		output = output + "\n]}";
        return output;
	}
	
}
