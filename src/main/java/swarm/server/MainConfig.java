package swarm.server;

//import java.net.URI;

import org.springframework.context.annotation.Configuration;

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
}

