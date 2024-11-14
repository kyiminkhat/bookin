package com.booking.api.Repo;

import com.booking.api.Entity.Waitlist;
import com.booking.api.Entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitlistRepository extends JpaRepository<Waitlist, Long> {

    // Fetch the first user from the waitlist for a given class (FIFO order)
    @Query(value = "SELECT * FROM waitlist WHERE class_schedule_id = :classScheduleId ORDER BY date_added ASC LIMIT 1", nativeQuery = true)
    Waitlist findFirstByClassScheduleOrderByDateAdded(ClassSchedule classSchedule);

    // Find all waitlisted users for a specific class
    @Query(value = "SELECT * FROM waitlist WHERE class_schedule_id = :classScheduleId", nativeQuery = true)
    List<Waitlist> findByClassSchedule(ClassSchedule classSchedule);

    // Remove a waitlisted user by their ID and class schedule
    @Query(value = "DELETE FROM waitlist WHERE user_id = :userId AND class_schedule_id = :classScheduleId", nativeQuery = true)
    void deleteByUserIdAndClassScheduleId(Long userId, Long classScheduleId);
}
