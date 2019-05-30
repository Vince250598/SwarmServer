package swarm.server.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import swarm.server.domains.Artefact;

public interface ArtefactRepository extends JpaRepository<Artefact, Long> {

	Artefact findBySourceCode(String sourceCode); //maybe change for hashcode
	
	Artefact findByTypeHash(int typeHash);
	
}
