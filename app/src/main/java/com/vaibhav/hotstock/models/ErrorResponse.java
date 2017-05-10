package com.vaibhav.hotstock.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vaibhav on 9/5/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private String message;

    private String timestamp;

    private String error;

    private String status;

    private String exception;

    private String path;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
