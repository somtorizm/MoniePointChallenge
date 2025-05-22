# MoniePoint Challenge

An Android app built with Kotlin and Jetpack Compose. This project showcases several screens with a splash animation using [Lottie](https://airbnb.io/lottie/#/) and features a bottom navigation bar.

## Tools and SDKs

- **Kotlin** 2.0
- **Jetpack Compose** with Material 3
- **Hilt** for dependency injection
- **Lottie Compose** for animations
- **Android Gradle Plugin** 8.10
- **Compile SDK** 35 (minimum SDK 24)

## Running the project

1. Clone the repository.
2. Provide a Google Maps API key. Edit `local.properties` in the project root and add your key:

   ```
   MAPS_API_KEY=your_key_here
   ```

   If the file does not exist, create it next to `gradlew`.
3. Open the project with the latest Android Studio (Hedgehog or newer).
4. Build or run using the built‑in Gradle tasks or from the command line:

   ```
   ./gradlew assembleDebug
   ```