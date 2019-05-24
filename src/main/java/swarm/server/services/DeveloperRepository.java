package swarm.server.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import swarm.server.domains.Developer;

@RepositoryRestResource(collectionResourceRel = "developers", path = "developers")
public interface DeveloperRepository extends PagingAndSortingRepository<Developer, Long> {
	Optional<Developer> findById(@Param("id") Long id);
	Developer findByNameAllIgnoringCase(@Param("name") String name);

	Page<Developer> findByName(@Param("name") String name, Pageable pageable);

}