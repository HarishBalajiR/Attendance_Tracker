package com.attendancetracker.ui.screens.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendancetracker.data.model.Attendance
import com.attendancetracker.data.model.AttendanceStatus
import com.attendancetracker.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class ScannerUiState(
    val scanResult: String? = null,
    val statusMessage: String = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    fun onScanResult(result: String) {
        _uiState.value = _uiState.value.copy(scanResult = result)
    }

    fun processScanResult(qrCode: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, statusMessage = "")
            
            try {
                // Find event by QR code
                val event = repository.getEventByQrCode(qrCode)
                
                if (event == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        statusMessage = "Invalid QR code. Event not found.",
                        isSuccess = false
                    )
                    return@launch
                }
                
                // For demo purposes, use the first user as current user
                val users = repository.getAllActiveUsers()
                users.collect { userList ->
                    val currentUser = userList.firstOrNull()
                    
                    if (currentUser == null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            statusMessage = "User not found. Please login again.",
                            isSuccess = false
                        )
                        return@collect
                    }
                    
                    // Check if user already has attendance for this event
                    val existingAttendance = repository.getAttendanceByUserAndEvent(
                        currentUser.id, 
                        event.id
                    )
                    
                    if (existingAttendance == null) {
                        // Check in
                        val attendance = Attendance(
                            id = UUID.randomUUID().toString(),
                            userId = currentUser.id,
                            eventId = event.id,
                            checkInTime = System.currentTimeMillis(),
                            status = AttendanceStatus.PRESENT
                        )
                        
                        repository.insertAttendance(attendance)
                        
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            statusMessage = "Successfully checked in to ${event.name}!",
                            isSuccess = true
                        )
                    } else if (existingAttendance.checkOutTime == null) {
                        // Check out
                        val updatedAttendance = existingAttendance.copy(
                            checkOutTime = System.currentTimeMillis(),
                            status = AttendanceStatus.CHECKED_OUT
                        )
                        
                        repository.updateAttendance(updatedAttendance)
                        
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            statusMessage = "Successfully checked out from ${event.name}!",
                            isSuccess = true
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            statusMessage = "You have already checked in and out of this event.",
                            isSuccess = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    statusMessage = "Error processing QR code: ${e.message}",
                    isSuccess = false
                )
            }
        }
    }

    fun clearStatus() {
        _uiState.value = _uiState.value.copy(
            scanResult = null,
            statusMessage = "",
            isSuccess = false
        )
    }
}



