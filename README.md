# Attendance Tracker Android App

A modern Android application built with Jetpack Compose for tracking attendance using QR codes. Perfect for hackathons, classrooms, events, and meetings.

## Features

### 🔐 Authentication
- Simple email/password login system
- Demo credentials provided for testing
- Role-based access (Admin, Teacher, Student)

### 📱 Core Functionality
- **QR Code Scanner**: Check in/out using QR codes
- **Event Management**: Create and manage events
- **Real-time Tracking**: Live attendance monitoring
- **Reports & Analytics**: Detailed attendance reports
- **User Profiles**: Comprehensive user management

### 🎨 Modern UI
- Material Design 3 with Jetpack Compose
- Dark/Light theme support
- Responsive and intuitive interface
- Smooth animations and transitions

### 🗄️ Data Management
- Local Room database
- Offline-first architecture
- Data persistence across app sessions

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Database**: Room (SQLite)
- **Dependency Injection**: Hilt
- **Navigation**: Navigation Compose
- **QR Scanning**: ZXing
- **Async Operations**: Coroutines & Flow

## Demo Credentials

The app comes with pre-configured demo accounts:

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@demo.com | admin123 |
| Teacher | teacher@demo.com | teacher123 |
| Student | student@demo.com | student123 |

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (Android 7.0)
- Kotlin 1.9.10+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd attendance-tracker
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project folder

3. **Sync Project**
   - Android Studio will automatically sync the project
   - Wait for Gradle sync to complete

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

### Building from Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## Project Structure

```
app/
├── src/main/java/com/attendancetracker/
│   ├── data/
│   │   ├── database/          # Room database setup
│   │   ├── dao/              # Data Access Objects
│   │   ├── model/            # Data models
│   │   ├── repository/       # Repository pattern
│   │   └── demo/             # Demo data initialization
│   ├── di/                   # Dependency injection
│   ├── ui/
│   │   ├── navigation/       # Navigation setup
│   │   ├── screens/          # UI screens
│   │   └── theme/            # App theming
│   └── MainActivity.kt       # Main activity
└── src/main/res/             # Resources
```

## Key Components

### Screens
- **LoginScreen**: User authentication
- **DashboardScreen**: Main overview with quick actions
- **ScannerScreen**: QR code scanning interface
- **EventsScreen**: Event management
- **ReportsScreen**: Analytics and reports
- **ProfileScreen**: User profile and settings

### Data Models
- **User**: User information and roles
- **Event**: Event details and QR codes
- **Attendance**: Check-in/out records

### Features by Role

#### Admin
- Full access to all features
- Create and manage events
- View all attendance reports
- Manage users

#### Teacher
- Create events for their classes
- View attendance for their events
- Generate reports

#### Student
- Scan QR codes to check in/out
- View their attendance history
- Access event information

## Usage Guide

### For Event Organizers

1. **Login** with admin or teacher credentials
2. **Create Events**:
   - Go to Events screen
   - Tap the "+" button
   - Fill in event details
   - Generate QR code for the event

3. **Monitor Attendance**:
   - View real-time attendance in Reports
   - Export attendance data
   - Track check-in/out times

### For Attendees

1. **Login** with student credentials
2. **Check In**:
   - Go to Scanner screen
   - Point camera at event QR code
   - Confirm check-in

3. **Check Out**:
   - Scan the same QR code again
   - System will automatically check you out

## Customization

### Adding New User Roles
1. Update `UserRole` enum in `User.kt`
2. Modify UI logic in screens as needed
3. Update database schema if required

### Styling
- Colors: `ui/theme/Color.kt`
- Typography: `ui/theme/Type.kt`
- Theme: `ui/theme/Theme.kt`

### Database Changes
- Update models in `data/model/`
- Increment database version in `AttendanceDatabase.kt`
- Add migration logic if needed

## Troubleshooting

### Common Issues

1. **Camera Permission Denied**
   - Go to device Settings > Apps > Attendance Tracker > Permissions
   - Enable Camera permission

2. **QR Code Not Scanning**
   - Ensure good lighting
   - Keep QR code within the scanning frame
   - Check if QR code is valid for an active event

3. **Login Issues**
   - Use the provided demo credentials
   - Check internet connection (if using remote auth)

### Debug Mode
- Enable developer options on your device
- Use Android Studio's debugger
- Check Logcat for error messages

## Future Enhancements

- [ ] Cloud synchronization
- [ ] Push notifications
- [ ] Biometric authentication
- [ ] Advanced analytics dashboard
- [ ] Export to CSV/PDF
- [ ] Multi-language support
- [ ] Offline mode improvements
- [ ] Real-time collaboration

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

---

**Built with ❤️ for the hackathon community**



