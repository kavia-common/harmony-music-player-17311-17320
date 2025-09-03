package com.harmony.player.playback

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.harmony.player.data.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicPlaybackService : Service() {
    private val binder = MusicBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrack: Track? = null
    
    private val _isPlaying = MutableStateFlow(false)
    private val _currentPosition = MutableStateFlow(0L)
    private val _isShuffleEnabled = MutableStateFlow(false)
    private val _isRepeatEnabled = MutableStateFlow(false)
    
    val isPlaying = _isPlaying.asStateFlow()
    val currentPosition = _currentPosition.asStateFlow()
    val isShuffleEnabled = _isShuffleEnabled.asStateFlow()
    val isRepeatEnabled = _isRepeatEnabled.asStateFlow()

    inner class MusicBinder : Binder() {
        fun getService(): MusicPlaybackService = this@MusicPlaybackService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun playTrack(track: Track) {
        currentTrack = track
        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(track.trackUrl)
            prepare()
            start()
        }
        _isPlaying.value = true
    }

    fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _isPlaying.value = false
            } else {
                it.start()
                _isPlaying.value = true
            }
        }
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun toggleShuffle() {
        _isShuffleEnabled.value = !_isShuffleEnabled.value
    }

    fun toggleRepeat() {
        _isRepeatEnabled.value = !_isRepeatEnabled.value
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}
