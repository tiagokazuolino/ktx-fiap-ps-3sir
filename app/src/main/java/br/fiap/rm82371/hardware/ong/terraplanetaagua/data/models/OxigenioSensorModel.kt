package br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models

import android.os.Parcelable
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.DataSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.StatusSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.TypeSensor
import kotlinx.parcelize.Parcelize

@Parcelize
data class OxigenioSensorModel(
    val uid: String? = null,
    val name: String? = null,
    val battery: Long? = null,
    val lat: Float? = null,
    val lng: Float? = null,
    val status: StatusSensor? = null,
    val type: TypeSensor? = null,
    val events: List<DataSensor>? = null
) : Parcelable