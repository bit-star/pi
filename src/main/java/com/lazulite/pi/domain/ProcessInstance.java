package com.lazulite.pi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProcessInstance.
 */
@Entity
@Table(name = "process_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attached_process_instance_ids")
    private String attachedProcessInstanceIds;

    @Column(name = "biz_action")
    private String bizAction;

    @Column(name = "business_id")
    private String businessId;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "finish_time")
    private Instant finishTime;

    @Column(name = "operation_records")
    private String operationRecords;

    @Column(name = "originator_dept_id")
    private String originatorDeptId;

    @Column(name = "originator_dept_name")
    private String originatorDeptName;

    @Column(name = "originator_userid")
    private String originatorUserid;

    @Column(name = "result")
    private String result;

    @Column(name = "status")
    private String status;

    @Column(name = "tasks")
    private String tasks;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "processInstance")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProcessInstanceEvent> processInstanceEvents = new HashSet<>();

    @OneToMany(mappedBy = "processInstance")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormComponentValues> formComponentValues = new HashSet<>();

    @OneToMany(mappedBy = "processInstance")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProcessMsgTask> processMsgTasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("processInstances")
    private ProcessTemplate processTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttachedProcessInstanceIds() {
        return attachedProcessInstanceIds;
    }

    public ProcessInstance attachedProcessInstanceIds(String attachedProcessInstanceIds) {
        this.attachedProcessInstanceIds = attachedProcessInstanceIds;
        return this;
    }

    public void setAttachedProcessInstanceIds(String attachedProcessInstanceIds) {
        this.attachedProcessInstanceIds = attachedProcessInstanceIds;
    }

    public String getBizAction() {
        return bizAction;
    }

    public ProcessInstance bizAction(String bizAction) {
        this.bizAction = bizAction;
        return this;
    }

    public void setBizAction(String bizAction) {
        this.bizAction = bizAction;
    }

    public String getBusinessId() {
        return businessId;
    }

    public ProcessInstance businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public ProcessInstance createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getFinishTime() {
        return finishTime;
    }

    public ProcessInstance finishTime(Instant finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public String getOperationRecords() {
        return operationRecords;
    }

    public ProcessInstance operationRecords(String operationRecords) {
        this.operationRecords = operationRecords;
        return this;
    }

    public void setOperationRecords(String operationRecords) {
        this.operationRecords = operationRecords;
    }

    public String getOriginatorDeptId() {
        return originatorDeptId;
    }

    public ProcessInstance originatorDeptId(String originatorDeptId) {
        this.originatorDeptId = originatorDeptId;
        return this;
    }

    public void setOriginatorDeptId(String originatorDeptId) {
        this.originatorDeptId = originatorDeptId;
    }

    public String getOriginatorDeptName() {
        return originatorDeptName;
    }

    public ProcessInstance originatorDeptName(String originatorDeptName) {
        this.originatorDeptName = originatorDeptName;
        return this;
    }

    public void setOriginatorDeptName(String originatorDeptName) {
        this.originatorDeptName = originatorDeptName;
    }

    public String getOriginatorUserid() {
        return originatorUserid;
    }

    public ProcessInstance originatorUserid(String originatorUserid) {
        this.originatorUserid = originatorUserid;
        return this;
    }

    public void setOriginatorUserid(String originatorUserid) {
        this.originatorUserid = originatorUserid;
    }

    public String getResult() {
        return result;
    }

    public ProcessInstance result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public ProcessInstance status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTasks() {
        return tasks;
    }

    public ProcessInstance tasks(String tasks) {
        this.tasks = tasks;
        return this;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getTitle() {
        return title;
    }

    public ProcessInstance title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<ProcessInstanceEvent> getProcessInstanceEvents() {
        return processInstanceEvents;
    }

    public ProcessInstance processInstanceEvents(Set<ProcessInstanceEvent> processInstanceEvents) {
        this.processInstanceEvents = processInstanceEvents;
        return this;
    }

    public ProcessInstance addProcessInstanceEvent(ProcessInstanceEvent processInstanceEvent) {
        this.processInstanceEvents.add(processInstanceEvent);
        processInstanceEvent.setProcessInstance(this);
        return this;
    }

    public ProcessInstance removeProcessInstanceEvent(ProcessInstanceEvent processInstanceEvent) {
        this.processInstanceEvents.remove(processInstanceEvent);
        processInstanceEvent.setProcessInstance(null);
        return this;
    }

    public void setProcessInstanceEvents(Set<ProcessInstanceEvent> processInstanceEvents) {
        this.processInstanceEvents = processInstanceEvents;
    }

    public Set<FormComponentValues> getFormComponentValues() {
        return formComponentValues;
    }

    public ProcessInstance formComponentValues(Set<FormComponentValues> formComponentValues) {
        this.formComponentValues = formComponentValues;
        return this;
    }

    public ProcessInstance addFormComponentValues(FormComponentValues formComponentValues) {
        this.formComponentValues.add(formComponentValues);
        formComponentValues.setProcessInstance(this);
        return this;
    }

    public ProcessInstance removeFormComponentValues(FormComponentValues formComponentValues) {
        this.formComponentValues.remove(formComponentValues);
        formComponentValues.setProcessInstance(null);
        return this;
    }

    public void setFormComponentValues(Set<FormComponentValues> formComponentValues) {
        this.formComponentValues = formComponentValues;
    }

    public Set<ProcessMsgTask> getProcessMsgTasks() {
        return processMsgTasks;
    }

    public ProcessInstance processMsgTasks(Set<ProcessMsgTask> processMsgTasks) {
        this.processMsgTasks = processMsgTasks;
        return this;
    }

    public ProcessInstance addProcessMsgTask(ProcessMsgTask processMsgTask) {
        this.processMsgTasks.add(processMsgTask);
        processMsgTask.setProcessInstance(this);
        return this;
    }

    public ProcessInstance removeProcessMsgTask(ProcessMsgTask processMsgTask) {
        this.processMsgTasks.remove(processMsgTask);
        processMsgTask.setProcessInstance(null);
        return this;
    }

    public void setProcessMsgTasks(Set<ProcessMsgTask> processMsgTasks) {
        this.processMsgTasks = processMsgTasks;
    }

    public ProcessTemplate getProcessTemplate() {
        return processTemplate;
    }

    public ProcessInstance processTemplate(ProcessTemplate processTemplate) {
        this.processTemplate = processTemplate;
        return this;
    }

    public void setProcessTemplate(ProcessTemplate processTemplate) {
        this.processTemplate = processTemplate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessInstance)) {
            return false;
        }
        return id != null && id.equals(((ProcessInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProcessInstance{" +
            "id=" + getId() +
            ", attachedProcessInstanceIds='" + getAttachedProcessInstanceIds() + "'" +
            ", bizAction='" + getBizAction() + "'" +
            ", businessId='" + getBusinessId() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", finishTime='" + getFinishTime() + "'" +
            ", operationRecords='" + getOperationRecords() + "'" +
            ", originatorDeptId='" + getOriginatorDeptId() + "'" +
            ", originatorDeptName='" + getOriginatorDeptName() + "'" +
            ", originatorUserid='" + getOriginatorUserid() + "'" +
            ", result='" + getResult() + "'" +
            ", status='" + getStatus() + "'" +
            ", tasks='" + getTasks() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
