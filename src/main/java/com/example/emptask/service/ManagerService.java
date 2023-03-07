package com.example.emptask.service;

import com.example.emptask.dto.ApiResponse;
import com.example.emptask.dto.PurchaseOrder;
import org.json.JSONException;

public interface ManagerService {

    ApiResponse claimTask(String taskId, String userId) throws JSONException;

    ApiResponse completeTask(String taskId, PurchaseOrder purchaseOrder) throws JSONException;
}
