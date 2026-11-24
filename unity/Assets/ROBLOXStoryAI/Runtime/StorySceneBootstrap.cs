using UnityEngine;
namespace RobloxStoryAI {
 public sealed class StorySceneBootstrap : MonoBehaviour {
  [SerializeField] private TextAsset storyJson; [SerializeField] private VoxelStageBuilder stageBuilder;
  [SerializeField] private Transform charactersRoot; [SerializeField] private StoryCinematicShotPlayer shotPlayer; [SerializeField] private bool buildOnStart=true;
  private void Start(){if(buildOnStart)BuildFromJson();}
  [ContextMenu("Build Story From JSON")] public void BuildFromJson(){
   if(storyJson==null){Debug.LogError("Assign story JSON TextAsset.",this);return;}
   if(!StorySceneValidator.TryParse(storyJson.text,out var story,out var error)){Debug.LogError(error,this);return;}
   if(stageBuilder!=null)stageBuilder.Build(story.blocks); if(charactersRoot==null)charactersRoot=transform;
   for(int i=charactersRoot.childCount-1;i>=0;i--){var go=charactersRoot.GetChild(i).gameObject;if(Application.isPlaying)Destroy(go);else DestroyImmediate(go);}
   var builder=new BlockCharacterBuilder();foreach(var character in story.characters)builder.Build(character,charactersRoot);
   if(shotPlayer!=null)shotPlayer.Play(story,charactersRoot);
   Debug.Log("Built "+story.title+": "+story.characters.Length+" characters, "+story.blocks.Length+" blocks, "+story.shots.Length+" shots; "+story.output.width+"x"+story.output.height+" @ "+story.output.frameRate+" fps.",this);
  }
 }
}