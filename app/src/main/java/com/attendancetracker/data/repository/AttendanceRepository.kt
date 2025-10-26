package com.attendancetracker.data.repository

import com.attendancetracker.data.dao.AttendanceDao
import com.attendancetracker.data.dao.EventDao
import com.attendancetracker.data.dao.UserDao
import com.attendancetracker.data.model.Attendance
import com.attendancetracker.data.model.Event
import com.attendancetracker.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(
    private val userDao: UserDao,
    private val eventDao: EventDao,
    private val attendanceDao: AttendanceDao
) {
    // User operations
    fun getAllActiveUsers(): Flow<List<User>> = userDao.getAllActiveUsers()
    suspend fun getUserById(userId: String): User? = userDao.getUserById(userId)
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
    fun getUsersByRole(role: String): Flow<List<User>> = userDao.getUsersByRole(role)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun deactivateUser(userId: String) = userDao.deactivateUser(userId)

    // Event operations
    fun getAllActiveEvents(): Flow<List<Event>> = eventDao.getAllActiveEvents()
    suspend fun getEventById(eventId: String): Event? = eventDao.getEventById(eventId)
    suspend fun getEventByQrCode(qrCode: String): Event? = eventDao.getEventByQrCode(qrCode)
    fun getEventsByCreator(userId: String): Flow<List<Event>> = eventDao.getEventsByCreator(userId)
    fun getEventsInTimeRange(startTime: Long, endTime: Long): Flow<List<Event>> = 
        eventDao.getEventsInTimeRange(startTime, endTime)
    suspend fun insertEvent(event: Event) = eventDao.insertEvent(event)
    suspend fun updateEvent(event: Event) = eventDao.updateEvent(event)
    suspend fun deleteEvent(event: Event) = eventDao.deleteEvent(event)
    suspend fun deactivateEvent(eventId: String) = eventDao.deactivateEvent(eventId)

    // Attendance operations
    fun getAttendanceByUser(userId: String): Flow<List<Attendance>> = 
        attendanceDao.getAttendanceByUser(userId)
    fun getAttendanceByEvent(eventId: String): Flow<List<Attendance>> = 
        attendanceDao.getAttendanceByEvent(eventId)
    suspend fun getAttendanceByUserAndEvent(userId: String, eventId: String): Attendance? = 
        attendanceDao.getAttendanceByUserAndEvent(userId, eventId)
    suspend fun getAttendanceById(attendanceId: String): Attendance? = 
        attendanceDao.getAttendanceById(attendanceId)
    fun getAttendanceInTimeRange(startTime: Long, endTime: Long): Flow<List<Attendance>> = 
        attendanceDao.getAttendanceInTimeRange(startTime, endTime)
    suspend fun getAttendanceCountByEventAndStatus(eventId: String, status: String): Int = 
        attendanceDao.getAttendanceCountByEventAndStatus(eventId, status)
    suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insertAttendance(attendance)
    suspend fun updateAttendance(attendance: Attendance) = attendanceDao.updateAttendance(attendance)
    suspend fun deleteAttendance(attendance: Attendance) = attendanceDao.deleteAttendance(attendance)
    suspend fun getActiveAttendance(userId: String, eventId: String): Attendance? = 
        attendanceDao.getActiveAttendance(userId, eventId)
}



