package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.oxigenio

import androidx.lifecycle.*
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.OxigenioSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories.OxigenioSensorRepository
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import kotlinx.coroutines.launch

class OxigenioViewModel(private val repository: OxigenioSensorRepository) : ViewModel() {

    private val _oxigenioLiveData = MutableLiveData<List<OxigenioSensorModel>>()

    val oxigenioLiveData: LiveData<List<OxigenioSensorModel>> = _oxigenioLiveData

    var oxigenioListener: ResponseListener? = null

    fun start() {
        oxigenioListener?.onStarted()
        viewModelScope.launch {
            val result = repository.getSensor()
            if (result != null) {
                val response = result.children.map { snapshot ->
                    snapshot.getValue(OxigenioSensorModel::class.java)
                }
                _oxigenioLiveData.postValue(response as List<OxigenioSensorModel>?)
                oxigenioListener?.onSuccess()
            } else {
                oxigenioListener?.onFailure("Fail to get data")
            }
        }

    }

    fun create(sensor: OxigenioSensorModel) {
        viewModelScope.launch {
            oxigenioListener?.onStarted()
            repository.setSensors(sensor, sensor.uid)
            oxigenioListener?.onUpdated()
        }
    }

    fun logout() {
        repository.logout()
        oxigenioListener?.onSuccess()
    }

    val user by lazy {
        repository.currentUser()
    }

    @Suppress("UNCHECKED_CAST")
    class OxigenioViewModelFactory(private val repository: OxigenioSensorRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OxigenioViewModel(repository) as T
        }
    }
}