using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Unity.Cinemachine;

namespace RobloxStoryAI
{
    public sealed class StoryCinematicShotPlayer : MonoBehaviour
    {
        [SerializeField] private Camera outputCamera;
        [SerializeField] private Transform camerasRoot;
        private readonly List<CinemachineCamera> generatedCameras = new List<CinemachineCamera>();
        private Coroutine playback;

        public void Play(StorySceneDocument story, Transform charactersRoot)
        {
            Stop();
            if (story == null || story.shots == null || story.shots.Length == 0) return;
            if (outputCamera == null) outputCamera = Camera.main;
            if (outputCamera == null)
            {
                var cameraObject = new GameObject("Story Output Camera");
                outputCamera = cameraObject.AddComponent<Camera>();
                cameraObject.tag = "MainCamera";
            }
            if (outputCamera.GetComponent<CinemachineBrain>() == null)
                outputCamera.gameObject.AddComponent<CinemachineBrain>();
            if (camerasRoot == null) camerasRoot = transform;

            CreateShotCameras(story, charactersRoot);
            playback = StartCoroutine(PlaySequence(story));
        }

        public void Stop()
        {
            if (playback != null) StopCoroutine(playback);
            playback = null;
            foreach (var camera in generatedCameras)
                if (camera != null) Destroy(camera.gameObject);
            generatedCameras.Clear();
        }

        private void CreateShotCameras(StorySceneDocument story, Transform charactersRoot)
        {
            foreach (var shot in story.shots)
            {
                var cameraObject = new GameObject("CM_Shot_" + shot.id);
                cameraObject.transform.SetParent(camerasRoot, false);
                cameraObject.transform.position = shot.cameraPosition;
                var virtualCamera = cameraObject.AddComponent<CinemachineCamera>();
                virtualCamera.enabled = false;

                var target = FindCharacter(charactersRoot, shot.subjectCharacterId);
                if (target != null)
                {
                    virtualCamera.LookAt = target;
                    var composer = cameraObject.AddComponent<CinemachineRotationComposer>();
                    composer.TargetOffset = shot.lookAtOffset;
                }
                generatedCameras.Add(virtualCamera);
            }
        }

        private IEnumerator PlaySequence(StorySceneDocument story)
        {
            for (var i = 0; i < story.shots.Length; i++)
            {
                if (i > 0) generatedCameras[i - 1].enabled = false;
                generatedCameras[i].enabled = true;
                yield return new WaitForSeconds(Mathf.Max(0.1f, story.shots[i].durationSeconds));
            }
            generatedCameras[generatedCameras.Count - 1].enabled = false;
            playback = null;
        }

        private static Transform FindCharacter(Transform root, string characterId)
        {
            if (root == null || string.IsNullOrWhiteSpace(characterId)) return null;
            for (var i = 0; i < root.childCount; i++)
            {
                var child = root.GetChild(i);
                if (child.name == characterId || child.name == characterId.Replace("_", " ")) return child;
            }
            return root.Find(characterId);
        }

        private void OnDisable() { Stop(); }
        private void OnDestroy() { Stop(); }
    }
}