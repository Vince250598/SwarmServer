package org.swarmdebugging.server.repository.search;

import org.swarmdebugging.server.domain.PathNode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PathNode entity.
 */
public interface PathNodeSearchRepository extends ElasticsearchRepository<PathNode, Long> {
}
