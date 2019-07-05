package swarm.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import swarm.server.domains.Developer;
import swarm.server.repositories.DeveloperRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureMockMvc
public class DeveloperRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DeveloperRepository developerRepository;

    @Test
    public void whenFindByUsernameIgnoringCase_thenReturnDeveloper() {

        Developer Bob = new Developer("Bob");
        entityManager.persist(Bob);
        entityManager.flush();

        Developer found = developerRepository.findByUsernameAllIgnoringCase(Bob.getUsername());

        assertEquals(Bob, found);

    }

}