package com.booking.api.Repo;

import com.booking.api.Entity.Booking;
import com.booking.api.Entity.ClassSchedule;
import com.booking.api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Find bookings by user
    @Query(value = "SELECT * FROM bookings WHERE user_id = :userId", nativeQuery = true)
    List<Booking> findByUser(User user);

    // Find bookings for a specific class schedule
    @Query(value = "SELECT * FROM bookings WHERE class_schedule_id = :classScheduleId", nativeQuery = true)
    List<Booking> findByClassSchedule(ClassSchedule classSchedule);

    // Find bookings for a specific class schedule and status
    @Query(value = "SELECT * FROM bookings WHERE class_schedule_id = :classScheduleId AND is_cancelled = :isCancelled", nativeQuery = true)
    List<Booking> findByClassScheduleAndIsCancelled(ClassSchedule classSchedule, boolean isCancelled);

    // Find a booking by user and class schedule
    @Query(value = "SELECT * FROM booking b WHERE b.user_id = :userId AND b.class_schedule_id = :classScheduleId", nativeQuery = true)
    Booking findByUserAndClassSchedule(User user, ClassSchedule classSchedule);
}
