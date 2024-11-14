package com.booking.api.Service;

import com.booking.api.Dto.PaymentCardDto;
import com.booking.api.Entity.Package;
import com.booking.api.Entity.PaymentCard;
import com.booking.api.Entity.User;
import com.booking.api.Entity.UserPackage;
import com.booking.api.Repo.PackageRepository;
import com.booking.api.Repo.UserPackageRepository;
import com.booking.api.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class PaymentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    UserPackageRepository userPackageRepository;


    public boolean addPaymentCard(PaymentCardDto cardDto) throws Exception {
        // 1. Validate card number format (e.g., 16-digit number)
        try {
            if (!isValidCardNumber(cardDto.getCardNumber())) {
                throw new IllegalArgumentException("Invalid card number format.");


            }

            // 2. Validate expiration date (should be in the future)
            if (!isValidExpirationDate(cardDto.getExpirationDate())) {
                throw new IllegalArgumentException("Card expiration date is invalid.");


            }
         }catch(Exception e){
        throw  new IllegalArgumentException(e.getMessage());
    }
        // 3. Mask the card number (e.g., store only the last 4 digits)
        String maskedCardNumber = maskCardNumber(cardDto.getCardNumber());

        // 4. Save card details (excluding CVV) in the database
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setUserId(cardDto.getUserId());
        paymentCard.setCardNumber(maskedCardNumber);
        paymentCard.setCardHolderName(cardDto.getCardHolderName());
        paymentCard.setExpirationDate(cardDto.getExpirationDate());

        // Mock saving to database (replace with actual repository call)
        System.out.println("Saving payment card details: " + paymentCard);

        // 5. Return success
        return true;
    }

    public boolean paymentCharge(Object parameters) {
        System.out.println("Mock: Charging payment");
        return true;
    }
    private boolean isValidCardNumber(String cardNumber) {
        // Simple regex for 16-digit card number
        return Pattern.matches("\\d{16}", cardNumber);
    }

    private boolean isValidExpirationDate(String expirationDate) {
        // Assuming expiration date format is MM/YY
        try {
            String[] parts = expirationDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt("20" + parts[1]);
            LocalDate expiry = LocalDate.of(year, month, 1);
            return expiry.isAfter(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    private String maskCardNumber(String cardNumber) {
        // Mask all but the last 4 digits
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}
