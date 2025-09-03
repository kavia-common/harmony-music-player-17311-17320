package com.harmony.player.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.harmony.player.data.repository.MusicRepository
import com.harmony.player.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val musicRepository = MusicRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFeatured.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecent.layoutManager = LinearLayoutManager(context)

        val featuredAdapter = TrackAdapter { track ->
            // Handle track click
            (activity as? MainActivity)?.playbackService?.playTrack(track)
        }
        val recentAdapter = TrackAdapter { track ->
            // Handle track click
            (activity as? MainActivity)?.playbackService?.playTrack(track)
        }

        binding.rvFeatured.adapter = featuredAdapter
        binding.rvRecent.adapter = recentAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            musicRepository.allTracks.collectLatest { tracks ->
                featuredAdapter.submitList(tracks.take(10))
                recentAdapter.submitList(tracks.takeLast(20))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
