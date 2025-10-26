package com.attendancetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attendancetracker.ui.screens.auth.LoginScreen
import com.attendancetracker.ui.screens.dashboard.DashboardScreen
import com.attendancetracker.ui.screens.events.EventsScreen
import com.attendancetracker.ui.screens.scanner.ScannerScreen
import com.attendancetracker.ui.screens.reports.ReportsScreen
import com.attendancetracker.ui.screens.profile.ProfileScreen

@Composable
fun AttendanceNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        
        composable("dashboard") {
            DashboardScreen(
                onNavigateToEvents = { navController.navigate("events") },
                onNavigateToScanner = { navController.navigate("scanner") },
                onNavigateToReports = { navController.navigate("reports") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        
        composable("events") {
            EventsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("scanner") {
            ScannerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("reports") {
            ReportsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("profile") {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}



