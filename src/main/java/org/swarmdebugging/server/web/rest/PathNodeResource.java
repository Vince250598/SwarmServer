package org.swarmdebugging.server.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.swarmdebugging.server.domain.PathNode;

import org.swarmdebugging.server.repository.PathNodeRepository;
import org.swarmdebugging.server.repository.search.PathNodeSearchRepository;
import org.swarmdebugging.server.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PathNode.
 */
@RestController
@RequestMapping("/api")
public class PathNodeResource {

    private final Logger log = LoggerFactory.getLogger(PathNodeResource.class);

    private static final String ENTITY_NAME = "pathNode";

    private final PathNodeRepository pathNodeRepository;

    private final PathNodeSearchRepository pathNodeSearchRepository;

    public PathNodeResource(PathNodeRepository pathNodeRepository, PathNodeSearchRepository pathNodeSearchRepository) {
        this.pathNodeRepository = pathNodeRepository;
        this.pathNodeSearchRepository = pathNodeSearchRepository;
    }

    /**
     * POST  /path-nodes : Create a new pathNode.
     *
     * @param pathNode the pathNode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pathNode, or with status 400 (Bad Request) if the pathNode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/path-nodes")
    @Timed
    public ResponseEntity<PathNode> createPathNode(@RequestBody PathNode pathNode) throws URISyntaxException {
        log.debug("REST request to save PathNode : {}", pathNode);
        if (pathNode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pathNode cannot already have an ID")).body(null);
        }
        PathNode result = pathNodeRepository.save(pathNode);
        pathNodeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/path-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /path-nodes : Updates an existing pathNode.
     *
     * @param pathNode the pathNode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pathNode,
     * or with status 400 (Bad Request) if the pathNode is not valid,
     * or with status 500 (Internal Server Error) if the pathNode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/path-nodes")
    @Timed
    public ResponseEntity<PathNode> updatePathNode(@RequestBody PathNode pathNode) throws URISyntaxException {
        log.debug("REST request to update PathNode : {}", pathNode);
        if (pathNode.getId() == null) {
            return createPathNode(pathNode);
        }
        PathNode result = pathNodeRepository.save(pathNode);
        pathNodeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pathNode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /path-nodes : get all the pathNodes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of pathNodes in body
     */
    @GetMapping("/path-nodes")
    @Timed
    public List<PathNode> getAllPathNodes(@RequestParam(required = false) String filter) {
        if ("destination-is-null".equals(filter)) {
            log.debug("REST request to get all PathNodes where destination is null");
            return StreamSupport
                .stream(pathNodeRepository.findAll().spliterator(), false)
                .filter(pathNode -> pathNode.getDestination() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PathNodes");
        return pathNodeRepository.findAll();
        }

    /**
     * GET  /path-nodes/:id : get the "id" pathNode.
     *
     * @param id the id of the pathNode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pathNode, or with status 404 (Not Found)
     */
    @GetMapping("/path-nodes/{id}")
    @Timed
    public ResponseEntity<PathNode> getPathNode(@PathVariable Long id) {
        log.debug("REST request to get PathNode : {}", id);
        PathNode pathNode = pathNodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pathNode));
    }

    /**
     * DELETE  /path-nodes/:id : delete the "id" pathNode.
     *
     * @param id the id of the pathNode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/path-nodes/{id}")
    @Timed
    public ResponseEntity<Void> deletePathNode(@PathVariable Long id) {
        log.debug("REST request to delete PathNode : {}", id);
        pathNodeRepository.delete(id);
        pathNodeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/path-nodes?query=:query : search for the pathNode corresponding
     * to the query.
     *
     * @param query the query of the pathNode search
     * @return the result of the search
     */
    @GetMapping("/_search/path-nodes")
    @Timed
    public List<PathNode> searchPathNodes(@RequestParam String query) {
        log.debug("REST request to search PathNodes for query {}", query);
        return StreamSupport
            .stream(pathNodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
