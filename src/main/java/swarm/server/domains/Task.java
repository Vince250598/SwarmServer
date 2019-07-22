package swarm.server.domains;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.leangen.graphql.annotations.GraphQLIgnore;


@Entity
public class Task implements Serializable{
	
	private static final long serialVersionUID = -5461243719276270987L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	Product product;
	
	String title;
	
	String url;
	
	String color;

	boolean done;
	
	@Column(name="CREATION_TS", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Calendar timestamp;
	
	public Task () {}
	
	public Task(Product product, String title, String url) {
		this.product = product;
		this.title = title;
		this.url = url;
		done = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getColor() {
		return color;
	}

	@GraphQLIgnore
	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean getDone() {
		return done;
	}

	@GraphQLIgnore
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
		return id + ": " + title;
	}

}
