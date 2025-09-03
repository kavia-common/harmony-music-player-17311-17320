package com.harmony.player

import android.app.Application
import com.harmony.player.ui.theme.ThemeManager
import com.harmony.player.data.model.ThemePreference

class HarmonyPlayerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize theme
        ThemeManager.setTheme(ThemePreference.LIGHT)
    }
}
