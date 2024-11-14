package com.booking.api.Controller;

import com.booking.api.Dto.PackageDto;
import com.booking.api.Service.PackageService;
import com.booking.api.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("/all")
    public List<PackageDto> getAllPackages() {
        return packageService.getAllPackages();
    }

    @PostMapping("/purchase")
    public ResponseEntity<ApiResponse> purchasePackage(@RequestParam Long userId, @RequestParam Long packageId) throws Exception {
        ApiResponse response = new ApiResponse(HttpStatus.OK.value(),packageService.purchasePackage(userId, packageId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
