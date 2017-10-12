package org.swarmdebugging.server.repository;

import org.swarmdebugging.server.domain.PathNode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PathNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathNodeRepository extends JpaRepository<PathNode, Long> {

}
