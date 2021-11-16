package br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories

import androidx.annotation.WorkerThread
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.FirebaseSource

class UserRepository(
    private val firebase: FirebaseSource
) {

    suspend fun login(email: String, password: String) = firebase.signin(email, password)

    suspend fun register(email: String, password: String) = firebase.signup(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}