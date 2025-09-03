package com.harmony.player.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.harmony.player.data.repository.AuthRepository
import com.harmony.player.databinding.ActivityLoginBinding
import com.harmony.player.ui.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                authRepository.signIn(email, password)
                    .onSuccess {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    .onFailure {
                        Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
