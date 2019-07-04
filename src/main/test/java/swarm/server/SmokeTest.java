import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import swarm.server.controllers.rest.DeveloperRestController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SmokeTest {

    @Autowired
    private DeveloperRestController developerRestController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(developerRestController).isNotNull();
    }
}