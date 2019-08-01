package swarm.server.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Breakpoint;
import swarm.server.services.BreakpointService;

@RestController
public class BreakpointRestController {
	
	@Autowired
	private BreakpointService breakpointService;
	
	@PostMapping("/breakpoints")
	public Breakpoint newBreakpoint(@RequestBody Breakpoint breakpoint) {
		return breakpointService.breakpointCreate(breakpoint);
	}
	
	@RequestMapping("/breakpoints/{id}")
	public Optional<Breakpoint> getBreakpoint(@PathVariable Long id) {
		return breakpointService.findById(id);
	}

	@RequestMapping("/taskBreakpoints/{id}")
	public Iterable<Breakpoint> getBreakpointByTaskId(@PathVariable Long id) {
		return breakpointService.breakpointsByTaskId(id);
	}
}
