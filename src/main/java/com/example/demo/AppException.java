package com.example.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.FileHandler;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppException {
    private HttpStatus status;
    private String message;
    private String debugMessage;
    private List<ValidError> ValidErrors;

    public AppException(HttpStatus status, String message, String debugMessage) {
        this.status = status;
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public AppException(HttpStatus status) {
        this.status = status;
    }

    public void setValidErrors(List<FieldError> validErrors) {
        validErrors.forEach(x->
        {
            ValidError error=new ValidError();
            error.setField(x.getField());
            error.setMessage(x.getDefaultMessage());
            error.setObject(x.getObjectName());
            error.setRejectedValue(x.getRejectedValue());
            ValidErrors.add(error);
        });
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<ValidError> getValidErrors() {
        return ValidErrors;
    }

}