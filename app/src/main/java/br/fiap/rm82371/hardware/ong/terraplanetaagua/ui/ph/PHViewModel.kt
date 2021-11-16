package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.ph

import androidx.lifecycle.*
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.PhSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories.PhSensorRepository
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import kotlinx.coroutines.launch

class PHViewModel(private val repository: PhSensorRepository) : ViewModel() {
    private val _phSensorLiveData = MutableLiveData<List<PhSensorModel>>()

    val phSensorLiveData: LiveData<List<PhSensorModel>> = _phSensorLiveData

    var phSensorListener: ResponseListener? = null

    fun start() {
        phSensorListener?.onStarted()
        viewModelScope.launch {
            val result = repository.getSensor()
            if (result != null) {
                val response = result.children.map { snapshot ->
                    snapshot.getValue(PhSensorModel::class.java)
                }
                _phSensorLiveData.postValue(response as List<PhSensorModel>?)
                phSensorListener?.onSuccess()
            } else {
                phSensorListener?.onFailure("Fail to get data")
            }
        }

    }

    fun create(sensor: PhSensorModel) {
        viewModelScope.launch {
            phSensorListener?.onStarted()

            repository.setSensors(sensor, sensor.uid)
            phSensorListener?.onUpdated()
        }
    }

    fun logout() {
        repository.logout()
        phSensorListener?.onSuccess()
    }

    val user by lazy {
        repository.currentUser()
    }

    @Suppress("UNCHECKED_CAST")
    class PHViewModelFactory(private val repository: PhSensorRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PHViewModel(repository) as T
        }
    }
}