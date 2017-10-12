package org.swarmdebugging.server.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.swarmdebugging.server.domain.Breakpoint;

import org.swarmdebugging.server.repository.BreakpointRepository;
import org.swarmdebugging.server.repository.search.BreakpointSearchRepository;
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
 * REST controller for managing Breakpoint.
 */
@RestController
@RequestMapping("/api")
public class BreakpointResource {

    private final Logger log = LoggerFactory.getLogger(BreakpointResource.class);

    private static final String ENTITY_NAME = "breakpoint";

    private final BreakpointRepository breakpointRepository;

    private final BreakpointSearchRepository breakpointSearchRepository;

    public BreakpointResource(BreakpointRepository breakpointRepository, BreakpointSearchRepository breakpointSearchRepository) {
        this.breakpointRepository = breakpointRepository;
        this.breakpointSearchRepository = breakpointSearchRepository;
    }

    /**
     * POST  /breakpoints : Create a new breakpoint.
     *
     * @param breakpoint the breakpoint to create
     * @return the ResponseEntity with status 201 (Created) and with body the new breakpoint, or with status 400 (Bad Request) if the breakpoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/breakpoints")
    @Timed
    public ResponseEntity<Breakpoint> createBreakpoint(@RequestBody Breakpoint breakpoint) throws URISyntaxException {
        log.debug("REST request to save Breakpoint : {}", breakpoint);
        if (breakpoint.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new breakpoint cannot already have an ID")).body(null);
        }
        Breakpoint result = breakpointRepository.save(breakpoint);
        breakpointSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/breakpoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /breakpoints : Updates an existing breakpoint.
     *
     * @param breakpoint the breakpoint to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated breakpoint,
     * or with status 400 (Bad Request) if the breakpoint is not valid,
     * or with status 500 (Internal Server Error) if the breakpoint couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/breakpoints")
    @Timed
    public ResponseEntity<Breakpoint> updateBreakpoint(@RequestBody Breakpoint breakpoint) throws URISyntaxException {
        log.debug("REST request to update Breakpoint : {}", breakpoint);
        if (breakpoint.getId() == null) {
            return createBreakpoint(breakpoint);
        }
        Breakpoint result = breakpointRepository.save(breakpoint);
        breakpointSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, breakpoint.getId().toString()))
            .body(result);
    }

    /**
     * GET  /breakpoints : get all the breakpoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of breakpoints in body
     */
    @GetMapping("/breakpoints")
    @Timed
    public List<Breakpoint> getAllBreakpoints() {
        log.debug("REST request to get all Breakpoints");
        return breakpointRepository.findAll();
        }

    /**
     * GET  /breakpoints/:id : get the "id" breakpoint.
     *
     * @param id the id of the breakpoint to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the breakpoint, or with status 404 (Not Found)
     */
    @GetMapping("/breakpoints/{id}")
    @Timed
    public ResponseEntity<Breakpoint> getBreakpoint(@PathVariable Long id) {
        log.debug("REST request to get Breakpoint : {}", id);
        Breakpoint breakpoint = breakpointRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(breakpoint));
    }

    /**
     * DELETE  /breakpoints/:id : delete the "id" breakpoint.
     *
     * @param id the id of the breakpoint to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/breakpoints/{id}")
    @Timed
    public ResponseEntity<Void> deleteBreakpoint(@PathVariable Long id) {
        log.debug("REST request to delete Breakpoint : {}", id);
        breakpointRepository.delete(id);
        breakpointSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/breakpoints?query=:query : search for the breakpoint corresponding
     * to the query.
     *
     * @param query the query of the breakpoint search
     * @return the result of the search
     */
    @GetMapping("/_search/breakpoints")
    @Timed
    public List<Breakpoint> searchBreakpoints(@RequestParam String query) {
        log.debug("REST request to search Breakpoints for query {}", query);
        return StreamSupport
            .stream(breakpointSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
