package com.example.emptask.controller;

import com.example.emptask.dto.*;
import com.example.emptask.service.EmpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.org.apache.xml.internal.utils.URI;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/api/emp-process")
public class EmpController {

    @Autowired
    EmpService empService;

    @PostMapping("/create-task/{process-definition-key}")
    public @ResponseBody
    ProcessDefinitionResponse createRequest(@PathVariable("process-definition-key") String processDefinitionKey,
                                            @RequestBody EmpRequest request) throws JSONException, JsonProcessingException {
        return empService.createRequest(processDefinitionKey, request);
    }

    @PostMapping("/claim-task/{task-id}")
    public @ResponseBody ApiResponse claimTask(@PathVariable("task-id") String taskId, @RequestParam String userId) throws JSONException {
        return empService.claimTask(taskId, userId);
    }

    @PostMapping("/complete-task/{task-id}")
    public @ResponseBody ApiResponse completeTask(@PathVariable("task-id") String taskId, @RequestBody PurchaseOrder request) throws JSONException {
        return empService.completeTask(taskId, request);
    }

    @GetMapping("/task")
    public @ResponseBody
    List<TaskDefinitionResponse> getTask(@RequestParam("processInstanceId") String processInstanceId, @RequestParam("processInstanceBusinessKey") String businessKey) throws URI.MalformedURIException {
        return empService.getTask(processInstanceId, businessKey);
    }

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @PostMapping("/upload") public ResponseEntity<String> uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        return new ResponseEntity<>(fileNameAndPath.toString(), HttpStatus.OK) ;
    }

}
