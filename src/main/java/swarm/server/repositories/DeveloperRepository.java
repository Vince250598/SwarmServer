package swarm.server.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import swarm.server.domains.Developer;

@RepositoryRestResource(collectionResourceRel = "developers", path = "developers")
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
	Optional<Developer> findById(@Param("id") Long id);
	
	Developer findByUsernameAllIgnoringCase(@Param("username") String username);

	Page<Developer> findByUsername(@Param("username") String username, Pageable pageable);
}