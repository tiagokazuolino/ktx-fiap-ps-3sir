package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.manutencao

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ManutencaoSensorFragmentBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter

class ManutencaoSensorFragment : Fragment() {

    companion object {
        fun newInstance() = ManutencaoSensorFragment()
    }

    private lateinit var viewModel: ManutencaoSensorViewModel
    private val args by navArgs<ManutencaoSensorFragmentArgs>()
    private lateinit var binding: ManutencaoSensorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ManutencaoSensorFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManutencaoSensorViewModel::class.java)
        val percent = viewModel.getPercent(args.manutencao?.list)
        val colors = viewModel.getColors()
        val dataSet = PieDataSet(percent, "Status Sensor")
        dataSet.colors = colors

        binding.pcManutencao.data = PieData(dataSet)
        binding.pcManutencao.apply {
            data.setValueFormatter(PercentFormatter(this))
            data.setValueTextSize(12f)
            data.setDrawValues(true)
            data.setValueTextColor(Color.WHITE)
            centerText = "Sensor By Status"
            setCenterTextSize(24f)
            description.isEnabled = true
            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
            }
        }


        binding.pcManutencao.invalidate()
    }
}