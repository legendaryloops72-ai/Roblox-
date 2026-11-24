# ROBLOX STORY AI

An Android story studio starter for creating block-world adventures.

## Current app features
- Arabic right-to-left interface built with Kotlin and Jetpack Compose.
- Story prompt entry and four genre presets: adventure, comedy, mystery, and fantasy.
- Offline story-outline generation with four editable-ready scene descriptions.
- Local project library: save, open, and delete up to 50 story projects.
- Unit tests for the story outline generator.

## Build configuration
- Package: `com.cart3r.robloxstoryai`
- Android Gradle Plugin: `9.1.0`
- Gradle: `9.3.1`
- Kotlin: `2.4.10`
- Compose BOM: `2026.09.00`
- JDK: `17`
- compileSdk: `37`
- targetSdk: `36`
- minSdk: `24`

The project currently uses the legacy Kotlin Android Gradle plugin compatibility path for AGP 9.1.0. See `gradle.properties` for the temporary AGP built-in Kotlin opt-out flags.

## CI
GitHub Actions runs unit tests and assembles a debug APK on pushes to `main` and `develop`, and on pull requests targeting `main`. The APK is uploaded as the `roblox-story-ai-debug-apk` workflow artifact.

## Planned integrations
1. Secure backend for Gemini story generation. Never place a Gemini API key in the Android app.
2. Firebase authentication and cloud project sync.
3. Unity as a separately integrated 3D runtime after the Android foundation is stable.
4. Scene editor, character/asset library, preview and MP4 export.

**Important:** The current offline outline generator is a local prototype, not Gemini AI. Unity rendering, cloud sync, and MP4 export are not implemented yet.
