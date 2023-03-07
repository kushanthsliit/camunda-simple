package com.example.emptask.dto;

public class ApiResponse {

    private Boolean success;
    private String response;
    private int statusCode;

    public ApiResponse(Boolean success, String response, int statusCode) {
        this.success = success;
        this.response = response;
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
