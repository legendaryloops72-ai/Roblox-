using System;
using UnityEngine;
namespace RobloxStoryAI {
 public static class StorySceneValidator {
  public static bool TryParse(string json, out StorySceneDocument document, out string error) {
   document=null; error=null;
   if (string.IsNullOrWhiteSpace(json)) { error="Story JSON is empty."; return false; }
   try { document=JsonUtility.FromJson<StorySceneDocument>(json); }
   catch(Exception e) { error="Story JSON parse failed: "+e.Message; return false; }
   if(document==null || document.schemaVersion!=1) { error="Expected story schemaVersion 1."; return false; }
   if(document.output==null || document.output.width!=1920 || document.output.height!=1080) { error="Expected 1920x1080 output."; return false; }
   if(document.characters==null) document.characters=Array.Empty<CharacterDefinition>();
   if(document.blocks==null) document.blocks=Array.Empty<VoxelBlockDefinition>();
   if(document.shots==null) document.shots=Array.Empty<ShotDefinition>();
   if(document.characters.Length>32 || document.blocks.Length>20000 || document.shots.Length>120) { error="Prototype limits exceeded."; return false; }
   foreach(var c in document.characters) if(c==null || string.IsNullOrWhiteSpace(c.id)) { error="Every character needs a stable id."; return false; }
   foreach(var s in document.shots) if(s==null || string.IsNullOrWhiteSpace(s.id) || s.durationSeconds<=0f) { error="Every shot needs an id and positive duration."; return false; }
   return true;
  }
 }
}