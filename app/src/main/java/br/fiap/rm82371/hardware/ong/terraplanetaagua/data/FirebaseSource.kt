package br.fiap.rm82371.hardware.ong.terraplanetaagua.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseSource {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    suspend fun signin(email: String, password: String): AuthResult? {
        val data: AuthResult? = try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            null
        }
        return data
    }

    suspend fun signup(email: String, password: String): AuthResult? {
        val data: AuthResult? = try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            null
        }
        return data
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser
}