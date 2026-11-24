using UnityEngine;
using UnityEngine.Playables;
using UnityEngine.Timeline;

namespace RobloxStoryAI
{
    public sealed class StoryTimelinePlayback : MonoBehaviour
    {
        [SerializeField] private PlayableDirector director;
        [SerializeField] private TimelineAsset cinematicTimeline;

        public void Play()
        {
            if (director == null) director = GetComponent<PlayableDirector>();
            if (director == null || cinematicTimeline == null)
            {
                Debug.LogWarning("Assign a PlayableDirector and a TimelineAsset to play the authored cinematic sequence.", this);
                return;
            }
            director.playableAsset = cinematicTimeline;
            director.time = 0;
            director.Play();
        }

        public void Stop()
        {
            if (director != null) director.Stop();
        }
    }
}