# ROBLOX STORY AI

Android foundation for an AI-assisted block-style 3D storytelling studio.

## Stack
- Kotlin + Jetpack Compose
- Gemini integration planned through a secure backend
- Unity as a separately versioned 3D runtime, planned for a later phase
- GitHub Actions for CI builds

## Requirements
- JDK 17
- Android SDK Platform 37
- Gradle 9.3.1 (CI installs it automatically)
- Android Studio compatible with AGP 9.4.0

## Build
```bash
gradle testDebugUnitTest
gradle assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

## Package
`com.cart3r.robloxstoryai`

## Current scope
Phase 1 provides the Android app shell, Arabic RTL Compose screens, theme, project configuration and GitHub Actions CI. Gemini, Firebase and Unity are integration phases, not active services in this starter.
