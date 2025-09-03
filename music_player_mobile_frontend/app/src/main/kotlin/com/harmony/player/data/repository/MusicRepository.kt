package com.harmony.player.data.repository

import com.harmony.player.data.model.Playlist
import com.harmony.player.data.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

// PUBLIC_INTERFACE
class MusicRepository {
    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    
    val allTracks: Flow<List<Track>> = _tracks
    val userPlaylists: Flow<List<Playlist>> = _playlists

    suspend fun searchTracks(query: String): Flow<List<Track>> {
        return _tracks.map { tracks ->
            tracks.filter { track ->
                track.title.contains(query, ignoreCase = true) ||
                track.artist.contains(query, ignoreCase = true) ||
                track.album.contains(query, ignoreCase = true)
            }
        }
    }

    suspend fun createPlaylist(name: String, userId: String): Result<Playlist> {
        return try {
            val playlist = Playlist(
                id = System.currentTimeMillis().toString(),
                name = name,
                tracks = emptyList(),
                createdBy = userId
            )
            val current = _playlists.value.toMutableList()
            current.add(playlist)
            _playlists.emit(current)
            Result.success(playlist)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addToPlaylist(playlistId: String, track: Track): Result<Playlist> {
        return try {
            val current = _playlists.value.toMutableList()
            val playlistIndex = current.indexOfFirst { it.id == playlistId }
            if (playlistIndex == -1) return Result.failure(Exception("Playlist not found"))
            
            val playlist = current[playlistIndex]
            val updatedPlaylist = playlist.copy(
                tracks = playlist.tracks + track
            )
            current[playlistIndex] = updatedPlaylist
            _playlists.emit(current)
            Result.success(updatedPlaylist)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
