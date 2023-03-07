package com.example.emptask.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;
    private String assignee;
    private String taskId;
    private String businessKey;
    private String instanceId;
    private String taskRole;

    public Task(String status, String assignee, String taskId, String businessKey, String instanceId, String taskRole) {
        this.status = status;
        this.assignee = assignee;
        this.taskId = taskId;
        this.businessKey = businessKey;
        this.instanceId = instanceId;
        this.taskRole = taskRole;
    }

    public Task() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskRole() {
        return taskRole;
    }

    public void setTaskRole(String taskRole) {
        this.taskRole = taskRole;
    }
}
