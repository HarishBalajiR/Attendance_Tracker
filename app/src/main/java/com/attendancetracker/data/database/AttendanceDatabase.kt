package com.attendancetracker.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.attendancetracker.data.dao.AttendanceDao
import com.attendancetracker.data.dao.EventDao
import com.attendancetracker.data.dao.UserDao
import com.attendancetracker.data.model.Attendance
import com.attendancetracker.data.model.Event
import com.attendancetracker.data.model.User

@Database(
    entities = [User::class, Event::class, Attendance::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        @Volatile
        private var INSTANCE: AttendanceDatabase? = null

        fun getDatabase(context: Context): AttendanceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AttendanceDatabase::class.java,
                    "attendance_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}



