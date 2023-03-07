package com.example.emptask.serviceImpl;

import com.example.emptask.dto.ApiResponse;
import com.example.emptask.dto.PurchaseOrder;
import com.example.emptask.dto.Task;
import com.example.emptask.dto.TaskDefinitionResponse;
import com.example.emptask.repository.TaskRepository;
import com.example.emptask.service.EmpService;
import com.example.emptask.service.ManagerService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    EmpService empService;

    @Value("${camunda.task.complete.url}")
    private String completeEndpoint;

    @Value("${polimit}")
    private Integer pOLimit;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RestTemplate restTemplate ;

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerServiceImpl.class);

    @Override
    public ApiResponse claimTask(String taskId, String userId) throws JSONException {
        return empService.claimTask(taskId, userId);
    }

    @Override
    public ApiResponse completeTask(String taskId, PurchaseOrder purchaseOrder) throws JSONException {

        String requestUrl = completeEndpoint.replace("#taskId#", taskId);

        Map<String, String> variables = new HashMap<>();
        variables.put("item", purchaseOrder.getItem());
        variables.put("amount", String.valueOf(purchaseOrder.getAmount()));

        JSONObject jsonObject = new JSONObject();
        JSONObject jo1 = new JSONObject();
        JSONObject jo2 = new JSONObject();
        JSONObject jo3 = new JSONObject();

        jo1.put("value", purchaseOrder.getItem());
        jo2.put("value", purchaseOrder.getAmount());

        jo3.put("item", jo1);
        jo3.put("amount", jo2);

        jsonObject.put("variables", jo3);

        HttpEntity<String> requestPayloadEntity = new HttpEntity<String>( jsonObject.toString(), getHeaders());

        try {
            String response = restTemplate.postForEntity(requestUrl, requestPayloadEntity, String.class).getBody();
            System.out.println(jsonObject);
            LOGGER.info("Task Completed..!");

                Task t = taskRepository.findByTaskId(taskId);
                t.setStatus("COMPLETED");
                taskRepository.save(t);
                if(purchaseOrder.getAmount() >= 100000) {
                    List<TaskDefinitionResponse> td = empService.getTask(t.getInstanceId(), t.getBusinessKey());
                    Task t1 = new Task("NEW", "", td.get(0).getId(), t.getBusinessKey(), t.getInstanceId(), "CEO");
                    taskRepository.save(t1);
                }

            return new ApiResponse(true, "Task Completed..!", HttpStatus.OK.value());
        } catch (Exception e) {
            LOGGER.error("Task Not Completed..!", e);
            return new ApiResponse(false, "Task Not Completed..!", HttpStatus.BAD_REQUEST.value());
        }
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
