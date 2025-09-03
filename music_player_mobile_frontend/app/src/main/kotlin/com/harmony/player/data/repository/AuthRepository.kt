package com.harmony.player.data.repository

import com.harmony.player.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

// PUBLIC_INTERFACE
class AuthRepository {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: Flow<User?> = _currentUser

    suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            // TODO: Implement actual authentication
            val user = User(
                id = "1",
                email = email
            )
            _currentUser.emit(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signOut() {
        _currentUser.emit(null)
    }
}
