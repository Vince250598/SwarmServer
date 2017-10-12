package org.swarmdebugging.server.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.swarmdebugging.server.domain.enumeration.EventKind;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind")
    private EventKind kind;

    @Column(name = "detail")
    private String detail;

    @Column(name = "namespace")
    private String namespace;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "type_full_path")
    private String typeFullPath;

    @Column(name = "method")
    private String method;

    @Column(name = "method_key")
    private String methodKey;

    @Column(name = "method_signature")
    private String methodSignature;

    @Column(name = "char_star")
    private Integer charStar;

    @Column(name = "char_end")
    private Integer charEnd;

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

    public EventKind getKind() {
        return kind;
    }

    public Event kind(EventKind kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(EventKind kind) {
        this.kind = kind;
    }

    public String getDetail() {
        return detail;
    }

    public Event detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNamespace() {
        return namespace;
    }

    public Event namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getType() {
        return type;
    }

    public Event type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeFullPath() {
        return typeFullPath;
    }

    public Event typeFullPath(String typeFullPath) {
        this.typeFullPath = typeFullPath;
        return this;
    }

    public void setTypeFullPath(String typeFullPath) {
        this.typeFullPath = typeFullPath;
    }

    public String getMethod() {
        return method;
    }

    public Event method(String method) {
        this.method = method;
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethodKey() {
        return methodKey;
    }

    public Event methodKey(String methodKey) {
        this.methodKey = methodKey;
        return this;
    }

    public void setMethodKey(String methodKey) {
        this.methodKey = methodKey;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public Event methodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
        return this;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    public Integer getCharStar() {
        return charStar;
    }

    public Event charStar(Integer charStar) {
        this.charStar = charStar;
        return this;
    }

    public void setCharStar(Integer charStar) {
        this.charStar = charStar;
    }

    public Integer getCharEnd() {
        return charEnd;
    }

    public Event charEnd(Integer charEnd) {
        this.charEnd = charEnd;
        return this;
    }

    public void setCharEnd(Integer charEnd) {
        this.charEnd = charEnd;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public Event lineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLineOfCode() {
        return lineOfCode;
    }

    public Event lineOfCode(String lineOfCode) {
        this.lineOfCode = lineOfCode;
        return this;
    }

    public void setLineOfCode(String lineOfCode) {
        this.lineOfCode = lineOfCode;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Event created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Session getSession() {
        return session;
    }

    public Event session(Session session) {
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
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", kind='" + getKind() + "'" +
            ", detail='" + getDetail() + "'" +
            ", namespace='" + getNamespace() + "'" +
            ", type='" + getType() + "'" +
            ", typeFullPath='" + getTypeFullPath() + "'" +
            ", method='" + getMethod() + "'" +
            ", methodKey='" + getMethodKey() + "'" +
            ", methodSignature='" + getMethodSignature() + "'" +
            ", charStar='" + getCharStar() + "'" +
            ", charEnd='" + getCharEnd() + "'" +
            ", lineNumber='" + getLineNumber() + "'" +
            ", lineOfCode='" + getLineOfCode() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
