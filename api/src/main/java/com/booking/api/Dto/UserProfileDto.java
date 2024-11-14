package com.booking.api.Dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private Long id;
    private String name;
    private String email;
    private String country;

    // Constructor, Getters, and Setters
}
