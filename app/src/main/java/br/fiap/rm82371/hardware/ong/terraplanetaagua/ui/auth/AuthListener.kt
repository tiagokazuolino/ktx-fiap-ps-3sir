package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.auth

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}