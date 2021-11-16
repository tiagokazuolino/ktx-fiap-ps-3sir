package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.temperatura

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.TemperaturaSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.StatusSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.TypeSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.TemperaturaFragmentBinding
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.config.Mock
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.lang.ref.WeakReference
import java.util.*
import kotlin.random.Random

class TemperaturaFragment : Fragment(), ResponseListener, DIAware,
    TemperaturaSensorsRecyclerViewAdapter.TemperaturaSensorItemInterface {
    companion object {
        fun newInstance() = TemperaturaFragment()
    }

    override val di: DI by closestDI()
    private lateinit var viewModel: TemperaturaViewModel
    private lateinit var binding: TemperaturaFragmentBinding
    private val factory: TemperaturaViewModel.TemperaturaViewModelFactory by instance()
    private val action = TemperaturaFragmentDirections

    private val temperaturaSensorsAdapter = TemperaturaSensorsRecyclerViewAdapter(WeakReference(this))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.temperatura_fragment, container, false)
        binding.lifecycleOwner = this
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[TemperaturaViewModel::class.java]
        viewModel.temperaturaListener = this
        binding.btnTemperaturaCreate.setOnClickListener {
            val list = Mock.eventList(10)
            val sensor = TemperaturaSensorModel(
                UUID.randomUUID().toString(),
                "BR-TESTE-TEMP",
                Random.nextLong(0, 100),
                (Random.nextFloat() * 90) * (if (Random.nextBoolean()) -1 else 1),
                (Random.nextFloat() * 180) * (if (Random.nextBoolean()) -1 else 1),
                StatusSensor.values()[Random.nextInt(StatusSensor.values().size)],
                TypeSensor.TEMPERATURA,
                list
            )
            viewModel.create(sensor)
        }
        binding.btnTemperaturaHome.setOnClickListener {
            findNavController().navigate(action.actionTemperaturaFragmentToHomeFragment())
        }
        binding.btnTemperaturaExit.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(action.actionTemperaturaFragmentToWelcomeFragment())
        }
        binding.rvTemperaturaSensorList.adapter = temperaturaSensorsAdapter
        viewModel.start()
        viewModel.temperaturaLiveData.observe(viewLifecycleOwner) { temperaturaSensorItems ->
            temperaturaSensorsAdapter.setItems(temperaturaSensorItems)
        }
    }

    override fun onStarted() {
    }

    override fun onSuccess() {
    }

    override fun onUpdated() {
        viewModel.start()
        temperaturaSensorsAdapter.notifyDataSetChanged()
    }

    override fun onFailure(message: String) {
        Log.i("OXIGENIO", message)
    }

    override fun onTemperaturaSensorItemClick(item: TemperaturaSensorModel) {
        findNavController().navigate(action.actionTemperaturaFragmentToTemperaturaDetailFragment(item))
    }
}