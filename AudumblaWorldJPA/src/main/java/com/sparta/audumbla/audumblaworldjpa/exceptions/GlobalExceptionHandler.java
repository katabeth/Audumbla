package com.sparta.audumbla.audumblaworldjpa.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(exception.getMessage(),HttpStatus.NOT_FOUND.toString(),request.getRequestURL().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HasDependantsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleHasDependantsException(HasDependantsException exception, HttpServletRequest request) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(exception.getMessage(),HttpStatus.CONFLICT.toString(),request.getRequestURL().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleDataMismatchFoundException(DataMismatchException exception, HttpServletRequest request) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(exception.getMessage(),HttpStatus.BAD_REQUEST.toString(),request.getRequestURL().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException exception, HttpServletRequest request){
        ErrorResponse<String> errorResponse = new ErrorResponse<>(exception.getMessage(),HttpStatus.CONFLICT.toString(),request.getRequestURL().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidEception(MethodArgumentNotValidException exception, HttpServletRequest request){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
                error -> errors.put(((FieldError)error).getField(), error.getDefaultMessage())
        );
        ErrorResponse<Map<String,String>> errorResponse = new ErrorResponse<>(errors,HttpStatus.BAD_REQUEST.toString(),request.getRequestURL().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    record ErrorResponse<T>(T errorDetails, String errorCode, String url) {}
}
