package swarm.server;

import java.net.URI;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import swarm.server.domains.*;
import swarm.server.repositories.*;

@Configuration
public class MainConfig {
	
	/*@Bean
	public BasicDataSource dataSource() throws Exception {
		String databaseURL = System.getenv("DATABASE_URL");
		if(databaseURL != null) {
            URI dbUri = new URI(databaseURL);

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(dbUrl);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);
            
            return basicDataSource;
    	} else {
    		throw new Exception("Environment var DATABASE_URL is not defined in your system.");
    	}
	}*/
	
	@Bean
	public CommandLineRunner test(
			DeveloperRepository developerRepository, 
			ArtefactRepository artefactRepository,
			SessionRepository sessionRepository,
			TaskRepository taskRepository,
			ProductRepository productRepository,
			NamespaceRepository namespaceRepository,
			TypeRepository typeRepository,
			BreakpointRepository breakpointRepository,
			MethodRepository methodRepository,
			InvocationRepository invocationRepository,
			EventRepository eventRepository) {
		
		return (args) -> {
			Developer developer1 = new Developer("Jean-Guy", "developer1.color");
			Developer developer2 = new Developer("Robert", "developer2.color");
			
			Artefact artefact1 = new Artefact("public class HelloWorld {\r\n" + 
					"\r\n" + 
					"    public static void main(String[] args) {\r\n" + 
					"        // Prints \"Hello, World\" to the terminal window.\r\n" + 
					"        System.out.println(\"Hello, World\");\r\n" + 
					"    }\r\n" + 
					"\r\n" + 
					"}");
			Artefact artefact2 = new Artefact("class ForLoopExample2 {\r\n" + 
					"    public static void main(String args[]){\r\n" + 
					"         for(int i=1; i>=1; i++){\r\n" + 
					"              System.out.println(\"The value of i is: \"+i);\r\n" + 
					"         }\r\n" + 
					"    }\r\n" + 
					"}");			
			
			Product product1 = new Product("product1.name");
			Product product2 = new Product("product2.name");
			
			Task task1 = new Task(product1, "task1.title","task1.url","task1.color");
			Task task2 = new Task(product2, "task2.title","task2.url","task2.color");
			
			Session session1 = new Session(developer1, task1, "session1.description", "session1.label","session1.purpose","session1.project");
			Session session2 = new Session(developer2, task2, "session2.description", "session2.label","session2.purpose","session2.project");
			
			Namespace namespace1 = new Namespace("namespace1.name","namespace1.fullPath");
			Namespace namespace2 = new Namespace("namespace2.name","namespace2.fullPath");
			
			Type type1 = new Type(namespace1, session1, "type1.fullName","type1.fullPath", "type1.name",artefact1);
			Type type2 = new Type(namespace2, session2, "type2.fullName","type2.fullPath", "type2.name",artefact2);
			
			/*Breakpoint breakpoint1 = new Breakpoint(type1,"breakpoint1.start","breakpoint1.end",59);
			Breakpoint breakpoint2 = new Breakpoint(type2,"breakpoint2.start","breakpoint2.end",374);
			*/
			Method method1 = new Method(type1,"method1.key","method1.name","method1.signature");
			Method method2 = new Method(type2,"method2.key","method2.name","method2.signature");
			
			/*Invocation invocation1 = new Invocation(method1,method2,session1,false);
			
			Event event1 = new Event(method2,session2,"event1.charStart","event1.charEnd",349,"event1.detail","event1.kind");*/
			
			developerRepository.save(developer1);
			developerRepository.save(developer2);
			artefactRepository.save(artefact1);
			artefactRepository.save(artefact2);
			productRepository.save(product1);
			productRepository.save(product2);
			taskRepository.save(task1);
			taskRepository.save(task2);
			sessionRepository.save(session1);
			sessionRepository.save(session2);
			namespaceRepository.save(namespace1);
			namespaceRepository.save(namespace2);
			typeRepository.save(type1);
			typeRepository.save(type2);
			/*breakpointRepository.save(breakpoint1);
			breakpointRepository.save(breakpoint2);*/
			methodRepository.save(method1);
			methodRepository.save(method2);
			/*invocationRepository.save(invocation1);
			eventRepository.save(event1);*/
		};
	}
}

