package com.example.emptask.dto;


import java.util.List;

public class ProcessDefinitionResponse {

	private String id;
	
	private String definitionId;
	
	private String businessKey;
	
	private String tenantId;
	
	private boolean ended;
	
	private boolean suspended;
	
	private List<TaskDefinitionResponse> tasks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public List<TaskDefinitionResponse> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskDefinitionResponse> tasks) {
		this.tasks = tasks;
	}	
}
