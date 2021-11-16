package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.ph

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.PhSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.StatusSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.TypeSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.PHFragmentBinding
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.config.Mock
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import java.lang.ref.WeakReference

class PHFragment : Fragment(), ResponseListener, DIAware,
    PHSensorsRecyclerViewAdapter.PhSensorItemInterface {
    companion object {
        fun newInstance() = PHFragment()
    }

    override val di: DI by closestDI()
    private lateinit var viewModel: PHViewModel
    private lateinit var binding: PHFragmentBinding
    private val factory: PHViewModel.PHViewModelFactory by instance()
    private val action = PHFragmentDirections
    private val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    private val phSensorsAdapter = PHSensorsRecyclerViewAdapter(WeakReference(this))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.p_h_fragment, container, false)
        binding.lifecycleOwner = this
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[PHViewModel::class.java]
        viewModel.phSensorListener = this
        binding.btnPhCreate.setOnClickListener {
            val list = Mock.eventList(10)
            val sensor = PhSensorModel(
                UUID.randomUUID().toString(),
                "BR-TESTE-PHS",
                Random.nextLong(0, 100),
                (Random.nextFloat() * 90) * (if (Random.nextBoolean()) -1 else 1),
                (Random.nextFloat() * 180) * (if (Random.nextBoolean()) -1 else 1),
                StatusSensor.values()[Random.nextInt(StatusSensor.values().size)],
                TypeSensor.PH,
                list
            )
            viewModel.create(sensor)
        }
        binding.btnPhHome.setOnClickListener {
            findNavController().navigate(action.actionPHFragmentToHomeFragment())
        }
        binding.btnPhExit.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(action.actionPHFragmentToWelcomeFragment())
        }
        binding.rvPhSensorList.adapter = phSensorsAdapter

        viewModel.apply {
            start()
            phSensorLiveData.observe(viewLifecycleOwner) { phSensorItems ->
                phSensorsAdapter.setItems(phSensorItems)
            }
        }
    }

    override fun onStarted() {
        Log.i("PH", "its started!")
    }

    override fun onSuccess() {
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onUpdated() {
        viewModel.start()
        phSensorsAdapter.notifyDataSetChanged()
    }

    override fun onFailure(message: String) {
    }

    override fun onPhSensorItemClick(item: PhSensorModel) {
        findNavController().navigate(action.actionPHFragmentToPHDetailFragment(item))
    }
}