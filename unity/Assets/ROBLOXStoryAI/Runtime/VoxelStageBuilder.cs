using System.Collections.Generic;
using UnityEngine;
namespace RobloxStoryAI {
 public sealed class VoxelStageBuilder : MonoBehaviour {
  [SerializeField] private Transform stageRoot;
  private readonly Dictionary<string,Material> materials=new Dictionary<string,Material>();
  public void Build(VoxelBlockDefinition[] blocks) {
   if(stageRoot==null) stageRoot=transform; ClearChildren(stageRoot); if(blocks==null)return;
   for(int i=0;i<blocks.Length;i++){ var b=blocks[i]; if(b==null)continue;
    var cube=GameObject.CreatePrimitive(PrimitiveType.Cube); cube.name="Voxel_"+i; cube.transform.SetParent(stageRoot,false);
    cube.transform.localPosition=b.position; cube.transform.localScale=Vector3.Max(b.scale,Vector3.one*0.05f);
    cube.GetComponent<Renderer>().sharedMaterial=GetMaterial(b.colorHex);
   }
  }
  private Material GetMaterial(string hex) {
   string key=string.IsNullOrWhiteSpace(hex)?"#8A9B68":hex; if(materials.TryGetValue(key,out var m))return m;
   Shader shader=Shader.Find("Universal Render Pipeline/Lit"); if(shader==null)shader=Shader.Find("Standard");
   m=new Material(shader); if(ColorUtility.TryParseHtmlString(key,out var c))m.color=c; materials.Add(key,m); return m;
  }
  private static void ClearChildren(Transform root) { for(int i=root.childCount-1;i>=0;i--){var go=root.GetChild(i).gameObject;if(Application.isPlaying)Destroy(go);else DestroyImmediate(go);} }
  private void OnDestroy(){foreach(var m in materials.Values){if(m==null)continue;if(Application.isPlaying)Destroy(m);else DestroyImmediate(m);}materials.Clear();}
 }
}