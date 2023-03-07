package com.example.emptask.service;

import com.example.emptask.dto.ApiResponse;
import com.example.emptask.dto.PurchaseOrder;
import org.json.JSONException;

public interface CeoService {
    ApiResponse claimTask(String taskId, String userId) throws JSONException;

    ApiResponse completeTask(String taskId, PurchaseOrder purchaseOrder, String instanceId, String businessKey) throws JSONException;
}
