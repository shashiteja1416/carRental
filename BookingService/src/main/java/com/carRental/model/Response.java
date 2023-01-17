package com.carRental.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author pteja
 * Model for Response to be shown as Output of Rest Call
 */
@Setter
@Getter
public class Response
{
    // Http code
    private HttpStatus statusCode;
    // message from print stack trace
    private String message;
    // response data
    private Map<String, ?> data;

    public Response(HttpStatus statusCode, String message, Map<String, ?> data) {
        super();
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
    public Response() {
    }
}