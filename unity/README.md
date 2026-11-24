# ROBLOX STORY AI Unity runtime

Unity owns 3D scene generation and cinematic rendering. Android remains the story editor, project library, render controller and MP4 delivery client.

## Editor baseline and packages
- Unity Editor 6000.5 (Unity 6.5), URP.
- Cinemachine 3.1.7
- Timeline 1.8.13
- Animation Rigging 1.4.1

## Current runtime scaffold
- Project version marker: ProjectSettings/ProjectVersion.txt.
- Runtime scripts: Assets/ROBLOXStoryAI/Runtime.
- StorySceneDocument defines a versioned JSON contract for output, characters, voxel blocks and shots.
- StorySceneValidator validates the JSON and enforces the 1920x1080 profile and prototype scene limits.
- VoxelStageBuilder creates cube primitives from block definitions.
- BlockCharacterBuilder creates a six-part block character prototype.
- StorySceneBootstrap loads the JSON and builds the stage and characters.
- Sample JSON: Assets/StreamingAssets/sample_story_scene.json.

## Open in Unity
1. Open the unity folder with Unity 6000.5 and install the packages from Packages/manifest.json.
2. Create a URP scene and add a GameObject with StorySceneBootstrap and VoxelStageBuilder components.
3. Assign Assets/StreamingAssets/sample_story_scene.json as the TextAsset. Assign the stage builder reference.
4. Press Play or use the Build Story From JSON component context menu.
5. Add a directional light and a camera to inspect the generated prototype.

## Next milestones
1. Build and validate the Unity scene in Editor, then Android target.
2. Add Animator controllers and Animation Rigging constraints for idle/walk/run/jump/wave.
3. Convert shot definitions into Cinemachine cameras and Timeline tracks.
4. Add deterministic 1080p/30 fps shot capture and ordered render clips.
5. Compose dialogue, music, effects, captions and transitions through Media3.
6. Add a JSON-based Android/Unity session bridge after standalone Unity and Android builds pass.

## Licensing and mobile performance
The selected MIT voxel implementation at https://github.com/Seremonik/Unity-Voxel-Engine is an algorithm reference, not yet installed as a dependency. Profile chunk generation, shaders, GPU time, RAM, heat and battery before porting code. Review source, dependencies and asset licenses separately.

## Honest status
This is an early Unity scene-generation scaffold. Cinemachine shot playback, Timeline track generation, animation, voice synthesis, frame capture, full MP4 assembly, cloud rendering and Unity as a Library are not complete. Do not embed Unity in the Android APK until the standalone Unity Android build and device bridge are verified.
