package com.attendancetracker.data.dao

import androidx.room.*
import com.attendancetracker.data.model.Attendance
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Query("SELECT * FROM attendance WHERE userId = :userId ORDER BY checkInTime DESC")
    fun getAttendanceByUser(userId: String): Flow<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE eventId = :eventId ORDER BY checkInTime DESC")
    fun getAttendanceByEvent(eventId: String): Flow<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE userId = :userId AND eventId = :eventId")
    suspend fun getAttendanceByUserAndEvent(userId: String, eventId: String): Attendance?

    @Query("SELECT * FROM attendance WHERE id = :attendanceId")
    suspend fun getAttendanceById(attendanceId: String): Attendance?

    @Query("SELECT * FROM attendance WHERE checkInTime >= :startTime AND checkInTime <= :endTime ORDER BY checkInTime DESC")
    fun getAttendanceInTimeRange(startTime: Long, endTime: Long): Flow<List<Attendance>>

    @Query("SELECT COUNT(*) FROM attendance WHERE eventId = :eventId AND status = :status")
    suspend fun getAttendanceCountByEventAndStatus(eventId: String, status: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: Attendance)

    @Update
    suspend fun updateAttendance(attendance: Attendance)

    @Delete
    suspend fun deleteAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE userId = :userId AND eventId = :eventId AND checkOutTime IS NULL")
    suspend fun getActiveAttendance(userId: String, eventId: String): Attendance?
}



