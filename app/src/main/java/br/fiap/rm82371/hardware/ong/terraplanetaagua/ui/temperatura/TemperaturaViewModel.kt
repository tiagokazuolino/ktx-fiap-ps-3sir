package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.temperatura

import androidx.lifecycle.*
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.TemperaturaSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories.TemperaturaSensorRepository
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import kotlinx.coroutines.launch

class TemperaturaViewModel(private val repository: TemperaturaSensorRepository) : ViewModel() {
    private val _temperaturaLiveData = MutableLiveData<List<TemperaturaSensorModel>>()

    val temperaturaLiveData: LiveData<List<TemperaturaSensorModel>> = _temperaturaLiveData

    var temperaturaListener: ResponseListener? = null

    fun start() {
        temperaturaListener?.onStarted()
        viewModelScope.launch {
            val result = repository.getSensor()
            if (result != null) {
                val response = result.children.map { snapshot ->
                    snapshot.getValue(TemperaturaSensorModel::class.java)
                }
                _temperaturaLiveData.postValue(response as List<TemperaturaSensorModel>?)
                temperaturaListener?.onSuccess()
            } else {
                temperaturaListener?.onFailure("Fail to get data")
            }
        }

    }

    fun create(sensor: TemperaturaSensorModel) {
        viewModelScope.launch {
            temperaturaListener?.onStarted()

            repository.setSensors(sensor, sensor.uid)
            temperaturaListener?.onUpdated()
        }
    }

    fun logout() {
        repository.logout()
        temperaturaListener?.onSuccess()
    }

    val user by lazy {
        repository.currentUser()
    }

    @Suppress("UNCHECKED_CAST")
    class TemperaturaViewModelFactory(private val repository: TemperaturaSensorRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TemperaturaViewModel(repository) as T
        }
    }
}