package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.oxigenio.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.DataSensor

class OxigenioDetailViewModel : ViewModel() {
    private val _eventsLivedata = MutableLiveData<List<DataSensor>>()
    val eventsLiveData: LiveData<List<DataSensor>> = _eventsLivedata

    fun start(list: List<DataSensor>) {
        var listSortedByDate = list.sortedWith(compareByDescending { it.eventAt?.year })
        _eventsLivedata.postValue(listSortedByDate)
    }
}