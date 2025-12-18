package org.example.week10day4project3.Advice;

import org.example.week10day4project3.Api.ApiException;
import org.example.week10day4project3.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> ApiException(ApiException e) {
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }

}
