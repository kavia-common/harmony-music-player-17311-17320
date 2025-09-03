package com.harmony.player.data.model

// PUBLIC_INTERFACE
data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val artworkUrl: String?,
    val trackUrl: String
)
