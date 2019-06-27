package swarm.server.domains;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import io.leangen.graphql.annotations.GraphQLIgnore;


@Entity
public class Developer implements Serializable{
	
	private static final long serialVersionUID = -8377345229493337082L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	String username;
	
	String color;
	
	@Column(name="CREATION_TS", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Calendar timestamp;
	
	@Transient
	boolean logged;
	
	public Developer(String username, String color) {
		this.username = username;
		this.color = color;
	}
	
	public Developer() {}

	public Long getId() {
		return id;
	}

	@GraphQLIgnore
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getColor() {
		return color;
	}

	@GraphQLIgnore
	public void setColor(String color) {
		this.color = color;
	}

	public boolean isLogged() {
		return logged;
	}

	@GraphQLIgnore
	public void setLogged(boolean logged) {
		this.logged = logged;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Developer developer = (Developer) o;

        return id.equals(developer.id);
    }
	
	@Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
		return id + ": " + username;
	}

}
