package com.booking.api.Service;

import com.booking.api.Entity.Booking;
import com.booking.api.Entity.ClassSchedule;
import com.booking.api.Entity.Package;
import com.booking.api.Entity.User;
import com.booking.api.Repo.ClassScheduleRepository;
import com.booking.api.Repo.PackageRepository;
import com.booking.api.Repo.UserRepository;
import com.booking.api.Repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private CacheService cacheService;  // Cache service for concurrency control

    public String bookClass(Long userId, Long classId, Long packageId) {
        // Fetch the user and class schedule
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ClassSchedule classSchedule = classScheduleRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));

        // Check if the class is full using cache (for concurrency control)
        if (cacheService.isClassFull(classSchedule)) {
            return "Class is full, please join the waitlist.";
        }

        // Find the package selected by the user
        Package userPackage = packageRepository.findById(packageId).orElseThrow(() -> new RuntimeException("Package not found"));

        // Check if the user has enough credits in their selected package
        if (userPackage.getCredits() <= 0) {
            return "Insufficient credits in the selected package.";
        }

        // Try to book the class (decrement the available slots)
        if (!cacheService.bookClass(classId)) {
            return "Class is already full, please try again later.";
        }

        // Create a new booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setClassSchedule(classSchedule);
        booking.setUserPackage(userPackage);  // Set the correct package here
        bookingRepository.save(booking);

        // Deduct 1 credit from the user's package
        userPackage.setCredits(userPackage.getCredits() - 1);  // Decrease credits by 1
        packageRepository.save(userPackage);  // Save the updated package back to the database

        return "Class booked successfully!";
    }
    public String cancelBooking(Long bookingId, Long userId) {
        // Fetch the booking details
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

        // Ensure the user is the one who made the booking
        if (!booking.getUser().getId().equals(userId)) {
            return "You cannot cancel someone else's booking.";
        }

        // Fetch the class schedule and get its start time
        ClassSchedule classSchedule = booking.getClassSchedule();
        LocalDateTime classStartTime = classSchedule.getStartTime();

        // Get current time
        LocalDateTime currentTime = LocalDateTime.now();

        // Check if the cancellation is within 4 hours of the class start time
        Duration duration = Duration.between(currentTime, classStartTime);
        long hoursRemaining = duration.toHours();

        // If the class is starting within 4 hours, no refund is issued
        if (hoursRemaining < 4) {
            booking.setCancelled(true);
            bookingRepository.save(booking);
            return "Booking cancelled. No refund issued because it's within 4 hours of the class start time.";
        }

        // If the cancellation is more than 4 hours before class, refund the credit
        Package userPackage = booking.getUserPackage();
        if (userPackage != null) {
            // Refund 1 credit to the user's package
            userPackage.setCredits(userPackage.getCredits() + 1); // Add 1 credit back
            packageRepository.save(userPackage);  // Save updated package
        }

        // Mark the booking as cancelled
        booking.setCancelled(true);
        bookingRepository.save(booking);

        // Optionally send an email notification
        emailService.sendCancellationEmail(booking.getUser().getEmail(), booking.getClassSchedule().getClassName());

        return "Booking cancelled successfully. Credit refunded.";
    }
    private long getTimeDifferenceInHours(Date classTime, Date currentTime) {
        long diffInMillis = classTime.getTime() - currentTime.getTime();
        return diffInMillis / (60 * 60 * 1000);  // Convert milliseconds to hours
    }
}
