package br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class DataSensor(
    val min: Float? = null,
    val max: Float? = null,
    val eventAt: Date? = null
) : Parcelable
