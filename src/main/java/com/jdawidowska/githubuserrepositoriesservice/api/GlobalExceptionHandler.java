package com.jdawidowska.githubuserrepositoriesservice.api;

import com.jdawidowska.githubuserrepositoriesservice.api.dto.ApiErrorResponse;
import com.jdawidowska.githubuserrepositoriesservice.exception.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonProcessingException(JsonProcessingException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse("500", "Error processing JSON response from GitHub API"));
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ApiErrorResponse> handleRestClientException(RestClientException exception) {
        if(exception.getMessage().contains("404")) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("404", "GitHub user not found"));
        } else if (exception.getMessage().contains("403")) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ApiErrorResponse("429", "GitHub request rate limit exceeded, provide api token for bigger limit"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("500", "Other error"));
        }
    }
}