package br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var uid: String? = null,
    var displayName: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var providerId: String? = null,
    var tenantId: String? = null,
) : Parcelable