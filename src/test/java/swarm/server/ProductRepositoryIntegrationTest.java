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
import swarm.server.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureMockMvc
public class ProductRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFinByDeveloperId_thenReturnProducts() {

        Product product = new Product("ProductName");
        testEntityManager.persist(product);
        testEntityManager.flush();

        List productList = new ArrayList<Product>();
        productList.add(product);

        Task task = new Task();
        task.setProduct(product);
        testEntityManager.persist(task);
        testEntityManager.flush();

        Developer developer = new Developer("BOB");
        testEntityManager.persist(developer);
        testEntityManager.flush();

        Session session = new Session();
        session.setDeveloper(developer);
        session.setTask(task);

        Iterable<Product> found = productRepository.findByDeveloperId(developer.getId());

        for (Product pro : found) {
            assertEquals(productList.get(0), pro);
        }

    }

}