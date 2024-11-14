package com.booking.api.Controller;

import com.booking.api.Entity.User;
import com.booking.api.Service.UserService;
import com.booking.api.Dto.UserDto;
import com.booking.api.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserDto user) {

        ApiResponse response = new ApiResponse(HttpStatus.CREATED.value(),userService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint to get user profile
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(@RequestParam Long userId) {
        UserDto userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }
}

