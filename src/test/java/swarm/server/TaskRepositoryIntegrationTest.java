package swarm.server;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
import swarm.server.repositories.TaskRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureMockMvc
public class TaskRepositoryIntegrationTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void whenFindByProductId_thenReturnTasks() {

        Product product = new Product("product");
        testEntityManager.persist(product);
        testEntityManager.flush();

        Task task1 = new Task();
        task1.setProduct(product);
        Task task2 = new Task();
        task2.setProduct(product);
        testEntityManager.persist(task1);
        testEntityManager.flush();
        testEntityManager.persist(task2);
        testEntityManager.flush();

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        Iterable<Task> found = taskRepository.findByProductId(product.getId());

        int i = 0;
        for (Task task : found) {
            assertEquals(taskList.get(i), task);
            i++;
        }
    }

    @Test
    public void whenFindActiveByDeveloperId_thenReturnTasks() {

        Developer developer = new Developer("Bob");
        testEntityManager.persist(developer);
        testEntityManager.flush();

        Product product = new Product("product");
        testEntityManager.persist(product);
        testEntityManager.flush();

        Task task1 = new Task();
        task1.setProduct(product);
        task1.setDone(true);
        Task task2 = new Task();
        task2.setProduct(product);
        task2.setDone(false);
        testEntityManager.persist(task1);
        testEntityManager.flush();
        testEntityManager.persist(task2);
        testEntityManager.flush();

        Session session1 = new Session(developer, task1, "description", "label", "purpose", "project");
        testEntityManager.persist(session1);
        testEntityManager.flush();

        Session session2 = new Session(developer, task2, "description", "label", "purpose", "project");
        testEntityManager.persist(session2);
        testEntityManager.flush();

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        Iterable<Task> active = taskRepository.findActiveTasksByDeveloperId(developer.getId());

        for (Task t : active) {
            assert task2 == t;
            assert t.getDone() == false;
        }
    }
}