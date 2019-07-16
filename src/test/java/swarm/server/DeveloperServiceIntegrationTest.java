package swarm.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
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

    /*@Test //TODO
    public void whenCreateDeveloper_thenReturnDeveloper() {
        Developer Bob = mock(Developer.class);
        //Bob.setUsername("Bob");
        doReturn(Developer.class).when(developerRepository).save(Bob);

        Developer dev = developerService.createDeveloper(Bob);

        assertEquals("Bob", dev.getUsername());
    }*/
}