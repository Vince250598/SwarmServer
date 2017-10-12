package org.swarmdebugging.server.repository;

import org.swarmdebugging.server.domain.Breakpoint;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Breakpoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreakpointRepository extends JpaRepository<Breakpoint, Long> {

}
