package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val currentUser = firebaseAuth.currentUser

    private suspend fun updatePassword(password: String){
        currentUser!!.updatePassword(password).await()
    }

    fun update(password: String){
        viewModelScope.launch {
            updatePassword(password)
        }
    }

}