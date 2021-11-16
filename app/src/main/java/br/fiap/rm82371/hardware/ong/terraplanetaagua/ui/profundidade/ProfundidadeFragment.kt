package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.profundidade

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
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.ProfundidadeSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.StatusSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.TypeSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ProfundidadeFragmentBinding
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.config.Mock
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.lang.ref.WeakReference
import java.util.*
import kotlin.random.Random

class ProfundidadeFragment : Fragment(), ResponseListener, DIAware,
    ProfundidadeSensorsRecyclerViewAdapter.ProfundidadeSensorItemInterface {
    companion object {
        fun newInstance() = ProfundidadeFragment()
    }

    override val di: DI by closestDI()
    private lateinit var viewModel: ProfundidadeViewModel
    private lateinit var binding: ProfundidadeFragmentBinding
    private val factory: ProfundidadeViewModel.ProfundidadeViewModelFactory by instance()
    private val action = ProfundidadeFragmentDirections

    private val profundidadeSensorsAdapter = ProfundidadeSensorsRecyclerViewAdapter(WeakReference(this))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profundidade_fragment, container, false)
        binding.lifecycleOwner = this
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[ProfundidadeViewModel::class.java]
        viewModel.profundidadeListener = this
        binding.btnProfundidadeCreate.setOnClickListener {
            val list = Mock.eventList(10)
            val sensor = ProfundidadeSensorModel(
                UUID.randomUUID().toString(),
                "BR-TESTE-PRO",
                Random.nextLong(0, 100),
                (Random.nextFloat() * 90) * (if (Random.nextBoolean()) -1 else 1),
                (Random.nextFloat() * 180) * (if (Random.nextBoolean()) -1 else 1),
                StatusSensor.values()[Random.nextInt(StatusSensor.values().size)],
                TypeSensor.PROFUNDIDADE,
                list
            )
            viewModel.create(sensor)
        }
        binding.btnProfundidadeHome.setOnClickListener {
            findNavController().navigate(action.actionProfundidadeFragmentToHomeFragment())
        }
        binding.btnProfundidadeExit.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(action.actionProfundidadeFragmentToWelcomeFragment())
        }
        binding.rvProfundidadeSensorList.adapter = profundidadeSensorsAdapter
        viewModel.start()
        viewModel.profundidadeLiveData.observe(viewLifecycleOwner) { profundidadeSensorItems ->
            profundidadeSensorsAdapter.setItems(profundidadeSensorItems)
        }
    }

    override fun onStarted() {
    }

    override fun onSuccess() {
    }

    override fun onUpdated() {
        viewModel.start()
        profundidadeSensorsAdapter.notifyDataSetChanged()
    }

    override fun onFailure(message: String) {
        Log.i("OXIGENIO", message)
    }

    override fun onProfundidadeSensorItemClick(item: ProfundidadeSensorModel) {
        findNavController().navigate(action.actionProfundidadeFragmentToProfundidadeDetailFragment(item))
    }
}