# Video pipeline

## Product master profile
ROBLOX STORY AI produces landscape cinematic films at 1920x1080 (16:9), 30 fps. A low-resolution preview may be used during editing, but the final deliverable is 1080p.

## Current Android implementation
VideoExportManager uses AndroidX Media3 Transformer 1.11.1 to export one input video URI to an H.264/AAC MP4 file. It supports completion/error callbacks and cancellation. It does not yet combine Unity shots or audio stems.

## Target pipeline
Validated story JSON -> Unity scene renderer -> ordered 1080p shot clips -> dialogue/music/effects tracks -> subtitles and transitions -> Media3 Composition/Transformer -> MP4 -> MediaStore/Share.

## Not yet implemented
- Cinemachine camera shot playback and Timeline track generation.
- Unity frame capture at 1920x1080/30 fps.
- Multi-shot composition and scene transitions.
- Dialogue, music and sound-effect mixing.
- Word-timed captions and animated text.
- Background export recovery and final-file verification.

## Export requirements
- Encode H.264 video and AAC audio in MP4.
- Preserve 1920x1080 dimensions and 30 fps in the final output.
- Keep export asynchronous with progress, cancellation and temporary-file cleanup.
- Verify actual output metadata after export; a selected UI preset is not proof of output quality.
- Benchmark encoder support, memory, thermal behavior and export time on representative Android devices.

## Rendering ownership
Unity renders 3D scenes. Media3 composes and delivers the rendered shot clips on Android. If device rendering cannot sustain 1080p, use a licensed cloud render worker and keep Android as the controller and delivery client.
