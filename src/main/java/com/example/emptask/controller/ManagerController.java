package com.example.emptask.controller;

import com.example.emptask.dto.ApiResponse;
import com.example.emptask.dto.PurchaseOrder;
import com.example.emptask.service.ManagerService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mng-process")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping("/claim-task/{task-id}")
    public @ResponseBody
    ApiResponse claimTask(@PathVariable("task-id") String taskId, @RequestParam String userId) throws JSONException {
        return managerService.claimTask(taskId, userId);
    }

    @PostMapping("/complete-task/{task-id}")
    public @ResponseBody ApiResponse completeTask(@PathVariable("task-id") String taskId, @RequestBody PurchaseOrder request) throws JSONException {
        return managerService.completeTask(taskId, request);
    }

}
