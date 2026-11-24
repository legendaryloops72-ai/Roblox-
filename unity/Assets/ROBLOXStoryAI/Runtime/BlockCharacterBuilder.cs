using UnityEngine;
namespace RobloxStoryAI {
 public sealed class BlockCharacterBuilder {
  public GameObject Build(CharacterDefinition d,Transform parent) {
   var root=new GameObject(d.id); root.transform.SetParent(parent,false);
   root.transform.localPosition=d.position; root.transform.localScale=Vector3.Max(d.scale,Vector3.one*0.1f);
   Color body; if(!ColorUtility.TryParseHtmlString(d.colorHex,out body))body=new Color(.25f,.5f,.8f);
   Part(root.transform,"Torso",new Vector3(0,1.35f,0),new Vector3(.85f,.9f,.45f),body);
   Part(root.transform,"Head",new Vector3(0,2.05f,0),Vector3.one*.62f,new Color(.92f,.76f,.61f));
   Part(root.transform,"LeftArm",new Vector3(-.58f,1.38f,0),new Vector3(.28f,.9f,.3f),body);
   Part(root.transform,"RightArm",new Vector3(.58f,1.38f,0),new Vector3(.28f,.9f,.3f),body);
   Part(root.transform,"LeftLeg",new Vector3(-.22f,.48f,0),new Vector3(.32f,.85f,.36f),new Color(.18f,.22f,.3f));
   Part(root.transform,"RightLeg",new Vector3(.22f,.48f,0),new Vector3(.32f,.85f,.36f),new Color(.18f,.22f,.3f));
   return root;
  }
  private static void Part(Transform p,string n,Vector3 pos,Vector3 size,Color color){
   var go=GameObject.CreatePrimitive(PrimitiveType.Cube);go.name=n;go.transform.SetParent(p,false);go.transform.localPosition=pos;go.transform.localScale=size;go.GetComponent<Renderer>().material.color=color;
  }
 }
}