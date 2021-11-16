package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.ph.detail

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.PHDetailFragmentBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class PHDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PHDetailFragment()
    }

    private lateinit var viewModel: PHDetailViewModel
    private lateinit var binding: PHDetailFragmentBinding
    private val args by navArgs<PHDetailFragmentArgs>()
    private val action = PHDetailFragmentDirections


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PHDetailFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PHDetailViewModel::class.java]

        val eventsAdapter = PHDetailRecyclerViewAdapter()
        val entriesMax = ArrayList<BarEntry>()
        val entriesMin = ArrayList<BarEntry>()
        val xAxisLabels = ArrayList<String>()

        args.ph?.events?.mapIndexed { i, event ->
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
            bcPHDetatilChart.data = data
            bcPHDetatilChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        }

        binding.apply {
            tvPHDetailsUuid.text = args.ph?.uid
            tvPHDetailsTitle.text = args.ph?.name
            tvPHDetailsStatus.text = args.ph?.status.toString()
            tvPHDetailsBattery.text = "Bateria em ${args.ph?.battery.toString()} % - "
        }

        binding.rvPHDetails.adapter = eventsAdapter

        viewModel.start(args.ph?.events!!)
        viewModel.eventsLiveData.observe(viewLifecycleOwner) { events ->
            eventsAdapter.setItems(events)
        }

        binding.apply {
            btnPHDetilsHome.setOnClickListener {
                findNavController().navigate(action.actionPHDetailFragmentToHomeFragment())
            }
            btnPHDetilsBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}