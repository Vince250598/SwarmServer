package swarm.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import swarm.server.domains.Developer;
import swarm.server.repositories.DeveloperRepository;
import swarm.server.services.DeveloperService;


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
}