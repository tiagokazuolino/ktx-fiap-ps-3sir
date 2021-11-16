package br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http

interface ResponseListener {
    fun onStarted()
    fun onSuccess()
    fun onUpdated()
    fun onFailure(message: String)
}