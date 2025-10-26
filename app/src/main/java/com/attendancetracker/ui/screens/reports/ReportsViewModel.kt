package com.attendancetracker.ui.screens.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendancetracker.data.model.Attendance
import com.attendancetracker.data.model.Event
import com.attendancetracker.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportsUiState(
    val totalEvents: Int = 0,
    val totalAttendance: Int = 0,
    val presentCount: Int = 0,
    val absentCount: Int = 0,
    val recentAttendance: List<Attendance> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadReportsData()
    }

    private fun loadReportsData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Load events count
                repository.getAllActiveEvents().collect { events ->
                    val totalEvents = events.size
                    
                    // Load attendance data
                    repository.getAttendanceInTimeRange(
                        startTime = 0L,
                        endTime = System.currentTimeMillis()
                    ).collect { attendanceList ->
                        val totalAttendance = attendanceList.size
                        val presentCount = attendanceList.count { 
                            it.status == com.attendancetracker.data.model.AttendanceStatus.PRESENT 
                        }
                        val absentCount = attendanceList.count { 
                            it.status == com.attendancetracker.data.model.AttendanceStatus.ABSENT 
                        }
                        val recentAttendance = attendanceList.take(10)
                        
                        _uiState.value = _uiState.value.copy(
                            totalEvents = totalEvents,
                            totalAttendance = totalAttendance,
                            presentCount = presentCount,
                            absentCount = absentCount,
                            recentAttendance = recentAttendance,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun refreshReports() {
        loadReportsData()
    }
}



