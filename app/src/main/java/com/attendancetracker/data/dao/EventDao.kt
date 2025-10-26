package com.attendancetracker.data.dao

import androidx.room.*
import com.attendancetracker.data.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events WHERE isActive = 1 ORDER BY startTime DESC")
    fun getAllActiveEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): Event?

    @Query("SELECT * FROM events WHERE qrCode = :qrCode AND isActive = 1")
    suspend fun getEventByQrCode(qrCode: String): Event?

    @Query("SELECT * FROM events WHERE createdBy = :userId AND isActive = 1 ORDER BY startTime DESC")
    fun getEventsByCreator(userId: String): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE startTime >= :startTime AND endTime <= :endTime AND isActive = 1")
    fun getEventsInTimeRange(startTime: Long, endTime: Long): Flow<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("UPDATE events SET isActive = 0 WHERE id = :eventId")
    suspend fun deactivateEvent(eventId: String)
}



