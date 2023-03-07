package com.example.emptask.serviceImpl;

import com.example.emptask.dto.ApiResponse;
import com.example.emptask.dto.PurchaseOrder;
import com.example.emptask.dto.Task;
import com.example.emptask.repository.TaskRepository;
import com.example.emptask.service.CeoService;
import com.example.emptask.service.EmpService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CeoServiceImpl implements CeoService {
    @Autowired
    EmpService empService;

    @Value("${camunda.task.claim.url}")
    private String claimEndpoint;

    @Value("${camunda.engine.rest.authorization}")
    private String basicAuthToken;

    @Value("${camunda.task.complete.url}")
    private String completeEndpoint;

    @Value("${camunda.tasks.process-instance.get.url}")
    private String taskIdEndpoint;

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    TaskRepository taskRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CeoServiceImpl.class);

    @Override
    public ApiResponse claimTask(String taskId, String userId) throws JSONException {
        String requestUrl = claimEndpoint.replace("#taskId#", taskId);
        HttpHeaders headers = getHeaders(basicAuthToken);
        String requestPayload =String.format("{\"userId\" : \"%s\"}", userId);
        HttpEntity<String> httpEntity = new HttpEntity<String>(requestPayload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
            LOGGER.info(response.getBody());

            Task t = taskRepository.findByTaskId(taskId);
            t.setStatus("ASSIGNED");
            t.setAssignee(userId);

            taskRepository.save(t);

            return new ApiResponse(true, "Task Claimed..!", HttpStatus.OK.value());
        } catch (Exception e) {
            LOGGER.error("Task Not Claimed..!", e);
            return new ApiResponse(false, "Task Not Claimed..!", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public ApiResponse completeTask(String taskId, PurchaseOrder request, String instanceId, String businessKey) throws JSONException {
        String requestUrl = completeEndpoint.replace("#taskId#", taskId);

        try {
            restTemplate.postForEntity(requestUrl, request, String.class);
            LOGGER.info("Task Completed..!");
            Task t = taskRepository.findByTaskId(taskId);
            t.setStatus("COMPLETED");
            taskRepository.save(t);

            return new ApiResponse(true, "Task Completed..!", HttpStatus.OK.value());
        } catch (Exception e) {
            LOGGER.error("Task Not Completed..!", e);
            return new ApiResponse(false, "Task Not Completed..!", HttpStatus.BAD_REQUEST.value());
        }
    }

    protected HttpHeaders getHeaders(String basicAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", basicAuthToken);
        return headers;
    }
}
