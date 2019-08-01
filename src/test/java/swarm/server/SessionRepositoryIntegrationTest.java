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
import swarm.server.domains.Product;
import swarm.server.domains.Session;
import swarm.server.domains.Task;
import swarm.server.repositories.SessionRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureMockMvc
public class SessionRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private SessionRepository sessionRepository; 

    @Test
    public void whenFindByTask_thenReturnSessions() {

        Product product = new Product("name");
        testEntityManager.persist(product);
        testEntityManager.flush();

        Task task = new Task(product, "title", "url", false);
        testEntityManager.persist(task);
        testEntityManager.flush();

        Developer developer = new Developer("Username");
        testEntityManager.persist(developer);
        testEntityManager.flush();

        Session session = new Session(developer, task, "description", "label", "purpose", "project");
        testEntityManager.persist(session);
        testEntityManager.flush();

        Iterable<Session> found = sessionRepository.findByTask(task.getId());

        for (Session s : found) {
            assertEquals(session, s);
        }
    }

    @Test
    public void whenFindByTaskAndDeveloper_thenReturnSessions() {

        Product product = new Product("name");
        testEntityManager.persist(product);
        testEntityManager.flush();

        Task task = new Task(product, "title", "url", false);
        testEntityManager.persist(task);
        testEntityManager.flush();

        Developer developer = new Developer("Username");
        testEntityManager.persist(developer);
        testEntityManager.flush();

        Session session = new Session(developer, task, "description", "label", "purpose", "project");
        testEntityManager.persist(session);
        testEntityManager.flush();

        Iterable<Session> found = sessionRepository.findByTaskAndDeveloper(task.getId(), developer.getId());

        for (Session s : found) {
            assertEquals(session, s);
        }
    }
}