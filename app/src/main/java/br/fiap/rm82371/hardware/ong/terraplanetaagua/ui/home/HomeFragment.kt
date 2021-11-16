package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.HomeModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.UserModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.Senores
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.TypeSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.HomeFragmentBinding
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.http.ResponseListener
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class HomeFragment : Fragment(), DIAware, ResponseListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val di: DI by closestDI()
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private val factory: HomeViewModel.HomeViewModelFactory by instance()
    private var action = HomeFragmentDirections


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        viewModel.sensorListener = this
        viewModel.start()
        val sensorList = ArrayList<HomeModel>()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mvMap) as SupportMapFragment
        mapFragment.getMapAsync { maps ->
            maps.clear()
        }

        viewModel.sensorLiveData.observe(viewLifecycleOwner) { list ->
            list.map {
                sensorList.add(it)
                mapFragment.getMapAsync { maps ->
                    maps.addMarker(
                        MarkerOptions()
                            .icon(colorMarker(it.type))
                            .position(
                                LatLng(
                                    it.lat!!.toDouble(),
                                    it.lng!!.toDouble()
                                )
                            ).title(it.name)
                    )
                }
            }
        }

        binding.btnHomeExit.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(action.actionHomeFragmentToWelcomeFragment())
        }
        binding.btnHomeOxigenio.setOnClickListener {
            findNavController().navigate(action.actionHomeFragmentToOxigenioFragment())
        }
        binding.btnHomePH.setOnClickListener {
            findNavController().navigate(action.actionHomeFragmentToPHFragment())
        }
        binding.btnHomeProfundidade.setOnClickListener {
            findNavController().navigate(action.actionHomeFragmentToProfundidadeFragment())
        }
        binding.btnHomeTemperatura.setOnClickListener {
            findNavController().navigate(action.actionHomeFragmentToTemperaturaFragment())
        }
        binding.btnProfile.setOnClickListener {
            val user = UserModel()
            user.displayName =
                if (viewModel.user?.displayName.isNullOrEmpty()) "não definido" else viewModel.user?.displayName
            user.email =
                if (viewModel.user?.email.isNullOrEmpty()) "não definido" else viewModel.user?.email
            user.tenantId =
                if (viewModel.user?.tenantId.isNullOrEmpty()) "não definido" else viewModel.user?.tenantId
            user.phoneNumber =
                if (viewModel.user?.phoneNumber.isNullOrEmpty()) "não definido" else viewModel.user?.phoneNumber
            user.providerId =
                if (viewModel.user?.providerId.isNullOrEmpty()) "não definido" else viewModel.user?.providerId
            user.uid =
                if (viewModel.user?.uid.isNullOrEmpty()) "não definido" else viewModel.user?.uid
            findNavController().navigate(action.actionHomeFragmentToProfileFragment(user))
        }
        binding.btnMaintence.setOnClickListener {
            val senores = Senores()
            senores.list = sensorList
            findNavController().navigate(action.actionHomeFragmentToManutencaoSensorFragment(senores))
        }
    }

    private fun colorMarker(type: TypeSensor?): BitmapDescriptor? {
        return if (type == TypeSensor.OXIGENIO) {
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        } else if (type == TypeSensor.PH) {
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        } else if (type == TypeSensor.PROFUNDIDADE) {
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)
        } else {
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        }
    }

    override fun onStarted() {
        Log.i("HOME", "load data started!")
    }

    override fun onSuccess() {
        Log.i("HOME", "load data success!")
    }

    override fun onUpdated() {
        Log.i("HOME", "load data updated!")
    }

    override fun onFailure(message: String) {
        Log.i("HOME", message)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}