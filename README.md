# MoniePoint Challenge

MoniePoint Challenge is a production-grade Android application engineered in Kotlin and Jetpack Compose. Inspired by the [Dribble: Movemate Shipments Mobile App](https://dribbble.com/shots/21617837-Movemate-Shipments-Mobile-App), it leverages a unidirectional data flow (MVI) architecture, Hilt for dependency injection, and Material 3 theming to deliver pixel‑perfect screens with hardware‑accelerated transitions. The UI harnesses vector‑based Lottie animations, custom MotionLayout sequences, and asynchronous image loading with Coil to create fluid, interactive experiences. A dynamic bottom navigation bar responds to navigation state with smooth crossfade and slide animations orchestrated via Compose’s animation APIs. Adhering to SOLID principles, state hoisting, side‑effect management with LaunchedEffect. This codebase exemplifies scalability, maintainability, and production readiness.


|                                                         Shipment History Screen                                                        |                                                         Home Screen                                                        |                                                         Map Screen                                                        |
| :--------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: |
| <img src="https://github.com/user-attachments/assets/d0b711fe-6311-44d8-a302-6122c6b7e0ee" alt="Splash Screen" width="200"/> | <img src="https://github.com/user-attachments/assets/79053c45-8440-4dc7-aa56-05b5ac356857" alt="Home Screen" width="200"/> | <img src="https://github.com/user-attachments/assets/2ca821fa-d926-46a4-b218-ee523637d104" alt="Profile Screen" width="200"/> |

## Tools and SDKs

* **Kotlin** 2.0
* **Jetpack Compose** with Material 3
* **Hilt** for dependency injection
* **Lottie Compose** for animations
* **Android Gradle Plugin** 8.10
* **Compile SDK** 35 (minimum SDK 24)

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
