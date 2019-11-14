package com.lazulite.pi.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.lazulite.pi.domain.enumeration.MessageStatus;

/**
 * A ProcessMsgTask.
 */
@Entity
@Table(name = "process_msg_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessMsgTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiving_department")
    private String receivingDepartment;

    @Column(name = "receiving_user")
    private String receivingUser;

    @Column(name = "title")
    private String title;

    @Column(name = "json")
    private String json;

    @Column(name = "execute_time")
    private Instant executeTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceivingDepartment() {
        return receivingDepartment;
    }

    public ProcessMsgTask receivingDepartment(String receivingDepartment) {
        this.receivingDepartment = receivingDepartment;
        return this;
    }

    public void setReceivingDepartment(String receivingDepartment) {
        this.receivingDepartment = receivingDepartment;
    }

    public String getReceivingUser() {
        return receivingUser;
    }

    public ProcessMsgTask receivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
        return this;
    }

    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }

    public String getTitle() {
        return title;
    }

    public ProcessMsgTask title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJson() {
        return json;
    }

    public ProcessMsgTask json(String json) {
        this.json = json;
        return this;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Instant getExecuteTime() {
        return executeTime;
    }

    public ProcessMsgTask executeTime(Instant executeTime) {
        this.executeTime = executeTime;
        return this;
    }

    public void setExecuteTime(Instant executeTime) {
        this.executeTime = executeTime;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public ProcessMsgTask status(MessageStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessMsgTask)) {
            return false;
        }
        return id != null && id.equals(((ProcessMsgTask) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProcessMsgTask{" +
            "id=" + getId() +
            ", receivingDepartment='" + getReceivingDepartment() + "'" +
            ", receivingUser='" + getReceivingUser() + "'" +
            ", title='" + getTitle() + "'" +
            ", json='" + getJson() + "'" +
            ", executeTime='" + getExecuteTime() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
