package com.booking.api.Dto;

import lombok.Data;

@Data
public class PaymentCardDto {
    private Long userId;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;

    // Getters and setters
}
