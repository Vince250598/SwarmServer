package swarm.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import swarm.server.domains.Breakpoint;
import swarm.server.domains.Product;
import swarm.server.domains.Type;
import swarm.server.repositories.BreakpointRepository;
import swarm.server.repositories.ProductRepository;

@Service
@GraphQLApi
public class BreakpointService {
	
	private final BreakpointRepository breakpointRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public BreakpointService(BreakpointRepository breakpointRepo, ProductRepository productRepo) {
		this.breakpointRepository = breakpointRepo;
		this.productRepository = productRepo;
	}
	
	public Breakpoint save(Breakpoint breakpoint) {
		return breakpointRepository.save(breakpoint);
	}
	
	public Optional<Breakpoint> findById(Long id) {
		return breakpointRepository.findById(id);
	}
	
	@GraphQLMutation
	public Breakpoint createBreakpoint(@GraphQLArgument(name = "type") Type type, @GraphQLArgument(name = "charStart") String charStart, 
			@GraphQLArgument(name = "charEnd") String charEnd, @GraphQLArgument(name = "lineNumber") Integer lineNumber) {
		return breakpointRepository.save(new Breakpoint(type, charStart, charEnd, lineNumber));
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
