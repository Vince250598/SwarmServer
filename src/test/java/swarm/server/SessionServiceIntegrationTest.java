package swarm.server;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    /*@Test TODO
    public void whenUpdateSession_thenReturnSession() {
        Session session = mock(Session.class);

        when(sessionRepository.save(session)).thenReturn(session);

        Date date = new Date(2015,05,25);

        Session newSession = sessionService.updateSession(session.getId(), date, date);

        assertEquals(session, newSession);
    }*/



}