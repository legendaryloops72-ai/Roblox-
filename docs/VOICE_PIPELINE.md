# Voice acting and lip-sync plan

## Runtime

Candidate runtime: sherpa-onnx (Apache-2.0), using its official Android/Kotlin TTS integration. The Android runtime package and native ABI size must be tested before selecting the final artifact.

## Arabic voice candidate

Nabra-82M is a declared Apache-2.0 Modern Standard Arabic model. Its model card states that it uses one female voice and expects diacritized input. It is not an Iraqi dialect model. It is suitable for a technical prototype, not yet approved as the product's final voice.

Model: https://huggingface.co/oddadmix/Nabra-82M-v0.1
Runtime: https://github.com/k2-fsa/sherpa-onnx

## Character voice model

Each character profile should reference a voice preset ID, language/locale, speaking rate, pitch range and emotion. Do not promise unique character voices until the selected TTS provider supports them and the voice/model license is checked.

## Dialogue audio record

Store:
- characterId
- lineId
- locale
- text
- normalized/diacritizedText when required
- audioAssetUri
- durationMs
- word/phoneme alignment when available
- voiceModelId and model revision

## Lip sync

Prefer provider word/phoneme timing. Otherwise run forced alignment or speech recognition on the generated audio. Map phonemes to visemes and drive the character mouth animation. Block-style characters can use a small set of mouth shapes rather than a full facial rig.

## Release gates

- Test Arabic pronunciation with native speakers.
- Test Iraqi colloquial samples separately from Modern Standard Arabic.
- Review the model card, training data and voice terms.
- Check offline model download size, first-run time, RAM, thermal impact and APK/AAB size.
- Keep third-party notices and model attribution with the shipped model.
