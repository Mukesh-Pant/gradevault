package com.gradevault.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private String error;
    private Object details;
    private String timestamp;
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }
    
    public static <T> ApiResponse<T> error(String error, Object details) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(error)
                .details(details)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }
}
