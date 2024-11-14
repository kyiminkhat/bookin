package com.booking.api.Repo;

import com.booking.api.Entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
}