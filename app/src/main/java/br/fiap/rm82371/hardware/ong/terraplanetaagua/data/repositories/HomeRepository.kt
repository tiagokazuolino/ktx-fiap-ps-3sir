package br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories

import androidx.annotation.Nullable
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.FirebaseDatasource
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.HomeModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.OxigenioSensorModel

class HomeRepository(
    private val firebase: FirebaseDatasource<HomeModel, Nullable>
) {
    suspend fun getSensors() = firebase.getSensors()

    fun logout() = firebase.logout()

    fun currentUser() = firebase.currentUser()
}