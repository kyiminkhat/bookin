package com.booking.api.Service;

import com.booking.api.Entity.User;
import com.booking.api.Dto.UserDto;
import com.booking.api.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired(required = true)
    private BCryptPasswordEncoder passwordEncoder; // For password encryption

    @Autowired
    private EmailService emailService;

    public String registerUser(UserDto userDto) {
        try {
            if (userRepository.existsByEmail(userDto.getEmail())) {

                throw new IllegalArgumentException("Email already in use! Please choose a different email.");

            }
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
            // 3. Encrypt the password
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());

            // 4. Create a new User entity and populate it with data from userDto
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getName());
            user.setCountry(userDto.getCountry());
            user.setPassword(encodedPassword);

            // 5. Save the user to the database
            userRepository.save(user);

            // 6. Send email verification (optional, mock example)
            emailService.sendVerificationEmail(user);


        return  "User registered successfully";
    }
    // Get user profile by userId
    public UserDto getUserProfile(Long userId) {
        // Fetch the user entity from the database
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Convert the User entity to a UserDto
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setCountry(user.getCountry());

        return userDto;
    }


}
