package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.SignInFragmentBinding
import br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.auth.AuthListener
import br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.auth.AuthViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class SignInFragment : Fragment(), AuthListener, DIAware {


    companion object {
        fun newInstance() = SignInFragment()
    }

    override val di: DI by closestDI()

    private val factory: AuthViewModel.AuthViewModelFactory by instance()

    private lateinit var viewModel: AuthViewModel

    private lateinit var binding: SignInFragmentBinding

    private var action = SignInFragmentDirections

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignInFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        viewModel.authListener = this

        binding.btnSignIn.setOnClickListener {
            viewModel.email = binding.etSignInEmail.text.toString()
            viewModel.password = binding.etSignInPassword.text.toString()
            viewModel.login()
        }
    }

    override fun onStarted() {
        Log.i("AUTH_STARTED", "auth started")
    }

    override fun onSuccess() {
        findNavController().navigate(action.actionSignInFragmentToHomeFragment())
    }

    override fun onFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {
            findNavController().navigate(action.actionSignInFragmentToHomeFragment())
        }
    }

}