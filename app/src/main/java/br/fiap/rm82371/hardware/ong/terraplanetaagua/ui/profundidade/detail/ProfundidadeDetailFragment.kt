package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.profundidade.detail

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.alpha
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ProfundidadeDetailFragmentBinding
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ProfundidadeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ProfundidadeDetailFragment()
    }

    private lateinit var viewModel: ProfundidadeDetailViewModel
    private lateinit var binding: ProfundidadeDetailFragmentBinding
    private val args by navArgs<ProfundidadeDetailFragmentArgs>()
    private val action = ProfundidadeDetailFragmentDirections


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfundidadeDetailFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProfundidadeDetailViewModel::class.java]

        val eventsAdapter = ProfundidadeDetailRecyclerViewAdapter()
        val entriesMax = ArrayList<Entry>()
        val entriesMin = ArrayList<Entry>()
        val xAxisLabels = ArrayList<String>()

        args.profundidade?.events?.mapIndexed { i, event ->
            val xBar = (i.toFloat() + 0.5f)
            entriesMin.add(Entry(xBar, event.min!!))
            entriesMax.add(Entry(i.toFloat(), event.max!!))
            xAxisLabels.add(i.toString())
        }

        val dataSetMax = ScatterDataSet(entriesMax, "Max")
        dataSetMax.apply {
            color = Color.BLUE
            formLineWidth = 0.5f
            valueTextSize = 5f
            setScatterShape(ScatterChart.ScatterShape.SQUARE)
        }

        val dataSetMin = ScatterDataSet(entriesMin, "Min")
        dataSetMin.apply {
            setScatterShape(ScatterChart.ScatterShape.CIRCLE)
            color = Color.DKGRAY
        }

        val data = ScatterData()
        data.addDataSet(dataSetMin)
        data.addDataSet(dataSetMax)

        binding.apply {
            bcProfundidadeDetatilChart.data = data
            bcProfundidadeDetatilChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        }

        binding.apply {
            tvProfundidadeDetailsUuid.text = args.profundidade?.uid
            tvProfundidadeDetailsTitle.text = args.profundidade?.name
            tvProfundidadeDetailsStatus.text = args.profundidade?.status.toString()
            tvProfundidadeDetailsBattery.text = "Bateria em ${args.profundidade?.battery.toString()} % - "
        }

        binding.rvProfundidadeDetails.adapter = eventsAdapter

        viewModel.start(args.profundidade?.events!!)
        viewModel.eventsLiveData.observe(viewLifecycleOwner) { events ->
            eventsAdapter.setItems(events)
        }

        binding.apply {
            btnProfundidadeDetilsHome.setOnClickListener {
                findNavController().navigate(action.actionProfundidadeDetailFragmentToHomeFragment())
            }
            btnProfundidadeDetilsBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}