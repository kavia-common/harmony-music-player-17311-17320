package com.harmony.player.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.harmony.player.R
import com.harmony.player.databinding.ActivityMainBinding
import com.harmony.player.playback.MusicPlaybackService
import com.harmony.player.ui.home.HomeFragment
import com.harmony.player.ui.library.LibraryFragment
import com.harmony.player.ui.profile.ProfileFragment
import com.harmony.player.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var playbackService: MusicPlaybackService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicPlaybackService.MusicBinder
            playbackService = binder.getService()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            playbackService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start and bind the music service
        Intent(this, MusicPlaybackService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
            startService(intent)
        }

        setupNavigation()
        setupMiniPlayer()

        // Start with home fragment
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun setupNavigation() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_search -> SearchFragment()
                R.id.nav_library -> LibraryFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }
            fragment?.let { loadFragment(it) }
            true
        }
    }

    private fun setupMiniPlayer() {
        binding.miniPlayerContainer.apply {
            btnPlayPause.setOnClickListener {
                playbackService?.togglePlayPause()
            }
            btnNext.setOnClickListener {
                // TODO: Implement next track
            }
            btnPrevious.setOnClickListener {
                // TODO: Implement previous track
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}
