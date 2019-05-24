package swarm.server.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import swarm.server.SwarmApplication;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = SwarmApplication.class)
@WebAppConfiguration
public class DeveloperRestControllerTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void login() throws Exception {
		this.mvc.perform(get("/login?name=petrillo")).andExpect(jsonPath("name", equalTo("petrillo")));
	}
	
	@Test
	public void getProjectsByDeveloper() throws Exception {
		//this.mvc.perform(get("/developer/projects?developerId=1")).andExpect(jsonPath("name", equalTo("petrillo")));
		System.out.println(this.mvc.perform(get("/developer/projects?developerId=1")).andReturn().getResponse().getContentAsString());
	}
}