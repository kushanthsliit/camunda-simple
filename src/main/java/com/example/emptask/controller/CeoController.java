package com.example.emptask.controller;

import com.example.emptask.dto.ApiResponse;
import com.example.emptask.dto.PurchaseOrder;
import com.example.emptask.service.CeoService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ceo-process")
public class CeoController {

    @Autowired
    CeoService ceoService;

    @PostMapping("/claim-task/{task-id}")
    public @ResponseBody
    ApiResponse claimTask(@PathVariable("task-id") String taskId, @RequestParam String userId) throws JSONException {
        return ceoService.claimTask(taskId, userId);
    }

    @PostMapping("/complete-task/{task-id}")
    public @ResponseBody ApiResponse completeTask(@PathVariable("task-id") String taskId, @RequestBody PurchaseOrder request, String instanceId, String businessKey) throws JSONException {
        return ceoService.completeTask(taskId, request, instanceId, businessKey);
    }
}
