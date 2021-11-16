package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {
    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.root.isForceDarkAllowed = false
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.introSliderViewPager.adapter = viewModel.introSliderAdapter

        viewModel.setupIndicators(binding, requireContext())

        viewModel.setCurrentIndicator(0, binding, requireContext())

        binding.introSliderViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setCurrentIndicator(position, binding, requireContext())
            }
        })
        binding.btnNext.setOnClickListener {
            // val action = MainFra
            // val navGraph = Navigation.findNavController( requireActivity(), R.id.main)
            if(binding.introSliderViewPager.currentItem + 1 < viewModel.introSliderAdapter.itemCount)
            {
                binding.introSliderViewPager.currentItem += 1
            } else {
                //val action = MainFragmentDirections.actionMainFragmentToWelcomeFragment()
               // Navigation.findNavController().navigate(action)
                val action = MainFragmentDirections.actionMainFragmentToWelcomeFragment()
                findNavController().navigate(action)
            }
        }
        binding.tvSkip.setOnClickListener {
            // findNavController().navigate(action)
            val action = MainFragmentDirections.actionMainFragmentToWelcomeFragment()
            findNavController().navigate(action)
        }
    }



}