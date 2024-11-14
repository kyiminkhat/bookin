package com.booking.api.Service;

import com.booking.api.Entity.ClassSchedule;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class CacheService {

    // In-memory cache to keep track of available slots for each class
    private final ConcurrentHashMap<Long, Integer> classSlotCache = new ConcurrentHashMap<>();

    // In-memory waitlist for each class (tracks users waiting for a slot)
    private final ConcurrentHashMap<Long, ConcurrentLinkedQueue<Long>> classWaitlist = new ConcurrentHashMap<>();

    // Initialize the cache with the class ID and max slots (this could be done when the class is created)
    public void initializeClassSlots(Long classId, int maxSlots) {
        classSlotCache.put(classId, maxSlots);
        classWaitlist.put(classId, new ConcurrentLinkedQueue<>());
    }

    // Check if the class has available slots
    public boolean isClassFull(ClassSchedule classId) {
        return classSlotCache.getOrDefault(classId, 0) <= 0;
    }

    // Decrease the available slots by 1 when a class is booked
    public synchronized boolean bookClass(Long classId) {
        if (classSlotCache.getOrDefault(classId, 0) > 0) {
            // Decrement the slot count and return true if successful
            classSlotCache.put(classId, classSlotCache.get(classId) - 1);
            return true;
        }
        return false;  // No available slots
    }

    // Increase the available slots by 1 when a class is cancelled
    public synchronized void cancelClass(Long classId) {
        // Increase the available slot
        classSlotCache.put(classId, classSlotCache.getOrDefault(classId, 0) + 1);

        // If there are users in the waitlist, book the class for the first user in the waitlist
        ConcurrentLinkedQueue<Long> waitlist = classWaitlist.get(classId);
        if (waitlist != null && !waitlist.isEmpty()) {
            Long userId = waitlist.poll();  // Get the first user in the waitlist
            bookClass(classId);  // Book the class for the user
            // Notify the user that they are successfully booked
            System.out.println("User " + userId + " has been moved from waitlist to booked.");
        }
    }

    // Add user to the waitlist if the class is full
    public void addToWaitlist(Long classId, Long userId) {
        // Add user to the waitlist
        ConcurrentLinkedQueue<Long> waitlist = classWaitlist.getOrDefault(classId, new ConcurrentLinkedQueue<>());
        waitlist.offer(userId);  // Add user to the queue
        classWaitlist.put(classId, waitlist);  // Update the waitlist cache
        System.out.println("User " + userId + " added to the waitlist for class " + classId);
    }
}

