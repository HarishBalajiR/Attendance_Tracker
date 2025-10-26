package com.attendancetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "events")
@Parcelize
data class Event(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String? = null,
    val startTime: Long,
    val endTime: Long,
    val location: String? = null,
    val qrCode: String,
    val createdBy: String, // User ID
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable



