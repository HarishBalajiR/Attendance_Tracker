package com.attendancetracker.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendancetracker.data.model.User
import com.attendancetracker.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val currentUser: User? = null,
    val eventsAttended: Int = 0,
    val totalHours: Int = 0,
    val isLoading: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Get current user (for demo, get first user)
                repository.getAllActiveUsers().collect { users ->
                    val currentUser = users.firstOrNull()
                    
                    if (currentUser != null) {
                        // Load attendance data for the user
                        repository.getAttendanceByUser(currentUser.id).collect { attendanceList ->
                            val eventsAttended = attendanceList.size
                            val totalHours = calculateTotalHours(attendanceList)
                            
                            _uiState.value = _uiState.value.copy(
                                currentUser = currentUser,
                                eventsAttended = eventsAttended,
                                totalHours = totalHours,
                                isLoading = false
                            )
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun calculateTotalHours(attendanceList: List<com.attendancetracker.data.model.Attendance>): Int {
        return attendanceList.sumOf { attendance ->
            if (attendance.checkOutTime != null) {
                val duration = attendance.checkOutTime - attendance.checkInTime
                (duration / (1000 * 60 * 60)).toInt() // Convert to hours
            } else {
                0
            }
        }
    }

    fun refreshProfile() {
        loadProfileData()
    }
}



