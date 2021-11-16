package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.manutencao

import android.util.Log
import androidx.lifecycle.ViewModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.HomeModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.StatusSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.TypeSensor
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*
import kotlin.collections.ArrayList

class ManutencaoSensorViewModel : ViewModel() {
    fun getPercent(list: List<HomeModel>?): List<PieEntry> {
        val percent = ArrayList<PieEntry>()
        val total = list?.size
        var connected = 0
        var disconnected = 0
        var falied = 0

        list?.map {
            if (it.status == StatusSensor.CONNECTED) connected += 1
            if (it.status == StatusSensor.DISCONNECT) disconnected += 1
            if (it.status == StatusSensor.FAILED) falied += 1

        }
        if (total != null) {
            percent.add(
                PieEntry(
                    (connected * 100 / total.toFloat()),
                    StatusSensor.CONNECTED.toString()
                )
            )
            percent.add(
                PieEntry(
                    (disconnected * 100 / total.toFloat()),
                    StatusSensor.DISCONNECT.toString()
                )
            )
            percent.add(
                PieEntry(
                    (falied * 100 / total.toFloat()),
                    StatusSensor.FAILED.toString()
                )
            )
        }



        return percent
    }

    fun getColors(): List<Int> {
        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        return colors
    }
}