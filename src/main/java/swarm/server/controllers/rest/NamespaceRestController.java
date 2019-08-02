package swarm.server.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Namespace;
import swarm.server.services.NamespaceService;

@RestController
public class NamespaceRestController {

	@Autowired
	private NamespaceService namespaceService;
	
	@RequestMapping("/namespaces/findByFullPath")
    public Optional<Namespace> findbyFullPath(String fullPath) {
		return namespaceService.namespace(fullPath);
    }	
	
	@PostMapping("/namespaces")
	public Namespace newNamespace(@RequestBody Namespace namespace) {
		return namespaceService.save(namespace);
	}
	
	@RequestMapping("/namespaces/{id}")
	public Optional<Namespace> namespaceById(@PathVariable Long id) {
		return namespaceService.namespace(id);
	}
}
