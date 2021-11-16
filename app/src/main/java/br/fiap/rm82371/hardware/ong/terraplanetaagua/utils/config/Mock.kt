package br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.config

import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.OxigenioSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.PhSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.ProfundidadeSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.TemperaturaSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.DataSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.StatusSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.TypeSensor

import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class Mock {
    companion object Factory {
        private val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
        fun eventList(size: Int): List<DataSensor>? {
            val result = mutableListOf<DataSensor>()
            for (i in 1..size) {
                val max = Random.nextFloat() * 40
                var min = Random.nextFloat() * 40
                if(min > max){
                    min = max - 0.5f
                }
                val sensor = DataSensor(
                    min,
                    max,
                    formatter.parse(
                        "${Random.nextInt(1, 29)}-jun-${
                            Random.nextInt(
                                2000,
                                2021
                            )
                        }"
                    )
                )
                result.add(sensor)
            }
            return result
        }
    }
}