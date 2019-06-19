package swarm.server.domains;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Artefact implements Serializable{
	
	private static final long serialVersionUID = -4862345229493334732L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(columnDefinition="text")
	String sourceCode;
	
	@Column(nullable=false)
	int typeHash;
	
	@Column(name="CREATION_TS", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Calendar timestamp;
	
	public Artefact() {}
	
	public Artefact(String sourceCode) {
		this.sourceCode = sourceCode;
		this.typeHash = -1;
	}
	
	@Override
    public int hashCode() {
        return id.hashCode();
    }
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artefact artefact = (Artefact) o;

        return id.equals(artefact.id);
    }
	
	@Override
    public String toString() {
		return id + ": " + sourceCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceCode()
	{
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public int getTypeHash() {
		return typeHash;
	}

	public void setTypeHash(int typeHash) {
		this.typeHash = typeHash;
	}

}
