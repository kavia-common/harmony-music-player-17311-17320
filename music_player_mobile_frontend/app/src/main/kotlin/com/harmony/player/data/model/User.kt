package com.harmony.player.data.model

// PUBLIC_INTERFACE
data class User(
    val id: String,
    val email: String,
    var themePreference: ThemePreference = ThemePreference.LIGHT
)

enum class ThemePreference {
    LIGHT, DARK
}
