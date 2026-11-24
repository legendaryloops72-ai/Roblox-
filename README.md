# ROBLOX STORY AI

An Android story studio for creating cinematic block-world films.

## Current Android features
- Arabic right-to-left Kotlin and Jetpack Compose interface.
- Story prompt entry and four genre presets: adventure, comedy, mystery and fantasy.
- Offline story-outline prototype with editable scene descriptions.
- Local project library with save, open and delete.
- Unit tests for the offline story outline generator.

## Build configuration
- Package: com.cart3r.robloxstoryai
- Android Gradle Plugin: 9.1.0; Gradle: 9.3.1; Kotlin: 2.4.10
- JDK 17; compileSdk 37; targetSdk 36; minSdk 24

## Cinematic production target
- Final format: landscape 16:9, 1920x1080, 30 fps.
- Unity 6.5 owns voxel environments, block characters and scene rendering.
- Cinemachine 3 and Timeline are pinned in the Unity package manifest for cinematic shots.
- Animation Rigging is pinned for IK and character constraints.
- AndroidX Media3 Transformer 1.11.1 is the Android-side MP4 composition/export layer.

## Unity runtime foundation
The development branch now contains a Unity 6000.5 project baseline, a versioned 1080p story JSON contract, JSON validation, a procedural cube-block stage builder, a modular block-character prototype and a sample scene. See unity/README.md and docs/CINEMATIC_1080P_IMPLEMENTATION.md.

## AI and security
Firebase AI Logic structured story generation remains planned, not integrated. Keep prompts and secrets server-side, use App Check and validate all generated JSON. The offline story generator is only a fallback prototype, not Gemini.

## Current limitations
Cinemachine/Timeline shot playback, character animation, TTS, Unity frame capture, complete multi-shot composition, cloud rendering and the Android-Unity bridge are not yet implemented or verified. The Media3 adapter currently exports one existing video URI only. The Unity runtime foundation has not yet been opened and built in Unity Editor.
