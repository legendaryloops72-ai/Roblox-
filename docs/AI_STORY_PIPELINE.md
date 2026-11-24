# AI story director contract

## Pipeline

User prompt -> input validation -> server prompt template -> Gemini structured JSON -> schema validation -> project repository -> scene/shot planners -> Unity adapter.

The Android app must not contain a Gemini Developer API key. Prefer Firebase AI Logic server prompt templates and enforce App Check. Keep provider-specific model names and prompts on the server so they can be changed without releasing a new APK.

## Story JSON shape

The structured response must contain:
- `title`: short story title.
- `language`: output language code.
- `genre`: one of adventure, comedy, mystery, fantasy, drama.
- `characters`: stable id, name, visual description, personality, voice profile.
- `scenes`: stable id, location, environment description, ordered actions and dialogue.
- `shots`: scene id, shot type, subject ids, camera movement, duration and transition.
- `audioCues`: cue type, description, scene id, start offset and duration.
- `safetyNotes`: brief content checks or constraints.

## Generation rules

- Request JSON matching a response schema, not free-form prose.
- Use stable IDs so editing a scene does not invalidate unrelated characters or shots.
- Keep the character appearance description consistent across all scenes.
- Make each shot executable: it must identify a subject, duration, framing and camera move.
- Dialogue belongs to a character ID and a locale; do not infer voice identity from a real person.
- Bound prompt length, scene count, dialogue length and generation tokens.
- Validate every model response on the server before saving it.
- Treat user story text as untrusted input, not as system instructions.
- Store prompt-template version and model metadata with each generated project.

## First generation profile

For a short-form story:
- 3 to 6 scenes.
- 2 to 4 characters.
- 2 to 5 shots per scene.
- 9:16 and 16:9 output profiles.
- Dialogue lines short enough for the target video duration.

## Security and cost controls

Use App Check, authenticated users when appropriate, per-user quotas, rate limits, server-side prompt templates and request validation. Avoid sending API secrets to the app. Keep the offline outline generator as a fallback while the online provider is not configured.
