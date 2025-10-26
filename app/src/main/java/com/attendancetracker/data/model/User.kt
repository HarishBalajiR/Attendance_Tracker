package com.attendancetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val studentId: String? = null,
    val department: String? = null,
    val phoneNumber: String? = null,
    val profileImageUrl: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class UserRole {
    ADMIN,
    TEACHER,
    STUDENT
}



