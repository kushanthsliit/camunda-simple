package com.example.emptask.serviceImpl;

import com.example.emptask.dto.*;
import com.example.emptask.repository.TaskRepository;
import com.example.emptask.service.EmpService;
import com.example.emptask.util.CommonUtil;
import com.sun.org.apache.xml.internal.utils.URI;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class EmpServiceImpl implements EmpService {

    @Value("${camunda.engine.rest.authorization}")
    private String basicAuthToken;

    @Value("${camunda.process-definition-key.start.url}")
    private String endpoint;

    @Value("${camunda.task.claim.url}")
    private String claimEndpoint;

    @Value("${camunda.task.complete.url}")
    private String completeEndpoint;

    @Value("${camunda.tasks.process-instance.get.url}")
    private String taskIdEndpoint;

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    TaskRepository taskRepository;

    CommonUtil commonUtil = new CommonUtil();

    HttpEntity<String> httpEntity = new HttpEntity<String>(commonUtil.getRequestPayload(), commonUtil.getHeaders(basicAuthToken));


    private static final Logger LOGGER = LoggerFactory.getLogger(EmpServiceImpl.class);

    @Override
    public ProcessDefinitionResponse createRequest(String processDefinitionKey, EmpRequest request) throws JSONException {
        String requestUrl = endpoint.replace("#key#", processDefinitionKey);


        try{
            ProcessDefinitionResponse response = restTemplate.postForEntity(requestUrl, request, ProcessDefinitionResponse.class).getBody();
            if(response != null){
                String url = taskIdEndpoint.replace("#instanceId#", response.getId()).replace("#businessKey#", response.getBusinessKey());

                try {
                    ResponseEntity<List<TaskDefinitionResponse>> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity,  new ParameterizedTypeReference<List<TaskDefinitionResponse>>() {});
                    List<TaskDefinitionResponse> tasks = result.getBody();
                    LOGGER.info(result.toString());
                    response.setTasks(tasks);

                    Task t = new Task("NEW", "", response.getTasks().get(0).getId(), response.getBusinessKey(), response.getId(), "EMPLOYEE");

                    taskRepository.save(t);
                }
                catch (Exception e){
                    LOGGER.error("", e);
                    return null;
                }
            }
            return response;
        }
        catch (Exception e){
            LOGGER.error("Task Not Created..!", e);
            return null;
        }
    }

    @Override
    public ApiResponse claimTask(String taskId, String userId) throws JSONException {

        String requestUrl = claimEndpoint.replace("#taskId#", taskId);

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
    public ApiResponse completeTask(String taskId, PurchaseOrder request) throws JSONException {

        String requestUrl = completeEndpoint.replace("#taskId#", taskId);

        try {
            restTemplate.postForEntity(requestUrl, request, String.class);
            LOGGER.info("Task Completed..!");
            Task t = taskRepository.findByTaskId(taskId);
            t.setStatus("COMPLETED");
            taskRepository.save(t);

            // Initiate new Instance for Manager
            String url = taskIdEndpoint.replace("#instanceId#", t.getInstanceId()).replace("#businessKey#", t.getBusinessKey());

            try {
                ResponseEntity<List<TaskDefinitionResponse>> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity,  new ParameterizedTypeReference<List<TaskDefinitionResponse>>() {});
                List<TaskDefinitionResponse> tasks = result.getBody();
                LOGGER.info(result.toString());

                Task t1 = new Task("NEW", "", tasks.get(0).getId(), t.getBusinessKey(), t.getInstanceId(), "MANAGER");
                taskRepository.save(t1);
            }
            catch (Exception e){
                LOGGER.error("", e);
            }

            return new ApiResponse(true, "Task Completed..!", HttpStatus.OK.value());
        } catch (Exception e) {
            LOGGER.error("Task Not Completed..!", e);
            return new ApiResponse(false, "Task Not Completed..!", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public List<TaskDefinitionResponse> getTask(String instanceId, String businessKey) throws URI.MalformedURIException {

        String url = taskIdEndpoint.replace("#instanceId#", instanceId).replace("#businessKey#", businessKey);

        try {
            ResponseEntity<List<TaskDefinitionResponse>> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity,  new ParameterizedTypeReference<List<TaskDefinitionResponse>>() {});
            List<TaskDefinitionResponse> tasks = result.getBody();
            LOGGER.info(result.toString());
            return tasks;
        }
        catch (Exception e){
            LOGGER.error("", e);
            return null;
        }
    }



}
