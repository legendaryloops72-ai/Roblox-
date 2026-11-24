package com.cart3r.robloxstoryai

import com.cart3r.robloxstoryai.data.buildStoryProject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StoryGeneratorTest {
    @Test
    fun createsFourScenesForAdventure() {
        val project = buildStoryProject("فريق يكتشف مدينة مخفية", "مغامرة")

        assertEquals(4, project.scenes.size)
        assertEquals("مغامرة", project.genre)
        assertTrue(project.scenes.first().contains("مدينة مخفية"))
    }

    @Test
    fun trimsPromptAndCreatesReadableTitle() {
        val project = buildStoryProject("  أبطال في عالم المكعبات  ", "خيال")

        assertEquals("أبطال في عالم المكعبات", project.title)
        assertEquals("أبطال في عالم المكعبات", project.prompt)
    }

    @Test
    fun createsUniqueProjectIds() {
        val first = buildStoryProject("القصة الأولى", "غموض")
        val second = buildStoryProject("القصة الثانية", "غموض")

        assertTrue(first.id != second.id)
    }
}
