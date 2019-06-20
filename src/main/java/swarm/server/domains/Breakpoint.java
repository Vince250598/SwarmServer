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
public class Breakpoint implements Serializable{
	
	private static final long serialVersionUID = -7145408705221743773L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(optional = false)
	private Type type;

	@Column(nullable = false)
	String charStart;
	
	@Column(nullable = false)
	String charEnd;
	
	@Column(nullable = false)
	Integer lineNumber;
	
	@Column(name="CREATION_TS", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Calendar timestamp;
	
	public Breakpoint() {}
	
	public Breakpoint(Type type, String charStart, String charEnd, Integer lineNumber) {
		this.type = type;
		this.charStart = charStart;
		this.charEnd = charEnd;
		this.lineNumber = lineNumber;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Breakpoint breakpoint = (Breakpoint) o;

        return id.equals(breakpoint.id);
    }
	
	@Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
		return id + ": " + lineNumber;
	}

	public Long getId() {
		return id;
	}

	@GraphQLIgnore
	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getCharStart() {
		return charStart;
	}

	public void setCharStart(String charStart) {
		this.charStart = charStart;
	}

	public String getCharEnd() {
		return charEnd;
	}

	public void setCharEnd(String charEnd) {
		this.charEnd = charEnd;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
}
