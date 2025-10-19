# Quick Start Guide - EMI Calculator

## 🚀 Getting Started in 3 Steps

### Step 1: Open in Android Studio

1. Launch Android Studio
2. Click "Open" or "Open an Existing Project"
3. Navigate to: `/Users/rahulgupta/Desktop/Android App/EMICalculator`
4. Click "Open"

### Step 2: Sync Dependencies

1. Wait for Gradle sync to complete automatically
2. If prompted, click "Sync Now"
3. This may take a few minutes on first run

### Step 3: Run the App

1. Click the green "Run" button (▶️) in the toolbar
2. Or press `Shift + F10` (Windows/Linux) or `Control + R` (Mac)
3. Select an emulator or connected device
4. Wait for the app to build and install

## 📱 First Time Usage

### On App Launch

1. **Splash Screen** appears with animated logo (2.5 seconds)
2. Auto-navigates to **Home Screen**

### Calculate Your First EMI

1. Enter **Loan Amount** (e.g., 500000)
2. Enter **Interest Rate** (e.g., 8.5)
3. Enter **Tenure in Years** (e.g., 5)
4. Tap **"Calculate EMI"** button

### View Results

1. See your **Monthly EMI** in large cyan text
2. View **Principal** and **Interest** breakdown
3. Check **Total Payable** amount
4. View the **Pie Chart** showing distribution
5. Tap **"View Amortization Schedule"** for month-wise details

### Access History

1. Tap the **History icon** (top-right on Home Screen)
2. View all past calculations
3. Delete individual items with trash icon
4. Clear all with the delete button

## 🎯 Example Calculation

**Input:**

- Loan Amount: ₹1,000,000
- Interest Rate: 9% p.a.
- Tenure: 10 years

**Output:**

- Monthly EMI: ₹12,668
- Total Interest: ₹5,20,141
- Total Payable: ₹15,20,141

## 🛠️ Troubleshooting

### Build Errors?

1. File → Invalidate Caches → Invalidate and Restart
2. Build → Clean Project
3. Build → Rebuild Project

### Sync Issues?

1. Check internet connection
2. File → Sync Project with Gradle Files
3. Update Android Studio if outdated

### Emulator Not Working?

1. Tools → Device Manager
2. Create a new virtual device
3. Recommended: Pixel 6 with Android 13+

## 📋 System Requirements

### Minimum Requirements

- Android Studio Arctic Fox or newer
- JDK 8 or higher
- 4GB RAM (8GB recommended)
- 2GB free disk space

### Target Device

- Android 8.0 (API 26) or higher
- Any screen size supported
- Portrait orientation optimized

## 🎨 App Navigation Map

```
Splash Screen (Auto → Home)
    ↓
Home Screen
    ├→ Calculate → Result Screen
    │                  ↓
    │              Amortization Screen
    │
    └→ History Screen
           ↓
       Settings Screen
```

## 💡 Tips for Best Experience

1. **Valid Inputs:**

   - Loan Amount: Any positive number
   - Interest Rate: 0.1% to 100%
   - Tenure: 1 to 30 years

2. **Navigation:**

   - Use back button to return
   - Swipe for smooth transitions
   - All calculations auto-saved

3. **Features:**
   - History stores up to 20 recent calculations
   - All data stored locally (offline app)
   - No internet required

## 🔍 Feature Highlights

### Home Screen

- ✨ Futuristic dark theme
- 🎯 Input validation
- 💡 Quick tips
- 📊 Instant calculation

### Result Screen

- 📈 Pie chart visualization
- 💰 Detailed breakdown
- 📋 Amortization access
- 🎨 Beautiful gradients

### History

- 📜 All calculations saved
- 🗑️ Easy deletion
- 📊 Quick stats
- ⏰ Timestamped entries

## 🎓 How to Use Each Screen

### Splash Screen

- Just wait 2.5 seconds
- Enjoy the animation!

### Home Screen

1. Fill all three fields
2. Tap Calculate
3. View results instantly
4. Check history anytime

### Result Screen

1. Review EMI amount
2. Check pie chart
3. View total payable
4. Access detailed schedule

### Amortization Screen

1. Scroll through months
2. See principal/interest split
3. Track outstanding balance
4. Plan your payments

### History Screen

1. Browse past calculations
2. Delete unwanted entries
3. Clear all if needed
4. Access settings

### Settings Screen

1. View app info
2. Check version
3. Explore options
4. Rate the app

## 📞 Need Help?

### Common Questions

**Q: Where is my data stored?**
A: Locally on your device in Room database

**Q: Does it work offline?**
A: Yes! No internet required

**Q: Can I export calculations?**
A: Not in current version (future feature)

**Q: Is my data safe?**
A: Yes, all data stays on your device

**Q: How accurate are calculations?**
A: Uses standard EMI formula, 100% accurate

## 🎉 You're Ready!

Open Android Studio, run the app, and start calculating!

**Enjoy your modern EMI Calculator! 💙**

---

_For detailed documentation, see README.md_
_For technical details, see PROJECT_SUMMARY.md_
