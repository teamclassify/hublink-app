# Hublink

Hublink is an Android app built with Kotlin and Jetpack Compose that provides user authentication, event creation, image upload, and real-time event listing. It integrates Firebase for authentication and realtime data, and Supabase Storage for hosting uploaded images.

## Tech Stack
- Kotlin, Coroutines, StateFlow
- Jetpack Compose, Material 3, Navigation
- Firebase Authentication, Firebase Realtime Database
- Supabase Storage (via `io.github.jan.supabase` client)

## Project Structure
```
app/src/main/java/com/classify/hublink/
├─ HublinkApplication.kt            # Application class & DI container bootstrap
├─ MainActivity.kt                  # Compose host, auth-driven navigation
├─ config/
│  └─ SupabaseClient.kt            # Supabase client initialization (Storage)
├─ data/
│  ├─ AppContainer.kt              # Simple DI container and interfaces
│  ├─ entities/
│  │  └─ Event.kt                  # Event data model
│  └─ repositories/
│     └─ EventsRepository.kt       # Realtime DB event stream & create
├─ ui/
│  ├─ AppViewModelProvider.kt      # ViewModel factory helpers (if used)
│  ├─ components/                  # Reusable Compose UI components
│  │  ├─ CustomButton.kt
│  │  ├─ ImageUploader.kt
│  │  ├─ Logo.kt
│  │  └─ NavigationBar.kt
│  ├─ screens/                     # Top-level screens
│  │  ├─ Home.kt
│  │  ├─ Loading.kt
│  │  ├─ Login.kt
│  └─ theme/                       # Material theme setup
│     ├─ Color.kt
│     ├─ Dimens.kt
│     ├─ Theme.kt
│     └─ Type.kt
├─ utils/
│  └─ Password.kt                  # Password helpers
└─ viewmodel/
   ├─ AuthViewModel.kt             # Auth state management
   └─ EventViewModel.kt            # Events stream & image upload
```

## Architecture Overview
- UI is built with Jetpack Compose. `MainActivity` hosts the Compose content and applies the `HublinkTheme`.
- Navigation is conditionally driven by authentication state exposed by `AuthViewModel`:
  - Loading → `LoadingScreen`
  - Authenticated → `NavigationBar` (bottom navigation / main app)
  - Idle/Error → NavHost with `LoginScreen` and `RegisterScreen`
- Dependency injection is handled via a lightweight container (`AppContainer` / `AppDataContainer`) initialized in `HublinkApplication`.
- Data flow for events:
  - `EventsRepository` listens to Firebase Realtime Database (`/events`) and exposes a `StateFlow<List<Event>>`.
  - `EventViewModel` collects the flow as a lifecycle-aware `StateFlow` and offers methods to add events.
  - Images are uploaded to Supabase Storage bucket `events`; the resulting public URL can be stored alongside event data.

## Key Components
- Authentication: `AuthViewModel` uses Firebase Auth and exposes `AuthState` sealed class with `Idle`, `Loading`, `Success`, and `Error`.
- Events: `EventsRepository` streams live updates via a `ValueEventListener` and supports `addNewEvent` for authenticated users.
- Image Upload: `EventViewModel.uploadImageToSupabase` reads bytes from a `Uri`, uploads to Supabase Storage, and returns a public URL.

## Configuration
You must set credentials for Firebase and Supabase.

### Firebase
- Add Firebase to the Android project (via Firebase Console), enable Email/Password authentication.
- Add `google-services.json` in `app/` and ensure the Gradle plugin is applied.
- Realtime Database security rules should allow authenticated users to read/write as appropriate for your app.

### Supabase
The Supabase client reads from `BuildConfig` fields in `config/SupabaseClient.kt`:
- `BuildConfig.SUPABASE_URL`
- `BuildConfig.SUPABASE_KEY`

In `local.properties`, add the following:

```kotlin
// local.properties
SUPABASE_URL=xxx
SUPABASE_KEY=xxx
```

In Supabase, create a Storage bucket named `events` and enable public access (or implement signed URLs if required).

## Getting Started
1. Prerequisites
   - Android Studio Ladybug+ (or newer)
   - JDK 17 (matching your Android Gradle Plugin requirements)
   - A configured Firebase project (Auth + Realtime Database)
   - A Supabase project with a public Storage bucket `events`

2. Clone and open in Android Studio
   - Sync Gradle
   - Set up `google-services.json`
   - Configure `BuildConfig` fields for Supabase

3. Run
   - Select a device/emulator and Run the app

## How It Works (Flow)
- App launch initializes `HublinkApplication`, wiring the `AppDataContainer`.
- `MainActivity` sets Compose content and observes `AuthState` from `AuthViewModel`:
  - On `Success` → main navigation UI (`NavigationBar`) is shown.
  - On `Idle`/`Error` → user can login or register.
- `EventsRepository` listens for realtime updates and updates its `StateFlow`.
- `EventViewModel` exposes the events to UI screens and allows adding new events.
- Image upload uses Supabase Storage; the returned URL can be associated with the event.

## Notable Files
- `HublinkApplication.kt`: Initializes the DI container (`AppDataContainer`).
- `MainActivity.kt`: Edge-to-edge setup, Compose root, auth-driven navigation.
- `config/SupabaseClient.kt`: Supabase client initialization with Storage plugin.
- `data/repositories/EventsRepository.kt`: Realtime events stream and `addNewEvent`.
- `viewmodel/AuthViewModel.kt`: Auth state and sign-in/register/sign-out.
- `viewmodel/EventViewModel.kt`: Event stream and image upload.
