package org.swarmdebugging.server.repository.search;

import org.swarmdebugging.server.domain.Session;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Session entity.
 */
public interface SessionSearchRepository extends ElasticsearchRepository<Session, Long> {
}
