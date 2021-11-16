package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.temperatura.detail

import android.graphics.Color
import android.graphics.DashPathEffect
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.TemperaturaDetailFragmentBinding
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.YAxis

import com.github.mikephil.charting.components.LimitLine

import com.github.mikephil.charting.components.XAxis




class TemperaturaDetailFragment : Fragment() {

    companion object {
        fun newInstance() = TemperaturaDetailFragment()
    }

    private lateinit var viewModel: TemperaturaDetailViewModel
    private lateinit var binding: TemperaturaDetailFragmentBinding
    private val args by navArgs<TemperaturaDetailFragmentArgs>()
    private val action = TemperaturaDetailFragmentDirections


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TemperaturaDetailFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TemperaturaDetailViewModel::class.java]

        val eventsAdapter = TemperaturaDetailRecyclerViewAdapter()
        val entriesMax = ArrayList<Entry>()
        val entriesMin = ArrayList<Entry>()
        val xAxisLabels = ArrayList<String>()

        args.temperatura?.events?.mapIndexed { i, event ->
            val xBar = (i.toFloat() + 0.5f)
            entriesMin.add(Entry(xBar, event.min!!))
            entriesMax.add(Entry(i.toFloat(), event.max!!))
            xAxisLabels.add(i.toString())
        }

        val dataSetMax = LineDataSet(entriesMax, "Max")
        dataSetMax.apply {
            setDrawIcons(false)
            enableDashedLine(10f, 5f, 0f)
            setCircleColor(Color.DKGRAY)
            setDrawCircleHole(false)
            setDrawFilled(true)
            formLineWidth = 1f
            lineWidth = 1f
            circleRadius = 1f
            color = Color.BLUE
            formLineWidth = 0.5f
            valueTextSize = 5f
            formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        }


        val dataSetMin = LineDataSet(entriesMin, "Min")
        dataSetMin.color = Color.GREEN

        val data = LineData()
        data.addDataSet(dataSetMin)
        data.addDataSet(dataSetMax)


        val llXAxis = LimitLine(10f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f

        val xAxis: XAxis = binding.bcTemperaturaDetatilChart.xAxis
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        xAxis.axisMaximum = 10f
        xAxis.axisMinimum = 0f
        xAxis.setDrawLimitLinesBehindData(true)

        val ll1 = LimitLine(30f, "Maximum Limit")
        ll1.lineWidth = 1f
        ll1.enableDashedLine(10f, 1f, 0f)
        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f

        val ll2 = LimitLine(10f, "Minimum Limit")
        ll2.lineWidth = 1f
        ll2.enableDashedLine(10f, 1f, 0f)
        ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f

        val leftAxis: YAxis = binding.bcTemperaturaDetatilChart.axisLeft
        leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(ll1)
        leftAxis.addLimitLine(ll2)
        leftAxis.axisMaximum = 40f
        leftAxis.axisMinimum = 0f
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(false)

        binding.apply {
            bcTemperaturaDetatilChart.data = data
            bcTemperaturaDetatilChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        }

        binding.apply {
            tvTemperaturaDetailsUuid.text = args.temperatura?.uid
            tvTemperaturaDetailsTitle.text = args.temperatura?.name
            tvTemperaturaDetailsStatus.text = args.temperatura?.status.toString()
            tvTemperaturaDetailsBattery.text =
                "Bateria em ${args.temperatura?.battery.toString()} % - "
        }

        binding.rvTemperaturaDetails.adapter = eventsAdapter

        viewModel.start(args.temperatura?.events!!)
        viewModel.eventsLiveData.observe(viewLifecycleOwner) { events ->
            eventsAdapter.setItems(events)
        }

        binding.apply {
            btnTemperaturaDetilsHome.setOnClickListener {
                findNavController().navigate(action.actionTemperaturaDetailFragmentToHomeFragment())
            }
            btnTemperaturaDetilsBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}