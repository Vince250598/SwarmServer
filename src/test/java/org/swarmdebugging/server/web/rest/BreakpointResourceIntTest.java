package org.swarmdebugging.server.web.rest;

import org.swarmdebugging.server.SwarmServerApp;

import org.swarmdebugging.server.domain.Breakpoint;
import org.swarmdebugging.server.repository.BreakpointRepository;
import org.swarmdebugging.server.repository.search.BreakpointSearchRepository;
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

import org.swarmdebugging.server.domain.enumeration.BreakpointKind;
/**
 * Test class for the BreakpointResource REST controller.
 *
 * @see BreakpointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SwarmServerApp.class)
public class BreakpointResourceIntTest {

    private static final BreakpointKind DEFAULT_KIND = BreakpointKind.LINE;
    private static final BreakpointKind UPDATED_KIND = BreakpointKind.CONDITIONAL;

    private static final String DEFAULT_NAMESPACE = "AAAAAAAAAA";
    private static final String UPDATED_NAMESPACE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LINE_NUMBER = 1;
    private static final Integer UPDATED_LINE_NUMBER = 2;

    private static final String DEFAULT_LINE_OF_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LINE_OF_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BreakpointRepository breakpointRepository;

    @Autowired
    private BreakpointSearchRepository breakpointSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBreakpointMockMvc;

    private Breakpoint breakpoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BreakpointResource breakpointResource = new BreakpointResource(breakpointRepository, breakpointSearchRepository);
        this.restBreakpointMockMvc = MockMvcBuilders.standaloneSetup(breakpointResource)
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
    public static Breakpoint createEntity(EntityManager em) {
        Breakpoint breakpoint = new Breakpoint()
            .kind(DEFAULT_KIND)
            .namespace(DEFAULT_NAMESPACE)
            .type(DEFAULT_TYPE)
            .lineNumber(DEFAULT_LINE_NUMBER)
            .lineOfCode(DEFAULT_LINE_OF_CODE)
            .created(DEFAULT_CREATED);
        return breakpoint;
    }

    @Before
    public void initTest() {
        breakpointSearchRepository.deleteAll();
        breakpoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createBreakpoint() throws Exception {
        int databaseSizeBeforeCreate = breakpointRepository.findAll().size();

        // Create the Breakpoint
        restBreakpointMockMvc.perform(post("/api/breakpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breakpoint)))
            .andExpect(status().isCreated());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeCreate + 1);
        Breakpoint testBreakpoint = breakpointList.get(breakpointList.size() - 1);
        assertThat(testBreakpoint.getKind()).isEqualTo(DEFAULT_KIND);
        assertThat(testBreakpoint.getNamespace()).isEqualTo(DEFAULT_NAMESPACE);
        assertThat(testBreakpoint.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBreakpoint.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testBreakpoint.getLineOfCode()).isEqualTo(DEFAULT_LINE_OF_CODE);
        assertThat(testBreakpoint.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the Breakpoint in Elasticsearch
        Breakpoint breakpointEs = breakpointSearchRepository.findOne(testBreakpoint.getId());
        assertThat(breakpointEs).isEqualToComparingFieldByField(testBreakpoint);
    }

    @Test
    @Transactional
    public void createBreakpointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = breakpointRepository.findAll().size();

        // Create the Breakpoint with an existing ID
        breakpoint.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreakpointMockMvc.perform(post("/api/breakpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breakpoint)))
            .andExpect(status().isBadRequest());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBreakpoints() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList
        restBreakpointMockMvc.perform(get("/api/breakpoints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breakpoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].namespace").value(hasItem(DEFAULT_NAMESPACE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].lineOfCode").value(hasItem(DEFAULT_LINE_OF_CODE.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getBreakpoint() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get the breakpoint
        restBreakpointMockMvc.perform(get("/api/breakpoints/{id}", breakpoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(breakpoint.getId().intValue()))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND.toString()))
            .andExpect(jsonPath("$.namespace").value(DEFAULT_NAMESPACE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER))
            .andExpect(jsonPath("$.lineOfCode").value(DEFAULT_LINE_OF_CODE.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingBreakpoint() throws Exception {
        // Get the breakpoint
        restBreakpointMockMvc.perform(get("/api/breakpoints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBreakpoint() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);
        breakpointSearchRepository.save(breakpoint);
        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();

        // Update the breakpoint
        Breakpoint updatedBreakpoint = breakpointRepository.findOne(breakpoint.getId());
        updatedBreakpoint
            .kind(UPDATED_KIND)
            .namespace(UPDATED_NAMESPACE)
            .type(UPDATED_TYPE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .lineOfCode(UPDATED_LINE_OF_CODE)
            .created(UPDATED_CREATED);

        restBreakpointMockMvc.perform(put("/api/breakpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBreakpoint)))
            .andExpect(status().isOk());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
        Breakpoint testBreakpoint = breakpointList.get(breakpointList.size() - 1);
        assertThat(testBreakpoint.getKind()).isEqualTo(UPDATED_KIND);
        assertThat(testBreakpoint.getNamespace()).isEqualTo(UPDATED_NAMESPACE);
        assertThat(testBreakpoint.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBreakpoint.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testBreakpoint.getLineOfCode()).isEqualTo(UPDATED_LINE_OF_CODE);
        assertThat(testBreakpoint.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the Breakpoint in Elasticsearch
        Breakpoint breakpointEs = breakpointSearchRepository.findOne(testBreakpoint.getId());
        assertThat(breakpointEs).isEqualToComparingFieldByField(testBreakpoint);
    }

    @Test
    @Transactional
    public void updateNonExistingBreakpoint() throws Exception {
        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();

        // Create the Breakpoint

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBreakpointMockMvc.perform(put("/api/breakpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breakpoint)))
            .andExpect(status().isCreated());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBreakpoint() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);
        breakpointSearchRepository.save(breakpoint);
        int databaseSizeBeforeDelete = breakpointRepository.findAll().size();

        // Get the breakpoint
        restBreakpointMockMvc.perform(delete("/api/breakpoints/{id}", breakpoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean breakpointExistsInEs = breakpointSearchRepository.exists(breakpoint.getId());
        assertThat(breakpointExistsInEs).isFalse();

        // Validate the database is empty
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBreakpoint() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);
        breakpointSearchRepository.save(breakpoint);

        // Search the breakpoint
        restBreakpointMockMvc.perform(get("/api/_search/breakpoints?query=id:" + breakpoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breakpoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].namespace").value(hasItem(DEFAULT_NAMESPACE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].lineOfCode").value(hasItem(DEFAULT_LINE_OF_CODE.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Breakpoint.class);
        Breakpoint breakpoint1 = new Breakpoint();
        breakpoint1.setId(1L);
        Breakpoint breakpoint2 = new Breakpoint();
        breakpoint2.setId(breakpoint1.getId());
        assertThat(breakpoint1).isEqualTo(breakpoint2);
        breakpoint2.setId(2L);
        assertThat(breakpoint1).isNotEqualTo(breakpoint2);
        breakpoint1.setId(null);
        assertThat(breakpoint1).isNotEqualTo(breakpoint2);
    }
}
