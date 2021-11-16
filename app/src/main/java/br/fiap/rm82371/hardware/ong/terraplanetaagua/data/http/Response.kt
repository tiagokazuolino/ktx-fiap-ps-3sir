package br.fiap.rm82371.hardware.ong.terraplanetaagua.data.http

import com.google.firebase.database.DataSnapshot
import java.lang.Exception

data class Response<T>(
    var list: List<T>? = null,
    var snapshot: DataSnapshot? = null,
    var exception: Exception? = null
)

