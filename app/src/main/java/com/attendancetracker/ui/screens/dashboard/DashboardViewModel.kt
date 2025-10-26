package com.attendancetracker.ui.screens.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendancetracker.data.model.Event
import com.attendancetracker.data.model.User
import com.attendancetracker.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class QuickStat(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color
)

data class QuickAction(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

data class DashboardUiState(
    val currentUser: User? = null,
    val quickStats: List<QuickStat> = emptyList(),
    val quickActions: List<QuickAction> = emptyList(),
    val recentEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // For demo purposes, get the first user as current user
                val users = repository.getAllActiveUsers()
                users.collect { userList ->
                    val currentUser = userList.firstOrNull()
                    
                    // Load recent events
                    val events = repository.getAllActiveEvents()
                    events.collect { eventList ->
                        val recentEvents = eventList.take(5)
                        
                        val quickStats = listOf(
                            QuickStat(
                                title = "Total Events",
                                value = eventList.size.toString(),
                                icon = Icons.Default.Event,
                                color = Color(0xFF2196F3)
                            ),
                            QuickStat(
                                title = "Active Users",
                                value = userList.size.toString(),
                                icon = Icons.Default.People,
                                color = Color(0xFF4CAF50)
                            ),
                            QuickStat(
                                title = "Today's Attendance",
                                value = "0", // This would be calculated from actual attendance data
                                icon = Icons.Default.CheckCircle,
                                color = Color(0xFFFF9800)
                            )
                        )
                        
                        _uiState.value = _uiState.value.copy(
                            currentUser = currentUser,
                            quickStats = quickStats,
                            recentEvents = recentEvents,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun setQuickActions(
        onNavigateToEvents: () -> Unit,
        onNavigateToScanner: () -> Unit,
        onNavigateToReports: () -> Unit,
        onNavigateToProfile: () -> Unit
    ) {
        val quickActions = listOf(
            QuickAction(
                title = "Scan QR",
                description = "Check in/out",
                icon = Icons.Default.QrCodeScanner,
                onClick = onNavigateToScanner
            ),
            QuickAction(
                title = "Events",
                description = "Manage events",
                icon = Icons.Default.Event,
                onClick = onNavigateToEvents
            ),
            QuickAction(
                title = "Reports",
                description = "View analytics",
                icon = Icons.Default.Analytics,
                onClick = onNavigateToReports
            ),
            QuickAction(
                title = "Profile",
                description = "Account settings",
                icon = Icons.Default.Person,
                onClick = onNavigateToProfile
            )
        )
        
        _uiState.value = _uiState.value.copy(quickActions = quickActions)
    }
}



