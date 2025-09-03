package com.harmony.player.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.harmony.player.data.repository.AuthRepository
import com.harmony.player.data.repository.MusicRepository
import com.harmony.player.databinding.FragmentLibraryBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LibraryFragment : Fragment() {
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private val musicRepository = MusicRepository()
    private val authRepository = AuthRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPlaylists.layoutManager = LinearLayoutManager(context)

        binding.btnCreatePlaylist.setOnClickListener {
            // Show dialog to create new playlist
            CreatePlaylistDialog().show(childFragmentManager, "create_playlist")
        }

        val adapter = PlaylistAdapter { playlist ->
            // TODO: Navigate to playlist detail screen
        }
        binding.rvPlaylists.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            musicRepository.userPlaylists.collectLatest { playlists ->
                adapter.submitList(playlists)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
