package org.swarmdebugging.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PathNode.
 */
@Entity
@Table(name = "path_node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pathnode")
public class PathNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "namespace")
    private String namespace;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "method")
    private String method;

    @Column(name = "created")
    private ZonedDateTime created;

    @OneToOne
    @JoinColumn(unique = true)
    private PathNode origin;

    @OneToOne(mappedBy = "origin")
    @JsonIgnore
    private PathNode destination;

    @ManyToOne
    private Session session;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public PathNode namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getType() {
        return type;
    }

    public PathNode type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public PathNode method(String method) {
        this.method = method;
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public PathNode created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public PathNode getOrigin() {
        return origin;
    }

    public PathNode origin(PathNode pathNode) {
        this.origin = pathNode;
        return this;
    }

    public void setOrigin(PathNode pathNode) {
        this.origin = pathNode;
    }

    public PathNode getDestination() {
        return destination;
    }

    public PathNode destination(PathNode pathNode) {
        this.destination = pathNode;
        return this;
    }

    public void setDestination(PathNode pathNode) {
        this.destination = pathNode;
    }

    public Session getSession() {
        return session;
    }

    public PathNode session(Session session) {
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
        PathNode pathNode = (PathNode) o;
        if (pathNode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pathNode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PathNode{" +
            "id=" + getId() +
            ", namespace='" + getNamespace() + "'" +
            ", type='" + getType() + "'" +
            ", method='" + getMethod() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
