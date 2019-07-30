package swarm.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import swarm.server.domains.Developer;
import swarm.server.repositories.DeveloperRepository;
import swarm.server.services.DeveloperService;

//@RunWith(MockitoJUnitRunner.class)
public class DeveloperServiceIntegrationTest {

    @Mock
    DeveloperRepository developerRepository;

    @InjectMocks
    DeveloperService developerService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenDeveloperByUsername_thenReturnDeveloper() {
        when(developerRepository.findByUsernameAllIgnoringCase("Bob")).thenReturn(new Developer("Bob"));

        Developer dev = developerService.developerByUsername("Bob");

        assertEquals("Bob", dev.getUsername());
    }

    @Test
    public void whenCreateDeveloper_thenReturnDeveloper() {
        Developer Bob = new Developer("Bob");

        when(developerRepository.save(Bob)).thenReturn(Bob);

        Developer saved = developerService.createDeveloper(Bob);

        assertEquals(Bob, saved);
    }
}