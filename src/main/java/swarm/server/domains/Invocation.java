package swarm.server.domains;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import io.leangen.graphql.annotations.GraphQLIgnore;


@Entity
public class Invocation implements Serializable{
	
	private static final long serialVersionUID = 8724123547818474005L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(optional = false)
	private Method invoking;

	@ManyToOne(optional = false)
	private Method invoked;

	@ManyToOne(optional = false)
	private Session session;
	
	@Column(name="CREATION_TS", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Calendar timestamp;
	
	@Transient
	private boolean isVirtual;
	
	public Invocation () {}
	
	public Invocation(Method invoking, Method invoked, Session session, boolean isVirtual) {
		this.invoking = invoking;
		this.invoked = invoked;
		this.session = session;
		this.isVirtual = isVirtual;
	}
	
	public Long getId() {
		return id;
	}

	@GraphQLIgnore
	public void setId(Long id) {
		this.id = id;
	}

	public Method getInvoking() {
		return invoking;
	}

	public void setInvoking(Method invoking) {
		this.invoking = invoking;
	}

	public Method getInvoked() {
		return invoked;
	}

	public void setInvoked(Method invoked) {
		this.invoked = invoked;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public boolean isVirtual() {
		return this.isVirtual;
	}
	
	public void setVirtual(boolean virtual) {
		this.isVirtual = virtual;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invocation invocation = (Invocation) o;

        return id.equals(invocation.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
	
    @Override
	public String toString() {
		return invoking.getId() + ": " + invoking.getKey() + " -> " + invoked.getId() + ": " + invoked.getKey();
	}
	
}
