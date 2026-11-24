# Cinematic 1080p implementation
## Product decision
Final output is landscape 1920x1080, 30 fps, 16:9. Lower-resolution previews may be added later; the master export remains 1080p.
## Runtime ownership
- Android Kotlin/Compose: prompt, project library, render controls and MP4 delivery.
- Firebase AI Logic: schema-constrained story/character/scene/shot/dialogue/audio JSON; prompts and secrets remain server-side.
- Unity 6.5 (6000.5): voxel stage, block-character assembly, animation, lighting and scene rendering.
- Cinemachine 3 + Timeline: shot framing, camera blends, dialogue/audio timing and ordered scene playback.
- Animation Rigging: IK and character constraints after static prototype.
- Media3 Transformer 1.11.1: composition/transcode and MP4 delivery. Existing adapter currently handles one input clip only.
## Implemented foundation in this branch
- Unity project baseline and official package pins.
- Versioned story JSON contract requiring 1920x1080.
- JSON validation with prototype limits.
- Procedural cube-block stage generator.
- Modular six-part block-character generator.
- Sample scene with two characters and two shot descriptions.
## Next milestones
1. Open unity in Unity 6000.5, install packages, create URP scene, attach StorySceneBootstrap and VoxelStageBuilder, and assign the sample JSON.
2. Add Animator controllers and Animation Rigging; validate idle/walk/run/jump/wave.
3. Convert ShotDefinition into Cinemachine camera states and Timeline tracks with deterministic timing.
4. Capture each shot at 1920x1080/30 fps and emit ordered clips plus audio stems.
5. Compose clips, dialogue, music, sound effects, subtitles and transitions through Media3.
6. Add Unity Android build and bridge after standalone scene build and device profiling pass.
## Quality gates
Verify output MP4 dimensions and frame rate, GPU frame time, memory, thermal behavior, battery and render duration on a representative Android device. Bound voxel count and character count. Move rendering to a licensed cloud worker if target devices cannot sustain 1080p.
## Limitations
This is a Unity runtime scaffold, not a completed film renderer. Cinemachine cameras, Timeline tracks, character animation, speech synthesis, frame capture and complete multi-shot MP4 composition are not yet implemented. Unity Editor and Android build verification remain required.
