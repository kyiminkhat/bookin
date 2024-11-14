package com.booking.api.Dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String country;
}
