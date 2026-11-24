# ROBLOX STORY AI Unity runtime

This directory is the planned Unity runtime boundary. The Android app remains the project launcher, story editor and library; Unity owns 3D scene generation, character animation and cinematic playback.

## Editor baseline

Use Unity 6.5 / Editor stream 6000.5. The package versions below are the released versions documented for this editor stream.

## Packages

- Cinemachine 3.1.7
- Timeline 1.8.13
- Animation Rigging 1.4.1

The voxel engine is not yet installed as a dependency. The selected MIT reference implementation is https://github.com/Seremonik/Unity-Voxel-Engine. It targets Unity 6000.0+ and URP. Before adopting its source, profile Android devices and review its exact source/dependency licenses.

## First Unity milestone

1. Create a Unity 6000.5 project using URP.
2. Add the package manifest in this directory to the Unity project.
3. Create one test scene with a 16x16 voxel floor, walls and a simple block character.
4. Add idle, walk, run, jump and wave animation states.
5. Add a Cinemachine camera and a Timeline with three shots.
6. Receive a versioned scene JSON from Android and validate it before creating GameObjects.

## Android bridge

Unity as a Library is a later integration step. Keep the bridge contract independent of Unity classes: JSON in, render/session events out. Do not embed Unity in the Android APK until the Unity project builds for Android and the bridge has been tested on a real device.

## Rendering/export boundary

Unity is responsible for rendering the scene. The Android Media3 adapter currently exports a single existing video clip. Full Unity frame capture, audio mix, multi-shot assembly, subtitles and final MP4 export are separate milestones and are not claimed complete.
