# Video pipeline

## Current implementation

`VideoExportManager` uses AndroidX Media3 Transformer 1.11.1 to export one input video URI to an H.264/AAC MP4 file. It supports completion/error callbacks and cancellation.

## Not yet implemented

- Rendering Unity scene frames to a video stream.
- Combining multiple story shots into one timeline.
- Mixing character dialogue, music and sound effects.
- Word-timed captions and animated text.
- Scene transitions, aspect-ratio presets and output quality selection UI.
- Background export job lifecycle and recovery after process death.

## Target pipeline

Unity scene renderer -> ordered shot clips -> audio/dialogue tracks -> caption and effect overlays -> Media3 Composition/Transformer -> MP4 -> MediaStore/Share.

Media3 Transformer supports editing operations such as trimming, scaling, rotation, effects and overlays. Use a single pinned version for all Media3 modules. Keep export asynchronous, expose progress, allow cancellation and clean up temporary files after success or failure.

## Mobile quality profiles

- Draft: 540p, 24 fps.
- Standard: 720p, 30 fps.
- High: 1080p, 30 fps when device encoders and thermal headroom allow.

These are proposed product presets, not measured device guarantees. Select output settings after testing representative Android devices.
