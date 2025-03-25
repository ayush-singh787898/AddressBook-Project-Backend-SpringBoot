//package com.example.AddressBook.Exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.NoSuchElementException;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Handle specific exceptions
//
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("message", ex.getMessage());
//        errorResponse.put("details", request.getDescription(false));
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("message", ex.getMessage());
//        errorResponse.put("details", request.getDescription(false));
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errors.put(error.getField(), error.getDefaultMessage());
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
//
//    // Handle generic exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("message", "An unexpected error occurred.");
//        errorResponse.put("details", request.getDescription(false));
//        // Log the exception for debugging purposes
//        ex.printStackTrace();
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}