package com.example.emptask.dto;

public enum TaskStatus {

    NEW("NEW"),
    ASSIGNED("ASSIGNED"),
    COMPLETED("COMPLETED");

    private String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
