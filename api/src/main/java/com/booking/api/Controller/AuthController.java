package com.booking.api.Controller;

import com.booking.api.Dto.LoginRequestDto;
import com.booking.api.Service.AuthenticationService;
import com.booking.api.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDto loginRequest) {
        ApiResponse response = new ApiResponse(HttpStatus.OK.value(),"Login  successfully",authenticationService.authenticateUser(loginRequest));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
