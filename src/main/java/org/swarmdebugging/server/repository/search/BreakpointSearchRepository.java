package org.swarmdebugging.server.repository.search;

import org.swarmdebugging.server.domain.Breakpoint;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Breakpoint entity.
 */
public interface BreakpointSearchRepository extends ElasticsearchRepository<Breakpoint, Long> {
}
