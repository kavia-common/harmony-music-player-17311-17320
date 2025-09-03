package com.harmony.player.ui.library

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.harmony.player.R
import com.harmony.player.data.repository.AuthRepository
import com.harmony.player.data.repository.MusicRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CreatePlaylistDialog : DialogFragment() {
    private val musicRepository = MusicRepository()
    private val authRepository = AuthRepository()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editText = EditText(requireContext()).apply {
            hint = getString(R.string.new_playlist)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.create_playlist)
            .setView(editText)
            .setPositiveButton(R.string.save) { _, _ ->
                val playlistName = editText.text.toString()
                if (playlistName.isNotBlank()) {
                    lifecycleScope.launch {
                        val userId = authRepository.currentUser.first()?.id ?: return@launch
                        musicRepository.createPlaylist(playlistName, userId)
                    }
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }
}
