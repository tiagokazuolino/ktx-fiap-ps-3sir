package br.fiap.rm82371.hardware.ong.terraplanetaagua.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class FirebaseDatasource<T, R>(child: String, report: String? = null) {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private val rootRef: DatabaseReference = database.reference
    private val chieldRef: DatabaseReference = rootRef.child(child)
    private val generalRef: DatabaseReference = rootRef.child("allSensors")

    @Suppress("UNCHECKED_CAST")
    suspend fun getAllSensors(): DataSnapshot? {
        return try {
            generalRef.get().await()
        } catch (e: Exception) {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getSensors(): DataSnapshot? {
        return try {
            chieldRef.get().await()
        } catch (e: Exception) {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun setSensors(T: Any, uuid: String?): Void? {
        return try {
            chieldRef.child(uuid!!).setValue(T).await()
            generalRef.child(uuid).setValue(T).await()
        } catch (e: Exception) {
            null
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser
}