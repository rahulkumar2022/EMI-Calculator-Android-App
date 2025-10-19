# EMI Calculator - Modern Android App

A sleek, futuristic Loan EMI Calculator Android app built with Jetpack Compose and MVVM architecture.

## ✨ Features

- **Instant EMI Calculation**: Calculate monthly EMI, total interest, and total payable amount
- **Visual Breakdown**: Interactive pie chart showing principal vs interest distribution
- **Amortization Schedule**: Detailed month-wise breakdown of EMI payments
- **Calculation History**: Save and view recent calculations with local Room database
- **Modern UI**: Futuristic dark theme with neon cyan/purple accents
- **Smooth Animations**: Polished transitions and animations throughout the app

## 🎨 Design Highlights

- **Futuristic Dark Theme**: Deep dark backgrounds with vibrant neon accents
- **Gradient Buttons**: Eye-catching gradient buttons with interactive animations
- **Rounded Cards**: Modern card-based UI with rounded corners
- **Clean Typography**: System fonts optimized for readability
- **Smooth Transitions**: Seamless navigation with fade and slide animations

## 📱 Screens

1. **Splash Screen**: Animated logo with futuristic gradient background
2. **Home Screen**: Input fields for loan amount, interest rate, and tenure
3. **Result Screen**: EMI display with pie chart visualization
4. **Amortization Schedule**: Month-wise payment breakdown table
5. **History Screen**: View and manage past calculations
6. **Settings Screen**: App information and preferences

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room for local data persistence
- **Navigation**: Jetpack Navigation Compose
- **Charts**: Custom Compose Canvas implementation
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## 📐 EMI Calculation Formula

The app uses the standard EMI formula:

```
EMI = [P × R × (1+R)^N] / [(1+R)^N - 1]

where:
P = Principal loan amount
R = Monthly interest rate (Annual rate / 12 / 100)
N = Loan tenure in months
```

## 🚀 Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Run the app on an emulator or physical device

## 📦 Dependencies

- AndroidX Core KTX
- Jetpack Compose (BOM 2023.08.00)
- Material 3 with extended icons
- Navigation Compose
- Lifecycle & ViewModel
- Room Database with KSP
- DataStore Preferences

## 🎯 Key Components

### Data Layer

- `EMICalculation`: Room entity for storing calculation history
- `EMIResult`: Model for calculation results
- `AmortizationEntry`: Model for schedule entries
- `AppDatabase`: Room database configuration
- `EMIRepository`: Data repository layer

### UI Layer

- `EMIViewModel`: ViewModel managing app state and calculations
- Custom composables: `GradientButton`, `NeonOutlinedTextField`, `PieChart`
- Screen composables: Splash, Home, Result, Amortization, History, Settings

### Utils

- `EMICalculator`: Utility object for EMI and amortization calculations
- Currency formatting functions

## 🎨 Color Scheme

- **Primary Accent**: Neon Cyan (#00F0FF)
- **Secondary Accent**: Purple (#8B5CF6)
- **Background**: Dark (#0A0E1A)
- **Surface**: Dark (#131824)
- **Card**: Dark (#1A2332)

## 📄 License

This project is created for educational and portfolio purposes.

## 👨‍💻 Developer Notes

- All calculations are performed locally - no internet required
- History is stored securely in local Room database
- Edge-to-edge display for modern Android experience
- Optimized animations with spring and tween effects
- Fully responsive layout adapting to different screen sizes

## 🔮 Future Enhancements

- Export calculations as PDF
- Multiple currency support
- Loan comparison feature
- Interest rate trends
- Custom themes
- Dark/Light mode toggle
- Biometric security for history

---

**Made with ❤️ using Jetpack Compose**
# EMI-Calculator-Android-App
