package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.oxigenio.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.OxigenioDetailFragmentBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OxigenioDetailFragment : Fragment() {

    companion object {
        fun newInstance() = OxigenioDetailFragment()
    }

    private lateinit var viewModel: OxigenioDetailViewModel
    private lateinit var binding: OxigenioDetailFragmentBinding
    private val args by navArgs<OxigenioDetailFragmentArgs>()
    private val action = OxigenioDetailFragmentDirections


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OxigenioDetailFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[OxigenioDetailViewModel::class.java]

        val eventsAdapter = OxigenioDetailRecyclerViewAdapter()
        val entriesMax = ArrayList<BarEntry>()
        val entriesMin = ArrayList<BarEntry>()
        val xAxisLabels = ArrayList<String>()

        args.oxigenio?.events?.mapIndexed { i, event ->
            val xBar = (i.toFloat() + 0.5f)
            entriesMin.add(BarEntry(xBar, event.min!!))
            entriesMax.add(BarEntry(i.toFloat(), event.max!!))
            xAxisLabels.add(i.toString())
        }

        val dataSetMax = BarDataSet(entriesMax, "Max")
        dataSetMax.color = Color.BLUE
        dataSetMax.formLineWidth = 0.5f
        dataSetMax.valueTextSize = 5f

        val dataSetMin = BarDataSet(entriesMin, "Min")
        dataSetMin.color = Color.GREEN

        val data = BarData()
        data.addDataSet(dataSetMin)
        data.addDataSet(dataSetMax)

        binding.apply {
            bcOxigenioDetatilChart.data = data
            bcOxigenioDetatilChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        }

        binding.apply {
            tvOxigenioDetailsUuid.text = args.oxigenio?.uid
            tvOxigenioDetailsTitle.text = args.oxigenio?.name
            tvOxigenioDetailsStatus.text = args.oxigenio?.status.toString()
            tvOxigenioDetailsBattery.text = "Bateria em ${args.oxigenio?.battery.toString()} % - "
        }

        binding.rvOxigenioDetails.adapter = eventsAdapter

        viewModel.start(args.oxigenio?.events!!)
        viewModel.eventsLiveData.observe(viewLifecycleOwner) { events ->
            eventsAdapter.setItems(events)
        }

        binding.apply {
            btnOxigenioDetilsHome.setOnClickListener {
                findNavController().navigate(action.actionOxigenioDetailFragmentToHomeFragment())
            }
            btnOxigenioDetilsBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}