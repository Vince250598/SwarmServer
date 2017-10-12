package org.swarmdebugging.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Session.
 */
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "description")
    private String description;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "started")
    private ZonedDateTime started;

    @Column(name = "finished")
    private ZonedDateTime finished;

    @OneToMany(mappedBy = "session")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PathNode> segments = new HashSet<>();

    @OneToMany(mappedBy = "session")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Breakpoint> breakpoints = new HashSet<>();

    @OneToMany(mappedBy = "session")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Event> events = new HashSet<>();

    @ManyToOne
    private Task task;

    @ManyToOne
    private Developer developer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Session label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public Session description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurpose() {
        return purpose;
    }

    public Session purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public ZonedDateTime getStarted() {
        return started;
    }

    public Session started(ZonedDateTime started) {
        this.started = started;
        return this;
    }

    public void setStarted(ZonedDateTime started) {
        this.started = started;
    }

    public ZonedDateTime getFinished() {
        return finished;
    }

    public Session finished(ZonedDateTime finished) {
        this.finished = finished;
        return this;
    }

    public void setFinished(ZonedDateTime finished) {
        this.finished = finished;
    }

    public Set<PathNode> getSegments() {
        return segments;
    }

    public Session segments(Set<PathNode> pathNodes) {
        this.segments = pathNodes;
        return this;
    }

    public Session addSegment(PathNode pathNode) {
        this.segments.add(pathNode);
        pathNode.setSession(this);
        return this;
    }

    public Session removeSegment(PathNode pathNode) {
        this.segments.remove(pathNode);
        pathNode.setSession(null);
        return this;
    }

    public void setSegments(Set<PathNode> pathNodes) {
        this.segments = pathNodes;
    }

    public Set<Breakpoint> getBreakpoints() {
        return breakpoints;
    }

    public Session breakpoints(Set<Breakpoint> breakpoints) {
        this.breakpoints = breakpoints;
        return this;
    }

    public Session addBreakpoint(Breakpoint breakpoint) {
        this.breakpoints.add(breakpoint);
        breakpoint.setSession(this);
        return this;
    }

    public Session removeBreakpoint(Breakpoint breakpoint) {
        this.breakpoints.remove(breakpoint);
        breakpoint.setSession(null);
        return this;
    }

    public void setBreakpoints(Set<Breakpoint> breakpoints) {
        this.breakpoints = breakpoints;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Session events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Session addEvent(Event event) {
        this.events.add(event);
        event.setSession(this);
        return this;
    }

    public Session removeEvent(Event event) {
        this.events.remove(event);
        event.setSession(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Task getTask() {
        return task;
    }

    public Session task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public Session developer(Developer developer) {
        this.developer = developer;
        return this;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
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
        Session session = (Session) o;
        if (session.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", started='" + getStarted() + "'" +
            ", finished='" + getFinished() + "'" +
            "}";
    }
}
