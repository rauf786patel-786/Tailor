# Bombaywala Tailor Bharuch - Android Studio Project

Native Android tailoring management app built with **Kotlin**, **Jetpack Compose (Material 3)**, and **Room Local Database**.

## Shop Details
- **Shop Name**: Bombaywala Tailor Bharuch
- **Speciality**: Gents & Ladies Specialist
- **Location**: Station Road, Bharuch - 392001, Gujarat, India
- **Operating Mode**: 100% Offline (No Internet, No Login required)

## Key Technical Features
1. **Offline Room Database**:
   - `customers` table for Name, 10-digit mobile, address, notes, timestamps.
   - `measurements` table with foreign key cascade linking multiple measurement records per customer.
2. **Ladies Measurements**:
   - Bust, Waist, Hip, Shoulder, Sleeve length, Blouse length, Kameez length, Salwar length, Neck design notes, Special measurement notes.
3. **Gents Measurements**:
   - Chest, Waist, Hip, Shoulder, Sleeve length, Shirt length, Trouser length, Neck, Special measurement notes.
4. **Search**: Instant reactive Coroutines Flow search by Name or Mobile number.
5. **Backup & Restore**: GSON-based JSON export/import to device storage.

## How to Open in Android Studio
1. Open **Android Studio** (Hedgehog, Iguana, Koala, or Ladybug).
2. Select **File > Open...** and select the `android` directory.
3. Allow Gradle to sync.
4. Run on an Android emulator or connect your Android device (Android 7.0 / API 24+).
