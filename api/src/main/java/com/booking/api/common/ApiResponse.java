package com.booking.api.common;

import lombok.Data;

@Data
public class ApiResponse {
    private String message;
    private int status;
    private String token;
    public ApiResponse( int status,String message) {
        this.message = message;
        this.status = status;
    }

    public ApiResponse( int status,String message, String token) {
        this.message = message;
        this.status = status;
        this.token = token;
    }
}
