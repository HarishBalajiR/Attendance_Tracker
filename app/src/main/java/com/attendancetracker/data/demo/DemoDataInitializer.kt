package com.attendancetracker.data.demo

import com.attendancetracker.data.model.Event
import com.attendancetracker.data.model.User
import com.attendancetracker.data.model.UserRole
import com.attendancetracker.data.repository.AttendanceRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoDataInitializer @Inject constructor(
    private val repository: AttendanceRepository
) {
    
    suspend fun initializeDemoData() {
        // Initialize demo users
        initializeDemoUsers()
        
        // Initialize demo events
        initializeDemoEvents()
    }
    
    private suspend fun initializeDemoUsers() {
        val existingAdmin = repository.getUserByEmail("admin@demo.com")
        if (existingAdmin == null) {
            val admin = User(
                id = UUID.randomUUID().toString(),
                name = "Admin User",
                email = "admin@demo.com",
                role = UserRole.ADMIN,
                department = "IT"
            )
            repository.insertUser(admin)
        }
        
        val existingTeacher = repository.getUserByEmail("teacher@demo.com")
        if (existingTeacher == null) {
            val teacher = User(
                id = UUID.randomUUID().toString(),
                name = "John Teacher",
                email = "teacher@demo.com",
                role = UserRole.TEACHER,
                department = "Computer Science"
            )
            repository.insertUser(teacher)
        }
        
        val existingStudent = repository.getUserByEmail("student@demo.com")
        if (existingStudent == null) {
            val student = User(
                id = UUID.randomUUID().toString(),
                name = "Jane Student",
                email = "student@demo.com",
                role = UserRole.STUDENT,
                studentId = "STU001",
                department = "Computer Science"
            )
            repository.insertUser(student)
        }
    }
    
    private suspend fun initializeDemoEvents() {
        val existingEvents = repository.getAllActiveEvents()
        existingEvents.collect { events ->
            if (events.isEmpty()) {
                val now = System.currentTimeMillis()
                val oneHour = 60 * 60 * 1000L
                
                val events = listOf(
                    Event(
                        id = UUID.randomUUID().toString(),
                        name = "Android Development Workshop",
                        description = "Learn modern Android development with Jetpack Compose",
                        startTime = now + oneHour,
                        endTime = now + (3 * oneHour),
                        location = "Room 101",
                        qrCode = "EVENT_ANDROID_001",
                        createdBy = "admin" // This will be updated with actual user ID
                    ),
                    Event(
                        id = UUID.randomUUID().toString(),
                        name = "Team Meeting",
                        description = "Weekly team standup and planning",
                        startTime = now + (2 * oneHour),
                        endTime = now + (3 * oneHour),
                        location = "Conference Room A",
                        qrCode = "EVENT_MEETING_002",
                        createdBy = "admin"
                    ),
                    Event(
                        id = UUID.randomUUID().toString(),
                        name = "Hackathon Kickoff",
                        description = "Introduction to the hackathon challenge",
                        startTime = now + (4 * oneHour),
                        endTime = now + (6 * oneHour),
                        location = "Main Hall",
                        qrCode = "EVENT_HACKATHON_003",
                        createdBy = "admin"
                    )
                )
                
                events.forEach { event ->
                    repository.insertEvent(event)
                }
            }
        }
    }
}



