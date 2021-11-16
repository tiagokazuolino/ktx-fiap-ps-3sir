package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private val args by navArgs<ProfileFragmentArgs>()
    private val action = ProfileFragmentDirections

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.apply {
            tvDisplayName.text = "Nome: ${args.user?.displayName}"
            tvEmail.text = "E-mail: ${args.user?.email}"
            tvPhoneNumber.text = "Telefone: ${args.user?.phoneNumber}"
            tvProviderId.text = "Provide Id: ${args.user?.providerId}"
            tvTenantId.text = "Tenat Id: ${args.user?.tenantId}"
            tvUid.text = "Uid: ${args.user?.uid}"
        }
        binding.btnProfileToHome.setOnClickListener {
            findNavController().navigate(action.actionProfileFragmentToHomeFragment())
        }
    }
}
