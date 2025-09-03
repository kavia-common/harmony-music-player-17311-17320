package com.harmony.player.data.model

// PUBLIC_INTERFACE
data class Playlist(
    val id: String,
    val name: String,
    val tracks: List<Track>,
    val createdBy: String
)
