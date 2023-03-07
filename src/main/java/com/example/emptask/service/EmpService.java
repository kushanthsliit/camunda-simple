package com.example.emptask.service;

import com.example.emptask.dto.*;
import com.sun.org.apache.xml.internal.utils.URI;
import org.json.JSONException;

import java.util.List;

public interface EmpService {

    ProcessDefinitionResponse createRequest(String processDefinitionKey, EmpRequest request) throws JSONException;

    ApiResponse claimTask(String taskId, String userId) throws JSONException;

    ApiResponse completeTask(String taskId, PurchaseOrder purchaseOrder) throws JSONException;

    List<TaskDefinitionResponse> getTask(String instanceId, String businessKey) throws URI.MalformedURIException;
}
