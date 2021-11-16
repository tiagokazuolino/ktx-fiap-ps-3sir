package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.welcome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.WelcomeFragmentBinding

class WelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private lateinit var viewModel: WelcomeViewModel

    private lateinit var binding: WelcomeFragmentBinding

    private var action = WelcomeFragmentDirections

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WelcomeFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(action.actionWelcomeFragmentToSignInFragment())
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(action.actionWelcomeFragmentToSignUpFragment())
        }

    }

}