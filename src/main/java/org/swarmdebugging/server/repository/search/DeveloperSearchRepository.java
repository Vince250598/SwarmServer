package org.swarmdebugging.server.repository.search;

import org.swarmdebugging.server.domain.Developer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Developer entity.
 */
public interface DeveloperSearchRepository extends ElasticsearchRepository<Developer, Long> {
}
