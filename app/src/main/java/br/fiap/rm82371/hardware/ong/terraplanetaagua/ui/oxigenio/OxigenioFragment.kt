package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.oxigenio

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
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.OxigenioSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.StatusSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.TypeSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.OxigenioFragmentBinding
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.config.Mock
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.lang.ref.WeakReference
import java.util.*
import kotlin.random.Random

class OxigenioFragment : Fragment(), ResponseListener, DIAware,
    OxigenioSensorsRecyclerViewAdapter.OxigenioSensorItemInterface {
    companion object {
        fun newInstance() = OxigenioFragment()
    }

    override val di: DI by closestDI()
    private lateinit var viewModel: OxigenioViewModel
    private lateinit var binding: OxigenioFragmentBinding
    private val factory: OxigenioViewModel.OxigenioViewModelFactory by instance()
    private val action = OxigenioFragmentDirections

    private val oxigenioSensorsAdapter = OxigenioSensorsRecyclerViewAdapter(WeakReference(this))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.oxigenio_fragment, container, false)
        binding.lifecycleOwner = this
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[OxigenioViewModel::class.java]
        viewModel.oxigenioListener = this
        binding.btnOxigenioCreate.setOnClickListener {
            val list = Mock.eventList(10)
            val sensor = OxigenioSensorModel(
                UUID.randomUUID().toString(),
                "BR-TESTE-OXI",
                Random.nextLong(0, 100),
                (Random.nextFloat() * 90) * (if (Random.nextBoolean()) -1 else 1),
                (Random.nextFloat() * 180) * (if (Random.nextBoolean()) -1 else 1),
                StatusSensor.values()[Random.nextInt(StatusSensor.values().size)],
                TypeSensor.OXIGENIO,
                list
            )
            viewModel.create(sensor)
        }
        binding.btnOxigenioHome.setOnClickListener {
            findNavController().navigate(action.actionOxigenioFragmentToHomeFragment())
        }
        binding.btnOxigenioExit.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(action.actionOxigenioFragmentToWelcomeFragment())
        }
        binding.rvOxigenioSensorList.adapter = oxigenioSensorsAdapter
        viewModel.start()
        viewModel.oxigenioLiveData.observe(viewLifecycleOwner) { oxigenioSensorItems ->
            oxigenioSensorsAdapter.setItems(oxigenioSensorItems)
        }
    }

    override fun onStarted() {
    }

    override fun onSuccess() {
    }

    override fun onUpdated() {
        viewModel.start()
        oxigenioSensorsAdapter.notifyDataSetChanged()
    }

    override fun onFailure(message: String) {
        Log.i("OXIGENIO", message)
    }

    override fun onOxigenioSensorItemClick(item: OxigenioSensorModel) {
        findNavController().navigate(action.actionOxigenioFragmentToOxigenioDetailFragment(item))
    }
}