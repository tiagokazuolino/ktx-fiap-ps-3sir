package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.home

import androidx.lifecycle.*
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.HomeModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.OxigenioSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories.HomeRepository
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories.UserRepository
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private val _sensorLiveData = MutableLiveData<List<HomeModel>>()

    val sensorLiveData: LiveData<List<HomeModel>> = _sensorLiveData

    var sensorListener: ResponseListener? = null

    fun start() {
        viewModelScope.launch {
            sensorListener?.onStarted()
            val result = repository.getSensors()
            if(result!= null){
                val response = result.children.map { snapshot ->
                    snapshot.getValue(HomeModel::class.java)
                }
                _sensorLiveData.postValue(response as List<HomeModel>?)
                sensorListener?.onSuccess()
            } else {
                sensorListener?.onFailure("error")
            }
        }
    }
    val user by lazy {
        repository.currentUser()
    }

    fun logout() {
        repository.logout()
    }

    class HomeViewModelFactory(private val repository: HomeRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }

    }
}