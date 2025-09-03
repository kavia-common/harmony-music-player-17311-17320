package com.harmony.player.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.harmony.player.data.repository.MusicRepository
import com.harmony.player.databinding.FragmentSearchBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val musicRepository = MusicRepository()
    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvResults.layoutManager = LinearLayoutManager(context)

        binding.etQuery.addTextChangedListener { text ->
            searchQuery.value = text?.toString() ?: ""
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchQuery
                .debounce(300)
                .filter { it.length >= 2 }
                .distinctUntilChanged()
                .onEach { query ->
                    val adapter = TrackAdapter { track ->
                        // Handle track click
                        (activity as? MainActivity)?.playbackService?.playTrack(track)
                    }
                    binding.rvResults.adapter = adapter

                    musicRepository.searchTracks(query).collect { tracks ->
                        adapter.submitList(tracks)
                    }
                }
                .launchIn(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
