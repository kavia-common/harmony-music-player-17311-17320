package com.harmony.player.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.harmony.player.data.model.ThemePreference
import com.harmony.player.data.repository.AuthRepository
import com.harmony.player.databinding.FragmentProfileBinding
import com.harmony.player.ui.auth.LoginActivity
import com.harmony.player.ui.theme.ThemeManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authRepository = AuthRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupThemeSelection()
        setupLogout()
        
        viewLifecycleOwner.lifecycleScope.launch {
            authRepository.currentUser.collectLatest { user ->
                user?.let {
                    binding.tvEmail.text = it.email
                }
            }
        }
    }

    private fun setupThemeSelection() {
        binding.rgTheme.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                binding.rbLight.id -> ThemePreference.LIGHT
                binding.rbDark.id -> ThemePreference.DARK
                else -> ThemePreference.LIGHT
            }
            ThemeManager.setTheme(theme)
        }
    }

    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                authRepository.signOut()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
