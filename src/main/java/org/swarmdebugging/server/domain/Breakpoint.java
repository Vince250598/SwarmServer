package org.swarmdebugging.server.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.swarmdebugging.server.domain.enumeration.BreakpointKind;

/**
 * A Breakpoint.
 */
@Entity
@Table(name = "breakpoint")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "breakpoint")
public class Breakpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind")
    private BreakpointKind kind;

    @Column(name = "namespace")
    private String namespace;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "line_of_code")
    private String lineOfCode;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private Session session;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BreakpointKind getKind() {
        return kind;
    }

    public Breakpoint kind(BreakpointKind kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(BreakpointKind kind) {
        this.kind = kind;
    }

    public String getNamespace() {
        return namespace;
    }

    public Breakpoint namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getType() {
        return type;
    }

    public Breakpoint type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public Breakpoint lineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLineOfCode() {
        return lineOfCode;
    }

    public Breakpoint lineOfCode(String lineOfCode) {
        this.lineOfCode = lineOfCode;
        return this;
    }

    public void setLineOfCode(String lineOfCode) {
        this.lineOfCode = lineOfCode;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Breakpoint created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Session getSession() {
        return session;
    }

    public Breakpoint session(Session session) {
        this.session = session;
        return this;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Breakpoint breakpoint = (Breakpoint) o;
        if (breakpoint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), breakpoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Breakpoint{" +
            "id=" + getId() +
            ", kind='" + getKind() + "'" +
            ", namespace='" + getNamespace() + "'" +
            ", type='" + getType() + "'" +
            ", lineNumber='" + getLineNumber() + "'" +
            ", lineOfCode='" + getLineOfCode() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
