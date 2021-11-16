package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var passwordConfirmation: String? = null

    var authListener: AuthListener? = null

    val user by lazy {
        repository.currentUser()
    }

    fun login() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        viewModelScope.launch {
            authListener?.onStarted()
            val result = repository.login(email!!, password!!)
            if (result != null) {
                authListener?.onSuccess()
            } else {
                authListener?.onFailure("Erro ao logar")
            }
        }
    }

    fun signup() {

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        if (password != passwordConfirmation) {
            authListener?.onFailure("password not match")
            return
        }
        viewModelScope.launch {
            authListener?.onStarted()
            val result = repository.register(email!!, password!!)
            if (result != null) {
                authListener?.onSuccess()
            } else {
                authListener?.onFailure("Register failed")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class AuthViewModelFactory(private val repository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(repository) as T
        }
    }
}