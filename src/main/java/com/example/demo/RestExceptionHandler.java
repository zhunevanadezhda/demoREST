package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log= LoggerFactory.getLogger(RestExceptionHandler.class);
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        AppException appExcept = new AppException(status, "Malformed JSON request", ex.toString());
        log.error("Exception:",ex);
        return new ResponseEntity(appExcept, status);
    }
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        log.error("Exception:",ex);
        return new ResponseEntity<Object>(new AppException(status, "no handler found", ex.toString()), HttpStatus.NOT_FOUND);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception:",ex);
        AppException appExcept = new AppException(status, "Method arg not valid", ex.toString());
        appExcept.setValidErrors(ex.getBindingResult().getFieldErrors());
        return new ResponseEntity<Object>(appExcept, BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        log.error("Exception:",ex);
        AppException appExcept = new AppException(BAD_REQUEST);
        appExcept.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        appExcept.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(appExcept, BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(EntityNotFoundException ex, WebRequest request) {
        log.error("Exception:",ex);
        AppException appExcept = new AppException(HttpStatus.NOT_FOUND, "entity not found ex", ex.toString());
        return new ResponseEntity<>(appExcept, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception:",ex);
        AppException appExcept = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "prosto exception", ex.toString());
        return new ResponseEntity<>(appExcept, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
