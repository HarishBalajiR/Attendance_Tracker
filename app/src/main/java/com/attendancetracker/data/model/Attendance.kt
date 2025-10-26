package com.attendancetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "attendance")
@Parcelize
data class Attendance(
    @PrimaryKey
    val id: String,
    val userId: String,
    val eventId: String,
    val checkInTime: Long,
    val checkOutTime: Long? = null,
    val status: AttendanceStatus,
    val notes: String? = null,
    val location: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class AttendanceStatus {
    PRESENT,
    LATE,
    ABSENT,
    CHECKED_OUT
}



