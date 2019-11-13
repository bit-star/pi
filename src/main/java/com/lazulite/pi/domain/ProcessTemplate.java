package com.lazulite.pi.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProcessTemplate.
 */
@Entity
@Table(name = "process_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "process_code")
    private String processCode;

    @OneToMany(mappedBy = "processTemplate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProcessInstance> processInstances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProcessTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessCode() {
        return processCode;
    }

    public ProcessTemplate processCode(String processCode) {
        this.processCode = processCode;
        return this;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public Set<ProcessInstance> getProcessInstances() {
        return processInstances;
    }

    public ProcessTemplate processInstances(Set<ProcessInstance> processInstances) {
        this.processInstances = processInstances;
        return this;
    }

    public ProcessTemplate addProcessInstance(ProcessInstance processInstance) {
        this.processInstances.add(processInstance);
        processInstance.setProcessTemplate(this);
        return this;
    }

    public ProcessTemplate removeProcessInstance(ProcessInstance processInstance) {
        this.processInstances.remove(processInstance);
        processInstance.setProcessTemplate(null);
        return this;
    }

    public void setProcessInstances(Set<ProcessInstance> processInstances) {
        this.processInstances = processInstances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessTemplate)) {
            return false;
        }
        return id != null && id.equals(((ProcessTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProcessTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", processCode='" + getProcessCode() + "'" +
            "}";
    }
}
