package com.booking.api.Dto;

import lombok.Data;



@Data
public class PackageDto {
    private Long id;
    private String name;
    private int credits;
    private double price;
    private String country;
    private boolean isActive;

    // Constructor, Getters, and Setters
}
