package com.booking.api.Service;

import com.booking.api.Dto.PackageDto;
import com.booking.api.Entity.Package;
import com.booking.api.Entity.User;
import com.booking.api.Entity.UserPackage;
import com.booking.api.Repo.PackageRepository;
import com.booking.api.Repo.UserPackageRepository;
import com.booking.api.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPackageRepository userPackageRepository;


    public List<PackageDto> getAllPackages() {
        // 1. Fetch all Package entities from the database
        List<Package> packages = packageRepository.findAll();

        // 2. Convert each Package entity to PackageDto
        List<PackageDto> packageDtoList = packages.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        // 3. Return the list of PackageDto
        return packageDtoList;
    }
    private PackageDto convertToDto(Package pkg) {
        PackageDto dto = new PackageDto();
        dto.setId(pkg.getId());
        dto.setName(pkg.getName());
        dto.setPrice(pkg.getPrice());
        dto.setActive(pkg.isActive());
        return dto;
    }
    @Transactional
    public String purchasePackage(Long userId, Long packageId) throws Exception {
        // 1. Retrieve the user and package
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Package pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // 2. Check if the user has enough credits
        try {
            if (user.getCredit() < pkg.getPrice()) {
                throw new Exception("Insufficient credits to purchase the package.");
            }
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
        // 3. Deduct the package price from user's credits
        user.setCredit(user.getCredit() - pkg.getPrice());

        // 4. Create a purchase record
        UserPackage userPackage = new UserPackage();
        userPackage.setUser(user);
        userPackage.setPackage(pkg);
        userPackage.setPurchaseDate(LocalDate.now());

        // 5. Set expiration date (e.g., 30 days from purchase)
        userPackage.setExpirationDate(LocalDate.now().plusDays(30));

        // 6. Save the purchase record and update the user
        userPackageRepository.save(userPackage);
        userRepository.save(user);

        return "Package purchased successfully!";
    }
}
