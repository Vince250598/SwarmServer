package org.swarmdebugging.server.web.rest;

import org.swarmdebugging.server.SwarmServerApp;

import org.swarmdebugging.server.domain.PathNode;
import org.swarmdebugging.server.repository.PathNodeRepository;
import org.swarmdebugging.server.repository.search.PathNodeSearchRepository;
import org.swarmdebugging.server.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.swarmdebugging.server.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PathNodeResource REST controller.
 *
 * @see PathNodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SwarmServerApp.class)
public class PathNodeResourceIntTest {

    private static final String DEFAULT_NAMESPACE = "AAAAAAAAAA";
    private static final String UPDATED_NAMESPACE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_METHOD = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PathNodeRepository pathNodeRepository;

    @Autowired
    private PathNodeSearchRepository pathNodeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPathNodeMockMvc;

    private PathNode pathNode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PathNodeResource pathNodeResource = new PathNodeResource(pathNodeRepository, pathNodeSearchRepository);
        this.restPathNodeMockMvc = MockMvcBuilders.standaloneSetup(pathNodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PathNode createEntity(EntityManager em) {
        PathNode pathNode = new PathNode()
            .namespace(DEFAULT_NAMESPACE)
            .type(DEFAULT_TYPE)
            .method(DEFAULT_METHOD)
            .created(DEFAULT_CREATED);
        return pathNode;
    }

    @Before
    public void initTest() {
        pathNodeSearchRepository.deleteAll();
        pathNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPathNode() throws Exception {
        int databaseSizeBeforeCreate = pathNodeRepository.findAll().size();

        // Create the PathNode
        restPathNodeMockMvc.perform(post("/api/path-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathNode)))
            .andExpect(status().isCreated());

        // Validate the PathNode in the database
        List<PathNode> pathNodeList = pathNodeRepository.findAll();
        assertThat(pathNodeList).hasSize(databaseSizeBeforeCreate + 1);
        PathNode testPathNode = pathNodeList.get(pathNodeList.size() - 1);
        assertThat(testPathNode.getNamespace()).isEqualTo(DEFAULT_NAMESPACE);
        assertThat(testPathNode.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPathNode.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testPathNode.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the PathNode in Elasticsearch
        PathNode pathNodeEs = pathNodeSearchRepository.findOne(testPathNode.getId());
        assertThat(pathNodeEs).isEqualToComparingFieldByField(testPathNode);
    }

    @Test
    @Transactional
    public void createPathNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pathNodeRepository.findAll().size();

        // Create the PathNode with an existing ID
        pathNode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPathNodeMockMvc.perform(post("/api/path-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathNode)))
            .andExpect(status().isBadRequest());

        // Validate the PathNode in the database
        List<PathNode> pathNodeList = pathNodeRepository.findAll();
        assertThat(pathNodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPathNodes() throws Exception {
        // Initialize the database
        pathNodeRepository.saveAndFlush(pathNode);

        // Get all the pathNodeList
        restPathNodeMockMvc.perform(get("/api/path-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pathNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].namespace").value(hasItem(DEFAULT_NAMESPACE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getPathNode() throws Exception {
        // Initialize the database
        pathNodeRepository.saveAndFlush(pathNode);

        // Get the pathNode
        restPathNodeMockMvc.perform(get("/api/path-nodes/{id}", pathNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pathNode.getId().intValue()))
            .andExpect(jsonPath("$.namespace").value(DEFAULT_NAMESPACE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingPathNode() throws Exception {
        // Get the pathNode
        restPathNodeMockMvc.perform(get("/api/path-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePathNode() throws Exception {
        // Initialize the database
        pathNodeRepository.saveAndFlush(pathNode);
        pathNodeSearchRepository.save(pathNode);
        int databaseSizeBeforeUpdate = pathNodeRepository.findAll().size();

        // Update the pathNode
        PathNode updatedPathNode = pathNodeRepository.findOne(pathNode.getId());
        updatedPathNode
            .namespace(UPDATED_NAMESPACE)
            .type(UPDATED_TYPE)
            .method(UPDATED_METHOD)
            .created(UPDATED_CREATED);

        restPathNodeMockMvc.perform(put("/api/path-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPathNode)))
            .andExpect(status().isOk());

        // Validate the PathNode in the database
        List<PathNode> pathNodeList = pathNodeRepository.findAll();
        assertThat(pathNodeList).hasSize(databaseSizeBeforeUpdate);
        PathNode testPathNode = pathNodeList.get(pathNodeList.size() - 1);
        assertThat(testPathNode.getNamespace()).isEqualTo(UPDATED_NAMESPACE);
        assertThat(testPathNode.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPathNode.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testPathNode.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the PathNode in Elasticsearch
        PathNode pathNodeEs = pathNodeSearchRepository.findOne(testPathNode.getId());
        assertThat(pathNodeEs).isEqualToComparingFieldByField(testPathNode);
    }

    @Test
    @Transactional
    public void updateNonExistingPathNode() throws Exception {
        int databaseSizeBeforeUpdate = pathNodeRepository.findAll().size();

        // Create the PathNode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPathNodeMockMvc.perform(put("/api/path-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathNode)))
            .andExpect(status().isCreated());

        // Validate the PathNode in the database
        List<PathNode> pathNodeList = pathNodeRepository.findAll();
        assertThat(pathNodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePathNode() throws Exception {
        // Initialize the database
        pathNodeRepository.saveAndFlush(pathNode);
        pathNodeSearchRepository.save(pathNode);
        int databaseSizeBeforeDelete = pathNodeRepository.findAll().size();

        // Get the pathNode
        restPathNodeMockMvc.perform(delete("/api/path-nodes/{id}", pathNode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pathNodeExistsInEs = pathNodeSearchRepository.exists(pathNode.getId());
        assertThat(pathNodeExistsInEs).isFalse();

        // Validate the database is empty
        List<PathNode> pathNodeList = pathNodeRepository.findAll();
        assertThat(pathNodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPathNode() throws Exception {
        // Initialize the database
        pathNodeRepository.saveAndFlush(pathNode);
        pathNodeSearchRepository.save(pathNode);

        // Search the pathNode
        restPathNodeMockMvc.perform(get("/api/_search/path-nodes?query=id:" + pathNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pathNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].namespace").value(hasItem(DEFAULT_NAMESPACE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PathNode.class);
        PathNode pathNode1 = new PathNode();
        pathNode1.setId(1L);
        PathNode pathNode2 = new PathNode();
        pathNode2.setId(pathNode1.getId());
        assertThat(pathNode1).isEqualTo(pathNode2);
        pathNode2.setId(2L);
        assertThat(pathNode1).isNotEqualTo(pathNode2);
        pathNode1.setId(null);
        assertThat(pathNode1).isNotEqualTo(pathNode2);
    }
}
