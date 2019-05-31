package swarm.server.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swarm.server.domains.Artefact;
import swarm.server.domains.Type;
import swarm.server.domains.TypeWrapper;
import swarm.server.services.ArtefactService;
import swarm.server.services.TypeService;

@RestController
public class TypeRestController {

	@Autowired
	private TypeService typeService;
	
	@Autowired
	private ArtefactService artefactService;
	
	@RequestMapping("/types/getBySessionId/")
    public Iterable<Type> getBySessionId(Long sessionId) {
		return typeService.typesBySessionId(sessionId);
    }
	
	@PostMapping("/types")
	public Type newType(@RequestBody TypeWrapper typeWrapper) {
		
		String source = typeWrapper.getSource();
		Type type = typeWrapper.getType();
		
		int typeHash = type.hashCode(source);
		
		Artefact artefact = artefactService.artefactByTypeHash(typeHash);
		
		if (artefact == null) {
			artefact = new Artefact(source);
			artefact.setTypeHash(typeHash);
		}
		
		artefactService.save(artefact);
		type.setArtefact(artefact);
		
		return typeService.save(type);	
	}
	
	@RequestMapping("/types/{id}")
	public Optional<Type> typeById(@PathVariable Long id) {
		return typeService.typeById(id);
	}
	
}