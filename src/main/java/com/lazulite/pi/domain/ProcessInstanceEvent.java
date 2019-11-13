package com.lazulite.pi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProcessInstanceEvent.
 */
@Entity
@Table(name = "process_instance_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessInstanceEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "finish_time")
    private Instant finishTime;

    @Column(name = "corp_id")
    private String corpId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "business_id")
    private String businessId;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "url")
    private String url;

    @Column(name = "result")
    private String result;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "process_code")
    private String processCode;

    @Column(name = "biz_category_id")
    private String bizCategoryId;

    @Column(name = "staff_id")
    private String staffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("processInstanceEvents")
    private ProcessInstance processInstance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFinishTime() {
        return finishTime;
    }

    public ProcessInstanceEvent finishTime(Instant finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public String getCorpId() {
        return corpId;
    }

    public ProcessInstanceEvent corpId(String corpId) {
        this.corpId = corpId;
        return this;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getEventType() {
        return eventType;
    }

    public ProcessInstanceEvent eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public ProcessInstanceEvent businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTitle() {
        return title;
    }

    public ProcessInstanceEvent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public ProcessInstanceEvent type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public ProcessInstanceEvent url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return result;
    }

    public ProcessInstanceEvent result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return createTime;
    }

    public ProcessInstanceEvent createTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProcessCode() {
        return processCode;
    }

    public ProcessInstanceEvent processCode(String processCode) {
        this.processCode = processCode;
        return this;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getBizCategoryId() {
        return bizCategoryId;
    }

    public ProcessInstanceEvent bizCategoryId(String bizCategoryId) {
        this.bizCategoryId = bizCategoryId;
        return this;
    }

    public void setBizCategoryId(String bizCategoryId) {
        this.bizCategoryId = bizCategoryId;
    }

    public String getStaffId() {
        return staffId;
    }

    public ProcessInstanceEvent staffId(String staffId) {
        this.staffId = staffId;
        return this;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public ProcessInstanceEvent processInstance(ProcessInstance processInstance) {
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
        if (!(o instanceof ProcessInstanceEvent)) {
            return false;
        }
        return id != null && id.equals(((ProcessInstanceEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProcessInstanceEvent{" +
            "id=" + getId() +
            ", finishTime='" + getFinishTime() + "'" +
            ", corpId='" + getCorpId() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", businessId='" + getBusinessId() + "'" +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", url='" + getUrl() + "'" +
            ", result='" + getResult() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", processCode='" + getProcessCode() + "'" +
            ", bizCategoryId='" + getBizCategoryId() + "'" +
            ", staffId='" + getStaffId() + "'" +
            "}";
    }
}
