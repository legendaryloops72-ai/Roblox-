package com.cart3r.robloxstoryai

import org.junit.Assert.assertEquals
import org.junit.Test

class PackageConfigurationTest {
    @Test
    fun applicationIdIsStable() {
        assertEquals("com.cart3r.robloxstoryai", BuildConfig.APPLICATION_ID)
    }
}
