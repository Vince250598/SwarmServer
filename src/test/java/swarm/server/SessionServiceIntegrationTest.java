package swarm.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import swarm.server.domains.Developer;
import swarm.server.domains.Product;
import swarm.server.domains.Session;
import swarm.server.domains.Task;
import swarm.server.repositories.SessionRepository;
import swarm.server.services.SessionService;

public class SessionServiceIntegrationTest {

    @Mock
    SessionRepository sessionRepository;

    @InjectMocks
    SessionService sessionService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenUpdateSession_thenReturnSession() {

        Product product = new Product("name");

        Task task = new Task(product, "title", "url", false);

        Developer developer = new Developer("Username");

        Session session = new Session(developer, task, "description", "label", "purpose", "project");

        when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
        when(sessionRepository.save(session)).thenReturn(session);

        Date date = new Date(2015, 05, 25);

        Session updated = sessionService.updateSession(session.getId(), date, date);
        
        assertEquals(session, updated);
    }

    @Test
    public void whenSessionStart_thenReturnSession() {

        Product product = new Product("name");

        Task task = new Task(product, "title", "url", false);

        Developer developer = new Developer("Username");

        Session session = new Session(developer, task, "description", "label", "purpose", "project");

        when(sessionRepository.save(session)).thenReturn(session);

        Session started = sessionService.sessionStart(session);

        assertEquals(session, started);
    }



}