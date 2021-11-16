package br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils

import android.os.Parcelable
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.HomeModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Senores(
    var list: List<HomeModel>? = null
) : Parcelable