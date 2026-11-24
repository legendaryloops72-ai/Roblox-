package com.cart3r.robloxstoryai.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class StoryProject(
    val id: String,
    val title: String,
    val genre: String,
    val prompt: String,
    val scenes: List<String>,
    val createdAt: Long
)

class StoryRepository(context: Context) {
    private val preferences = context.getSharedPreferences("story_projects", Context.MODE_PRIVATE)

    fun load(): List<StoryProject> {
        val raw = preferences.getString("projects", "[]") ?: "[]"
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.getJSONObject(index)
                    val scenesJson = item.optJSONArray("scenes") ?: JSONArray()
                    val scenes = buildList {
                        for (sceneIndex in 0 until scenesJson.length()) {
                            add(scenesJson.optString(sceneIndex))
                        }
                    }
                    add(
                        StoryProject(
                            id = item.optString("id"),
                            title = item.optString("title"),
                            genre = item.optString("genre"),
                            prompt = item.optString("prompt"),
                            scenes = scenes,
                            createdAt = item.optLong("createdAt")
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    fun save(project: StoryProject): List<StoryProject> {
        val updated = (listOf(project) + load().filterNot { it.id == project.id }).take(50)
        val array = JSONArray()
        updated.forEach { item ->
            val scenes = JSONArray()
            item.scenes.forEach { scene -> scenes.put(scene) }
            array.put(
                JSONObject()
                    .put("id", item.id)
                    .put("title", item.title)
                    .put("genre", item.genre)
                    .put("prompt", item.prompt)
                    .put("scenes", scenes)
                    .put("createdAt", item.createdAt)
            )
        }
        preferences.edit().putString("projects", array.toString()).apply()
        return updated
    }

    fun delete(id: String): List<StoryProject> {
        val updated = load().filterNot { it.id == id }
        val array = JSONArray()
        updated.forEach { item ->
            val scenes = JSONArray()
            item.scenes.forEach { scene -> scenes.put(scene) }
            array.put(
                JSONObject()
                    .put("id", item.id)
                    .put("title", item.title)
                    .put("genre", item.genre)
                    .put("prompt", item.prompt)
                    .put("scenes", scenes)
                    .put("createdAt", item.createdAt)
            )
        }
        preferences.edit().putString("projects", array.toString()).apply()
        return updated
    }
}

fun buildStoryProject(prompt: String, genre: String): StoryProject {
    val cleanPrompt = prompt.trim()
    val title = cleanPrompt
        .replace(Regex("\\s+"), " ")
        .take(42)
        .let { if (cleanPrompt.length > 42) "$it…" else it }
    val sceneIdeas = when (genre) {
        "كوميديا" -> listOf(
            "المشهد الأول: بداية يوم عادي ينقلب إلى موقف مضحك بسبب: $cleanPrompt",
            "المشهد الثاني: محاولة الأبطال إصلاح المشكلة، لكن خطتهم تزيد الفوضى",
            "المشهد الثالث: سوء فهم كبير يجمع كل الشخصيات في مكان واحد",
            "المشهد الرابع: حل طريف ونهاية تترك باباً لمغامرة جديدة"
        )
        "غموض" -> listOf(
            "المشهد الأول: اكتشاف دليل غريب مرتبط بـ: $cleanPrompt",
            "المشهد الثاني: البحث عن مصدر الدليل وظهور شخصية مشبوهة",
            "المشهد الثالث: كشف سر جزئي يقلب توقعات الأبطال",
            "المشهد الرابع: حل اللغز وظهور إشارة إلى سر أكبر"
        )
        "خيال" -> listOf(
            "المشهد الأول: اكتشاف عالم غير مألوف بسبب: $cleanPrompt",
            "المشهد الثاني: لقاء حارس العالم والحصول على مهمة",
            "المشهد الثالث: اختبار قوة الأبطال واتخاذ قرار صعب",
            "المشهد الرابع: مواجهة حاسمة وفتح بوابة إلى عالم جديد"
        )
        else -> listOf(
            "المشهد الأول: تقديم الأبطال والعالم الذي تبدأ فيه: $cleanPrompt",
            "المشهد الثاني: ظهور التحدي الأول واكتشاف هدف الرحلة",
            "المشهد الثالث: عقبة كبيرة تختبر شجاعة الفريق وتعاونه",
            "المشهد الرابع: المواجهة النهائية ونتيجة قرارات الأبطال"
        )
    }
    return StoryProject(
        id = java.util.UUID.randomUUID().toString(),
        title = title.ifBlank { "قصة جديدة" },
        genre = genre,
        prompt = cleanPrompt,
        scenes = sceneIdeas,
        createdAt = System.currentTimeMillis()
    )
}
