package com.attendancetracker.di

import android.content.Context
import com.attendancetracker.data.dao.AttendanceDao
import com.attendancetracker.data.dao.EventDao
import com.attendancetracker.data.dao.UserDao
import com.attendancetracker.data.database.AttendanceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAttendanceDatabase(@ApplicationContext context: Context): AttendanceDatabase {
        return AttendanceDatabase.getDatabase(context)
    }

    @Provides
    fun provideUserDao(database: AttendanceDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideEventDao(database: AttendanceDatabase): EventDao {
        return database.eventDao()
    }

    @Provides
    fun provideAttendanceDao(database: AttendanceDatabase): AttendanceDao {
        return database.attendanceDao()
    }
}



