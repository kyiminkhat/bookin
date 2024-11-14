package com.booking.api.Controller;

import com.booking.api.Dto.UserDto;
import com.booking.api.Service.BookingService;
import com.booking.api.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<ApiResponse> bookClass(@RequestParam Long userId, @RequestParam Long classId, @RequestParam Long packageId) {
        ApiResponse response = new ApiResponse(HttpStatus.OK.value(),bookingService.bookClass(userId, classId, packageId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse> cancelBooking(@RequestParam Long bookingId, @RequestParam Long userId) {
        ApiResponse response = new ApiResponse(HttpStatus.OK.value(),bookingService.cancelBooking(bookingId,userId));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}