package com.harmony.player.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import com.harmony.player.data.model.ThemePreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// PUBLIC_INTERFACE
object ThemeManager {
    private val _currentTheme = MutableStateFlow(ThemePreference.LIGHT)
    val currentTheme: StateFlow<ThemePreference> = _currentTheme

    fun setTheme(theme: ThemePreference) {
        _currentTheme.value = theme
        when (theme) {
            ThemePreference.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemePreference.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
