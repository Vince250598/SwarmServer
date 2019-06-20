package swarm.server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import swarm.server.domains.Namespace;

@RepositoryRestResource(collectionResourceRel = "namespaces", path = "namespaces")
public interface NamespaceRepository extends JpaRepository<Namespace, Long> {

	Optional<Namespace> findByFullPath(@Param("fullPath") String fullPath);

}