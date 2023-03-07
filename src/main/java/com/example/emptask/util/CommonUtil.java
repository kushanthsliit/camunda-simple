package com.example.emptask.util;

import org.springframework.http.HttpHeaders;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    public static String createTraceId() {
        SimpleDateFormat dateTime = new SimpleDateFormat("yyyyMMddHHmm");
        return "TRC" + dateTime.format(new Date());
    }

    public HttpHeaders getHeaders(String basicAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", basicAuthToken);
        return headers;
    }

    public String getRequestPayload(){
        String requestPayload =String.format("{\"userId\" : \"%s\"}", "antony_digiratina");
        return requestPayload;
    }

}
