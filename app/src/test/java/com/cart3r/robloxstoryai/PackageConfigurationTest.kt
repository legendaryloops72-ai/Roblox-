package com.cart3r.robloxstoryai

import org.junit.Assert.assertTrue
import org.junit.Test

class PackageConfigurationTest {
    @Test
    fun debugApplicationIdUsesTheExpectedPackagePrefix() {
        assertTrue(BuildConfig.APPLICATION_ID.startsWith("com.cart3r.robloxstoryai"))
    }
}
