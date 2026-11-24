# Open-source skill research and adoption register

Last reviewed: 2026-11-24

This register separates **adopted dependencies**, **reference implementations**, and **deferred candidates**. A repository being public does not automatically make its code or assets safe to redistribute. Review the exact release, transitive dependencies, model weights, and asset licenses before shipping.

## 1. Voxel 3D worlds

### Primary architecture reference: Seremonik/Unity-Voxel-Engine
- Repository: https://github.com/Seremonik/Unity-Voxel-Engine
- License: MIT (verified in the repository LICENSE).
- Target: Unity 6000.0 or later; Universal Render Pipeline.
- Useful techniques: chunked world storage, binary face culling, greedy meshing, Burst/Jobs, ambient occlusion, flood-fill lighting, live voxel edits.
- Adoption: use as the primary algorithm and profiling reference. Do not blindly copy the complete project. Port only required runtime pieces after Android GPU profiling and API review.
- Mobile gate: benchmark chunk generation, memory, shader support, thermal load, and battery on a mid-range Android device before increasing view distance.

### Secondary package candidate: BLaZeKiLL/VloxyEngine
- Repository: https://github.com/BLaZeKiLL/VloxyEngine
- License: MIT (repository license).
- Unity package: `io.codeblaze.vloxyengine` version 1.2.0; package manifest declares Unity 2021.3 and Collections 1.3.1.
- Useful features: Jobs/Burst voxel generation, streaming, Android demo builds.
- Adoption: keep as a comparison candidate only. Its declared baseline is older than the Unity 6.5 project baseline and its Collections dependency needs compatibility testing.

## 2. Character animation

### Unity official animation stack
- Animation Rigging: `com.unity.animation.rigging@1.4.1`
- Timeline: `com.unity.timeline@1.8.13`
- Cinemachine: `com.unity.cinemachine@3.1.7`
- These are released package versions documented for Unity 6000.5.
- Use Animator/Mecanim for core character state machines, Animation Rigging for IK constraints, and Timeline for authored scene sequences.

### Foot placement reference: plonkabartosz/IKFootPlacement
- Repository: https://github.com/plonkabartosz/IKFootPlacement
- License: MIT, as declared by the repository.
- Useful technique: ground raycasts plus Animation C# Jobs and Two Bone IK to place feet on uneven ground.
- Adoption: reference only until its declared Animation Rigging 1.1.1 dependency is verified against the pinned Unity 6.5 packages. Port/adapt the algorithm rather than adding an unverified package dependency.

### Animation authoring reference: Sheggyb/AnimStudio
- Repository: https://github.com/Sheggyb/AnimStudio
- License: AGPL-3.0.
- Useful workflow: GLB rigging, retargeting, animation editing, foot-slide diagnostics, GLB export.
- Adoption: workflow research only. Do not copy or link its code into this app unless the project intentionally accepts AGPL obligations. Animation packs and model assets have separate licenses.

## 3. Cinematic direction

Use the Unity-supported stack:
- Cinemachine 3 for follow, dolly, orbit, shot framing, camera collision and blends.
- Timeline for ordered shots, animation tracks, dialogue/audio cues, effects and scene timing.
- Animation Rigging for character aim, hand/foot placement and constraints.

A story scene should describe shots as data: shot type, subject, lens/framing intent, camera move, start/end time, transition and audio cue. The Unity adapter turns that data into Timeline clips and Cinemachine cameras.

## 4. Video rendering and MP4 export

### Adopted Android library: Jetpack Media3 Transformer
- Official guide: https://developer.android.com/media/implement/editing-app
- Current pinned modules: `media3-transformer`, `media3-effect`, `media3-common` version 1.11.1.
- Implemented in this repository: `VideoExportManager`.
- Current capability: transcode one input video URI to H.264/AAC MP4.
- Not yet implemented: render Unity scene frames, multi-shot composition, audio mixing, subtitles, transitions, final story timeline export.

Media3 supports trimming, scaling, rotation, overlays, effects, asynchronous export, progress and cancellation. All Media3 modules in an app must use the same version.

### Reference editor: Inexra/opencut-mobile
- Repository: https://github.com/Inexra/opencut-mobile
- License: MIT.
- Useful patterns: Compose editing UI, Media3 preview, clip ordering, trim controls, aspect ratios, caption overlays and GitHub Actions APK builds.
- Adoption: architecture and UX reference only. Do not copy its full app into this project.

## 5. Voice acting and lip sync

### Runtime candidate: sherpa-onnx
- Repository: https://github.com/k2-fsa/sherpa-onnx
- Runtime license: Apache-2.0.
- Android support: official Android examples include standalone TTS and TTS engine projects; Kotlin API is available.
- Use as the on-device speech runtime after an AAR/ABI and device-size spike. Do not bundle model files until each model and frontend license is reviewed.

### Arabic model candidate: Nabra-82M
- Model card: https://huggingface.co/oddadmix/Nabra-82M-v0.1
- Declared license: Apache-2.0.
- Language: Modern Standard Arabic, one female voice, 24 kHz.
- Important limitations: expects diacritized Arabic input; it is not trained for Iraqi dialect and does not provide multiple character voices.
- Adoption: research/prototype candidate, not the final voice choice for Iraqi colloquial dialogue. Evaluate quality with native speakers and compare dialect-capable alternatives before shipping.

### Lip-sync contract
Generate speech per character line and retain word/phoneme timestamps when the selected TTS provider exposes them. Convert phonemes to a compact viseme sequence (closed lips, open vowel, rounded vowel, teeth/lip, tongue, rest), then drive blend shapes or block-character mouth states. If the TTS provider gives only a finished waveform, use forced alignment or speech recognition to estimate timing; do not claim exact lip sync from audio duration alone.

## 6. AI story director

### Adopted integration direction: Firebase AI Logic
- Structured output guide: https://firebase.google.com/docs/ai-logic/generate-structured-output
- Security checklist: https://firebase.google.com/docs/ai-logic/security-checklist
- Use response schemas to return validated JSON for story, characters, scenes, shots, dialogue, sound cues and asset prompts.
- Use server prompt templates to keep system prompts and model configuration outside the APK.
- Enforce Firebase App Check, authenticate users as appropriate, restrict API keys, validate input and apply quotas.
- Never put a Gemini Developer API key or service-account credential in Android resources, source code, Git history or the APK.

## Integration order

1. Android video export adapter and tests/build verification.
2. Unity 6.5 project skeleton with official packages.
3. Voxel chunk prototype and mobile performance gate.
4. Character controller, animation state machine and IK.
5. Timeline/Cinemachine shot playback from story JSON.
6. Arabic voice spike, then dialect-capable voice selection.
7. Lip-sync timing and sound cue scheduler.
8. Firebase AI Logic structured story generation with App Check and server prompt templates.
9. Render Unity shots and compose the full MP4 with audio/subtitles.

## License policy

Maintain a third-party inventory with repository/model URL, exact version or commit, license, copyright notice, modified-file notes, model/voice terms, and redistribution decision. Never assume a repository's software license covers its sample models, animations, textures, or voices.
