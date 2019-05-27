package swarm.server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Namespace;
import swarm.server.services.NamespaceService;

@RestController
public class NamespaceRestController {

	@Autowired
	private NamespaceService service;
	
	@RequestMapping("/namespaces/findByFullPath/{fullPath}")
    public Namespace findbyFullPath(@PathVariable String fullPath) {
		return service.namespaceByFullPath(fullPath);
    }	
}
