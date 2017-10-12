package org.swarmdebugging.server.web.rest;

import org.swarmdebugging.server.SwarmServerApp;

import org.swarmdebugging.server.domain.Event;
import org.swarmdebugging.server.repository.EventRepository;
import org.swarmdebugging.server.repository.search.EventSearchRepository;
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

import org.swarmdebugging.server.domain.enumeration.EventKind;
/**
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SwarmServerApp.class)
public class EventResourceIntTest {

    private static final EventKind DEFAULT_KIND = EventKind.STEP_OUT;
    private static final EventKind UPDATED_KIND = EventKind.STEP_INTO;

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAMESPACE = "AAAAAAAAAA";
    private static final String UPDATED_NAMESPACE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_FULL_PATH = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_FULL_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_METHOD_KEY = "AAAAAAAAAA";
    private static final String UPDATED_METHOD_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_METHOD_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_METHOD_SIGNATURE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHAR_STAR = 1;
    private static final Integer UPDATED_CHAR_STAR = 2;

    private static final Integer DEFAULT_CHAR_END = 1;
    private static final Integer UPDATED_CHAR_END = 2;

    private static final Integer DEFAULT_LINE_NUMBER = 1;
    private static final Integer UPDATED_LINE_NUMBER = 2;

    private static final String DEFAULT_LINE_OF_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LINE_OF_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventSearchRepository eventSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventMockMvc;

    private Event event;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventResource eventResource = new EventResource(eventRepository, eventSearchRepository);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
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
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .kind(DEFAULT_KIND)
            .detail(DEFAULT_DETAIL)
            .namespace(DEFAULT_NAMESPACE)
            .type(DEFAULT_TYPE)
            .typeFullPath(DEFAULT_TYPE_FULL_PATH)
            .method(DEFAULT_METHOD)
            .methodKey(DEFAULT_METHOD_KEY)
            .methodSignature(DEFAULT_METHOD_SIGNATURE)
            .charStar(DEFAULT_CHAR_STAR)
            .charEnd(DEFAULT_CHAR_END)
            .lineNumber(DEFAULT_LINE_NUMBER)
            .lineOfCode(DEFAULT_LINE_OF_CODE)
            .created(DEFAULT_CREATED);
        return event;
    }

    @Before
    public void initTest() {
        eventSearchRepository.deleteAll();
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getKind()).isEqualTo(DEFAULT_KIND);
        assertThat(testEvent.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testEvent.getNamespace()).isEqualTo(DEFAULT_NAMESPACE);
        assertThat(testEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvent.getTypeFullPath()).isEqualTo(DEFAULT_TYPE_FULL_PATH);
        assertThat(testEvent.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testEvent.getMethodKey()).isEqualTo(DEFAULT_METHOD_KEY);
        assertThat(testEvent.getMethodSignature()).isEqualTo(DEFAULT_METHOD_SIGNATURE);
        assertThat(testEvent.getCharStar()).isEqualTo(DEFAULT_CHAR_STAR);
        assertThat(testEvent.getCharEnd()).isEqualTo(DEFAULT_CHAR_END);
        assertThat(testEvent.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testEvent.getLineOfCode()).isEqualTo(DEFAULT_LINE_OF_CODE);
        assertThat(testEvent.getCreated()).isEqualTo(DEFAULT_CREATED);

        // Validate the Event in Elasticsearch
        Event eventEs = eventSearchRepository.findOne(testEvent.getId());
        assertThat(eventEs).isEqualToComparingFieldByField(testEvent);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].namespace").value(hasItem(DEFAULT_NAMESPACE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typeFullPath").value(hasItem(DEFAULT_TYPE_FULL_PATH.toString())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].methodKey").value(hasItem(DEFAULT_METHOD_KEY.toString())))
            .andExpect(jsonPath("$.[*].methodSignature").value(hasItem(DEFAULT_METHOD_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].charStar").value(hasItem(DEFAULT_CHAR_STAR)))
            .andExpect(jsonPath("$.[*].charEnd").value(hasItem(DEFAULT_CHAR_END)))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].lineOfCode").value(hasItem(DEFAULT_LINE_OF_CODE.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.namespace").value(DEFAULT_NAMESPACE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.typeFullPath").value(DEFAULT_TYPE_FULL_PATH.toString()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()))
            .andExpect(jsonPath("$.methodKey").value(DEFAULT_METHOD_KEY.toString()))
            .andExpect(jsonPath("$.methodSignature").value(DEFAULT_METHOD_SIGNATURE.toString()))
            .andExpect(jsonPath("$.charStar").value(DEFAULT_CHAR_STAR))
            .andExpect(jsonPath("$.charEnd").value(DEFAULT_CHAR_END))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER))
            .andExpect(jsonPath("$.lineOfCode").value(DEFAULT_LINE_OF_CODE.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);
        eventSearchRepository.save(event);
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findOne(event.getId());
        updatedEvent
            .kind(UPDATED_KIND)
            .detail(UPDATED_DETAIL)
            .namespace(UPDATED_NAMESPACE)
            .type(UPDATED_TYPE)
            .typeFullPath(UPDATED_TYPE_FULL_PATH)
            .method(UPDATED_METHOD)
            .methodKey(UPDATED_METHOD_KEY)
            .methodSignature(UPDATED_METHOD_SIGNATURE)
            .charStar(UPDATED_CHAR_STAR)
            .charEnd(UPDATED_CHAR_END)
            .lineNumber(UPDATED_LINE_NUMBER)
            .lineOfCode(UPDATED_LINE_OF_CODE)
            .created(UPDATED_CREATED);

        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvent)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getKind()).isEqualTo(UPDATED_KIND);
        assertThat(testEvent.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testEvent.getNamespace()).isEqualTo(UPDATED_NAMESPACE);
        assertThat(testEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvent.getTypeFullPath()).isEqualTo(UPDATED_TYPE_FULL_PATH);
        assertThat(testEvent.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testEvent.getMethodKey()).isEqualTo(UPDATED_METHOD_KEY);
        assertThat(testEvent.getMethodSignature()).isEqualTo(UPDATED_METHOD_SIGNATURE);
        assertThat(testEvent.getCharStar()).isEqualTo(UPDATED_CHAR_STAR);
        assertThat(testEvent.getCharEnd()).isEqualTo(UPDATED_CHAR_END);
        assertThat(testEvent.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testEvent.getLineOfCode()).isEqualTo(UPDATED_LINE_OF_CODE);
        assertThat(testEvent.getCreated()).isEqualTo(UPDATED_CREATED);

        // Validate the Event in Elasticsearch
        Event eventEs = eventSearchRepository.findOne(testEvent.getId());
        assertThat(eventEs).isEqualToComparingFieldByField(testEvent);
    }

    @Test
    @Transactional
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Create the Event

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);
        eventSearchRepository.save(event);
        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Get the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eventExistsInEs = eventSearchRepository.exists(event.getId());
        assertThat(eventExistsInEs).isFalse();

        // Validate the database is empty
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);
        eventSearchRepository.save(event);

        // Search the event
        restEventMockMvc.perform(get("/api/_search/events?query=id:" + event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].namespace").value(hasItem(DEFAULT_NAMESPACE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typeFullPath").value(hasItem(DEFAULT_TYPE_FULL_PATH.toString())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].methodKey").value(hasItem(DEFAULT_METHOD_KEY.toString())))
            .andExpect(jsonPath("$.[*].methodSignature").value(hasItem(DEFAULT_METHOD_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].charStar").value(hasItem(DEFAULT_CHAR_STAR)))
            .andExpect(jsonPath("$.[*].charEnd").value(hasItem(DEFAULT_CHAR_END)))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].lineOfCode").value(hasItem(DEFAULT_LINE_OF_CODE.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);
        event2.setId(2L);
        assertThat(event1).isNotEqualTo(event2);
        event1.setId(null);
        assertThat(event1).isNotEqualTo(event2);
    }
}
