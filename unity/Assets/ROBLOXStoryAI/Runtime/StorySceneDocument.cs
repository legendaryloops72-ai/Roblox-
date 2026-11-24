using System;
using UnityEngine;
namespace RobloxStoryAI {
 [Serializable] public sealed class StorySceneDocument {
  public int schemaVersion = 1; public string title; public OutputProfile output = new OutputProfile();
  public CharacterDefinition[] characters = Array.Empty<CharacterDefinition>();
  public VoxelBlockDefinition[] blocks = Array.Empty<VoxelBlockDefinition>();
  public ShotDefinition[] shots = Array.Empty<ShotDefinition>();
 }
 [Serializable] public sealed class OutputProfile { public int width=1920; public int height=1080; public int frameRate=30; }
 [Serializable] public sealed class CharacterDefinition { public string id; public string displayName; public Vector3 position; public Vector3 scale=Vector3.one; public string colorHex="#4F83CC"; }
 [Serializable] public sealed class VoxelBlockDefinition { public Vector3 position; public Vector3 scale=Vector3.one; public string colorHex="#8A9B68"; }
 [Serializable] public sealed class ShotDefinition {
  public string id; public string subjectCharacterId; public float durationSeconds=4f; public string framing="medium";
  public Vector3 cameraPosition=new Vector3(0f,4f,-8f); public Vector3 lookAtOffset=new Vector3(0f,1f,0f); public string transition="cut";
 }
}