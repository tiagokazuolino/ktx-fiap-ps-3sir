package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.main

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.slide.IntroSlide
import br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.slide.IntroSlideAdapter
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.MainFragmentBinding

class MainViewModel : ViewModel() {
    private val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut congue tellus odio, accumsan ultrices risus rutrum in. In hac habitasse platea dictumst. Sed ac urna blandit enim faucibus lobortis in at justo. Proin lacinia ut arcu sed gravida. Donec suscipit, lorem id fermentum mollis, dui felis euismod quam, vel elementum nibh libero vel ligula. Donec pretium varius quam eu feugiat. Sed vel est posuere, eleifend sem a, vehicula magna."
    val introSliderAdapter = IntroSlideAdapter(
        listOf(
            IntroSlide(
                "Oxigenio",
                description,
                R.drawable.ic_oxygen_white
            ),
            IntroSlide(
                "PH",
                description,
                R.drawable.ic_ph_meter_white
            ),
            IntroSlide(
                "Temperatura",
                description,
                R.drawable.ic_thermometer_white
            ),
            IntroSlide(
                "Algas Marinhas",
                description,
                R.drawable.ic_seaweed_white
            ),
        )
    )
    fun setupIndicators(binding: MainFragmentBinding, context: Context) {
        val indicators = arrayOfNulls<ImageView>(this.introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_indicator_active
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicatorsContainer.addView(indicators[i])
        }
    }

    fun setCurrentIndicator(index :Int, binding: MainFragmentBinding, context: Context){
        val childCount = binding.indicatorsContainer.childCount
        for(i in 0 until childCount){
            val imageView = binding.indicatorsContainer[i] as ImageView
            if(i == index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_indicator_active
                    )
                )
            } else imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_indicator_inactive
                )
            )

        }
    }
}