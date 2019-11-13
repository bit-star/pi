package com.lazulite.pi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FormComponentValues.
 */
@Entity
@Table(name = "form_component_values")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormComponentValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component_type")
    private String componentType;

    @Column(name = "value")
    private String value;

    @Column(name = "name")
    private String name;

    @Column(name = "ext_value")
    private String extValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("formComponentValues")
    private ProcessInstance processInstance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentType() {
        return componentType;
    }

    public FormComponentValues componentType(String componentType) {
        this.componentType = componentType;
        return this;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getValue() {
        return value;
    }

    public FormComponentValues value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public FormComponentValues name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtValue() {
        return extValue;
    }

    public FormComponentValues extValue(String extValue) {
        this.extValue = extValue;
        return this;
    }

    public void setExtValue(String extValue) {
        this.extValue = extValue;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public FormComponentValues processInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
        return this;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormComponentValues)) {
            return false;
        }
        return id != null && id.equals(((FormComponentValues) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FormComponentValues{" +
            "id=" + getId() +
            ", componentType='" + getComponentType() + "'" +
            ", value='" + getValue() + "'" +
            ", name='" + getName() + "'" +
            ", extValue='" + getExtValue() + "'" +
            "}";
    }
}
