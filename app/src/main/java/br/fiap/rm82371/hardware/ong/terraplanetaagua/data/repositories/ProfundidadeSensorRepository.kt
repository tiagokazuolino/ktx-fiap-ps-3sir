package br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories

import androidx.annotation.Nullable
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.FirebaseDatasource
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.ProfundidadeSensorModel

class ProfundidadeSensorRepository(private val firebase: FirebaseDatasource<ProfundidadeSensorModel, Nullable>) {
    suspend fun setSensors(model: ProfundidadeSensorModel, uuid: String?) =
        firebase.setSensors(model, uuid)

    suspend fun getSensor() = firebase.getSensors()

    fun logout() = firebase.logout()

    fun currentUser() = firebase.currentUser()
}