package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.profundidade

import androidx.lifecycle.*
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.ProfundidadeSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories.ProfundidadeSensorRepository
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import kotlinx.coroutines.launch

class ProfundidadeViewModel(private val repository: ProfundidadeSensorRepository) : ViewModel() {
    private val _profundidadeLiveData = MutableLiveData<List<ProfundidadeSensorModel>>()

    val profundidadeLiveData: LiveData<List<ProfundidadeSensorModel>> = _profundidadeLiveData

    var profundidadeListener: ResponseListener? = null

    fun start() {
        profundidadeListener?.onStarted()
        viewModelScope.launch {
            val result = repository.getSensor()
            if (result != null) {
                val response = result.children.map { snapshot ->
                    snapshot.getValue(ProfundidadeSensorModel::class.java)
                }
                _profundidadeLiveData.postValue(response as List<ProfundidadeSensorModel>?)
                profundidadeListener?.onSuccess()
            } else {
                profundidadeListener?.onFailure("Fail to get data")
            }
        }

    }

    fun create(sensor: ProfundidadeSensorModel) {
        viewModelScope.launch {
            profundidadeListener?.onStarted()

            repository.setSensors(sensor, sensor.uid)
            profundidadeListener?.onUpdated()
        }
    }

    fun logout() {
        repository.logout()
        profundidadeListener?.onSuccess()
    }

    val user by lazy {
        repository.currentUser()
    }

    @Suppress("UNCHECKED_CAST")
    class ProfundidadeViewModelFactory(private val repository: ProfundidadeSensorRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfundidadeViewModel(repository) as T
        }
    }
}